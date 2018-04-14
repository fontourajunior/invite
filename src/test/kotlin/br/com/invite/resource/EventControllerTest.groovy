package br.com.invite.resource

import br.com.invite.commons.Description
import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.event.Event
import br.com.invite.domain.user.User
import br.com.invite.repository.EventRepository
import br.com.invite.repository.UserRepository
import br.com.invite.resource.request.CreateEventRequest
import br.com.invite.resource.request.UpdateEventRequest
import br.com.invite.service.EventService
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.sql.Timestamp

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
@Transactional
class EventControllerTest extends Specification {

    MockMvc mockMvc

    @Autowired
    EventService eventService

    @Autowired
    EventRepository eventRepository

    @Autowired
    UserRepository userRepository

    private User user

    private Timestamp date

    def setup() {
        mockMvc = standaloneSetup(new EventController(eventService)).build()
        user = userRepository.save(new User(new Name("Bart Simpson"),
                new Phone("34324989328"),
                new Password("password123"),
                new ArrayList<Event>()))
        date = Timestamp.valueOf("2018-01-01 00:00:00.0")
    }

    def "Create"() {
        given:
        CreateEventRequest eventRequest = new CreateEventRequest(user.getId(),"Anakin Skywalker", "Event description", date, true)
        def jsonContent = new JsonBuilder(eventRequest).toPrettyString()

        when:
        def response = mockMvc.perform(post('/events').contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.userId == 1
        content.name == "Anakin Skywalker"
        content.description == "Event description"
        content.indConfirmPresence == true
    }

    def "Update"() {
        given:
        def eventCreate = eventRepository.save(new Event(new Name("Event Name"), new Description("Event description"), date, true, user))
        UpdateEventRequest eventRequest = new UpdateEventRequest("Event Name Updated", "Event description", date, false)
        def jsonContent = new JsonBuilder(eventRequest).toPrettyString()

        when:
        def response = mockMvc.perform(put('/events/{id}', eventCreate.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isOk())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.userId == 1
        content.name == "Event Name Updated"
        content.description == "Event description"
        content.indConfirmPresence == false

    }

    def "FindOne"() {
        given:
        def eventCreate = eventRepository.save(new Event(new Name("Event Name"), new Description("Event description"), date, true, user))

        when:
        def response = mockMvc.perform(get('/events/{id}', eventCreate.id))
                .andExpect(status().isOk())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.userId == 1
        content.name == "Event Name"
        content.description == "Event description"
        content.indConfirmPresence == true
    }

    def "Should findo all events"() {
        given:
        def eventCreateOne = eventRepository.save(new Event(new Name("Event One"), new Description("Event one description"), date, true, user))
        def eventCreateTwo = eventRepository.save(new Event(new Name("Event Two"), new Description("Event two description"), date, false, user))

        when:
        def response = mockMvc.perform(get('/events'))
                .andExpect(status().isOk())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content[0].userId == 1
        content[0].name == "Event One"
        content[0].description == "Event one description"
        content[0].indConfirmPresence == true

        content[1].userId == 1
        content[1].name == "Event Two"
        content[1].description == "Event two description"
        content[1].indConfirmPresence == false
    }

}
