package br.com.invite.resource

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.event.Event
import br.com.invite.domain.user.User
import br.com.invite.repository.UserRepository
import br.com.invite.resource.request.CreateUserRequest
import br.com.invite.service.UserService
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
@Transactional
class UserControllerTest extends Specification {

    MockMvc mockMvc

    @Autowired
    UserService userService

    @Autowired
    UserRepository userRepository

    def setup() {
        mockMvc = standaloneSetup(new UserController(userService)).build()
        userRepository.deleteAll()
    }

    def "Create"() {
        given:
        CreateUserRequest userRequest = new CreateUserRequest("Anakin Skywalker", "342398855776", "Senh@123")
        def jsonContent = new JsonBuilder(userRequest).toPrettyString()

        when:
        def response = mockMvc.perform(post('/users').contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.id == 1
        content.name == "Anakin Skywalker"
        content.phone == "342398855776"
    }

    def "Update"() {
        given:
        def userCreated = userRepository.save(new User(new Name("Qui-Gon Jinn"), new Phone("349955855776"), new Password("Senh@123"), new ArrayList<Event>()))
        CreateUserRequest userRequest = new CreateUserRequest("Lord Dukan", "3422223333", "SenAlt&rada")
        def jsonContent = new JsonBuilder(userRequest).toPrettyString()

        when:
        def response = mockMvc.perform(put('/users/{id}', userCreated.id).contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isOk())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.id == userCreated.id
        content.name == "Lord Dukan"
        content.phone == "3422223333"
    }

    def "FindOne"() {
        given:
        def userCreated = userRepository.save(new User(new Name("Qui-Gon Jinn"), new Phone("349955855776"), new Password("Senh@123"), new ArrayList<Event>()))

        when:
        def response = mockMvc.perform(get('/users/{id}', userCreated.id))
                .andExpect(status().isOk())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.id == userCreated.id
        content.name == "Qui-Gon Jinn"
        content.phone == "349955855776"
    }

    def "FindAll"() {
        given:
        def userOne = userRepository.save(new User(new Name("Qui-Gon Jinn"), new Phone("349955855776"), new Password("Senh@123"), new ArrayList<Event>()))
        def userTwo = userRepository.save(new User(new Name("Obi-Wan Kenobi"), new Phone("348855855776"), new Password("Senh@123"), new ArrayList<Event>()))
        def userThree = userRepository.save(new User(new Name("Yoda"), new Phone("34774455776"), new Password("Senh@123"), new ArrayList<Event>()))

        when:
        def response = mockMvc.perform(get('/users'))
                .andExpect(status().isOk())
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.size == 3

        content[0].id == userOne.id
        content[0].name == "Qui-Gon Jinn"
        content[0].phone == "349955855776"

        content[1].id == userTwo.id
        content[1].name == "Obi-Wan Kenobi"
        content[1].phone == "348855855776"

        content[2].id == userThree.id
        content[2].name == "Yoda"
        content[2].phone == "34774455776"
    }
}
