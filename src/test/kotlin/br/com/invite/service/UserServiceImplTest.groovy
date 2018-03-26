package br.com.invite.service

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.user.User
import br.com.invite.repository.UserRepository
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

    @Autowired UserRepository repository

    def "should find one user for id"() {

        given:
        def userOne = repository.save(new User(new Name("Qui-Gon Jinn"), new Phone(9955855776), new Password("Senh@")))

        when:
        def userFound = userService.findOne(userOne.id)

        then:
        assert userFound != null
        assert userFound.name == new Name("Qui-Gon Jinn")
        assert userFound.phone == new Phone(9955855776)

    }

    def "FindAll"() {
        given:
        repository.save(new User(new Name("Qui-Gon Jinn"), new Phone(9955855776), new Password("Senh@")))
        repository.save(new User(new Name("Obi-Wan Kenobi"), new Phone(8855855776), new Password("Senh@")))
        repository.save(new User(new Name("Yoda"), new Phone(774455776), new Password("Senh@")))

        when:
        def users = userService.findAll()

        then:
        assert users != null
        assert users.size() == 3
    }

}
