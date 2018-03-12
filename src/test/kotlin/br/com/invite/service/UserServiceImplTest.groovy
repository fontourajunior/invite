package br.com.invite.service

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.exception.BusinessException
import br.com.invite.repository.UserRepository
import br.com.invite.resource.request.CreateUserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
@Transactional
class UserServiceImplTest extends Specification {

    @Autowired
    UserService userService

    @Autowired
    UserRepository userRepository

    void setup() {
    }

    void cleanup() {
    }

    def "should create a new user"() {
        given:
            def name = Name.of("Name")
            def phone = Phone.of(324989328)
            def password = Password.of("asdf")
            def request = new CreateUserRequest(name, phone, password)

        when:
            def userCreated = userService.save(request)

        then:
            assert userCreated != null
            assert userCreated.name == name
            assert userCreated.phone == phone
            assert userCreated.password == password
    }

    def "should throw exception duplicating name"() {
        given:
            def request = new CreateUserRequest(Name.of("Name"), Phone.of(324989328), Password.of("asdf"))
            def request2 = new CreateUserRequest(Name.of("Name"), Phone.of(324989328), Password.of("asdf"))

        when:
            userService.save(request)
            userService.save(request2)

        then:
            thrown(BusinessException)
    }



    def "should update a user"() {
        given:
            def request = new CreateUserRequest(Name.of("Name"), Phone.of(324989328), Password.of("asdf"))

        when:
            def userCreated = userService.save(request)
            def userUpdated = userCreated.update(userRepository, Name.of("Name Updated"), Phone.of(999444885), Password.of("#updated"))

        then:
            assert userUpdated != null
            assert userUpdated.name == Name.of("Name Updated")
            assert userUpdated.phone == Phone.of(999444885)
            assert userUpdated.password == Password.of("#updated")


    }

    def "should find one user for id"() {

        given:
        def request = new CreateUserRequest(Name.of("Name"), Phone.of(324989328), Password.of("asdf"))

        when:
        def userCreated = userService.save(request)
        def userFound = userService.findOne(userCreated.id)

        then:
        assert userFound != null
        assert userFound.name == Name.of("Name")
        assert userFound.phone == Phone.of(324989328)
        assert userFound.password == Password.of("asdf")

    }

    def "FindAll"() {
        given:
            def request = new CreateUserRequest(Name.of("Name"), Phone.of(324989328), Password.of("asdf"))
            def request1 = new CreateUserRequest(Name.of("Name 2"), Phone.of(324989328), Password.of("asdf"))
            def request2 = new CreateUserRequest(Name.of("Name 3"), Phone.of(324989328), Password.of("asdf"))

        when:
            userService.save(request)
            userService.save(request1)
            userService.save(request2)

            def users = userService.findAll()

        then:
        assert users != null
        assert users.size() == 3
    }
}
