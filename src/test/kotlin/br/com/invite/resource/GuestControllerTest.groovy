package br.com.invite.resource

import br.com.invite.commons.Description
import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.event.Event
import br.com.invite.domain.guest.Guest
import br.com.invite.domain.user.User
import br.com.invite.repository.EventRepository
import br.com.invite.repository.GuestRepository
import br.com.invite.repository.UserRepository
import br.com.invite.resource.request.CreateEventRequest
import br.com.invite.resource.request.CreateGuestRequest
import br.com.invite.resource.request.UpdateGuestRequest
import br.com.invite.service.GuestService
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
import java.time.Instant

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
@Transactional
class GuestControllerTest extends Specification {

    MockMvc mockMvc

    @Autowired
    GuestService guestService

    @Autowired
    GuestRepository repository

    @Autowired
    EventRepository eventRepository

    @Autowired
    UserRepository userRepository

    private User homerSimpson

    private User bartSimpson

    private User krusty

    private Event event

    def setup() {
        mockMvc = standaloneSetup(new GuestController(guestService)).build()

        homerSimpson = userRepository.save(new User(new Name("Homer Simpson"),
                new Phone("34324989328"),
                new Password("password123"),
                new ArrayList()))

        bartSimpson = userRepository.save(new User(new Name("Bart Simpson"),
                new Phone("34324989328"),
                new Password("password123"),
                new ArrayList()))

        krusty = userRepository.save(new User(new Name("Krusty"),
                new Phone("34324989328"),
                new Password("password123"),
                new ArrayList()))

        event = eventRepository.save(new Event(
                new Name("Birthday party"),
                new Description("Birthday party"),
                Timestamp.from(Instant.now()),
                true,
                krusty
        ))
    }

    def "Create"() {
        given:
        CreateGuestRequest guestRequest = new CreateGuestRequest(event.id, homerSimpson.id, true)
        def jsonContent = new JsonBuilder(guestRequest).toPrettyString()

        when:
        def response = mockMvc.perform(post('/guests').contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.eventId == event.id
        content.userId == homerSimpson.id
        content.indConfirmPresence == true
    }

    def "Update"() {
        given:
        def guestCreated = repository.save(new Guest(event, homerSimpson, true))
        UpdateGuestRequest guestRequest = new UpdateGuestRequest(false)

        def jsonContent = new JsonBuilder(guestRequest).toPrettyString()

        when:
        def response = mockMvc.perform(put('/guests/{id}', guestCreated.id).contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isOk())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.userId == homerSimpson.id
        content.indConfirmPresence == false
    }

    def "Find all by Event ID"() {
        given:
        repository.save(new Guest(event, homerSimpson, true))
        repository.save(new Guest(event, bartSimpson, false))

        when:
        def response = mockMvc.perform(get('/guests/{id}', event.id))
                .andExpect(status().isOk())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content != null
        content.size() == 2
    }



}
