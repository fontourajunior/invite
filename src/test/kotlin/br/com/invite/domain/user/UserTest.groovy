package br.com.invite.domain.user

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.exception.BusinessException
import br.com.invite.repository.UserRepository
import br.com.invite.validator.UserValidator
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
class UserTest extends Specification {

    @Autowired
    UserRepository repository

    @Autowired
    UserValidator validator

    def "should to save a new user"() {

        given:
        def name = new Name("Homer Simpson")
        def phone = new Phone("34324989328")
        def password = new Password("password123")
        def userToSave = new User(name, phone, password)

        when:
        def userCreated = userToSave.save(repository, validator)

        then:
        assert userCreated != null
        assert userCreated.name == name
        assert userCreated.phone == phone
        assert userCreated.password == password

    }

    def "should throw a Business Exception of duplicated name when to save"() {
        given:
        def userOne = new User(new Name("Bart Simpson"), new Phone("34324989328"), new Password("password123"))
        def userTwo = new User(new Name("Bart Simpson"), new Phone("34324989328"), new Password("password123"))

        when:
        userOne.save(repository, validator)
        userTwo.save(repository, validator)

        then:
        thrown(BusinessException)
    }

    def "should to update a user"() {

        given:
        def user = buildUser()

        when:
        def userCreated = user.save(repository, validator)
        def userUpdated = userCreated.update(repository,
                validator,
                new Name("Homer"),
                new Phone("34999444885"),
                new Password("#updated"))

        then:
        assert userUpdated != null
        assert userUpdated.name == new Name("Homer")
        assert userUpdated.phone == new Phone("34999444885")
        assert userUpdated.password == new Password("#updated")

    }

    def "should throw a Business Exception of duplicated name when to update"() {
        given:
        def userOne = new User(new Name("Homer Simpson"), new Phone("34324989328"), new Password("senha123"))
        def userTwo = new User(new Name("Marge Simpson"), new Phone("34324989328"), new Password("senha123"))

        when:
        userOne.save(repository, validator)
        def userSaved = userTwo.save(repository, validator)

        userSaved.update(repository, validator, new Name("Homer Simpson"), new Phone("34324989328"), new Password("senha123"))


        then:
        thrown(BusinessException)
    }

    private def buildUser = {
        def name = new Name("Bart Simpson")
        def phone = new Phone("34324989328")
        def password = new Password("password123")
        return new User(name, phone, password)
    }
}
