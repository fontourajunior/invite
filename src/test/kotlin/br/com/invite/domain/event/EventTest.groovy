package br.com.invite.domain.event

import br.com.invite.commons.Description
import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.user.User
import br.com.invite.exception.BusinessException
import br.com.invite.repository.EventRepository
import br.com.invite.repository.UserRepository
import br.com.invite.validator.EventValidator
import br.com.invite.validator.UserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
@Transactional
class EventTest extends Specification {

    @Autowired
    EventRepository repository

    @Autowired
    UserRepository userRepository

    @Autowired
    EventValidator validator

    private def User user

    def setup() {
        user = userRepository.save(new User(new Name("Homer Simpson"),
                new Phone("34324989328"),
                new Password("password123"),
                new ArrayList()))
    }


    def "should to save a new event"() {

        given:
        def name = new Name("Birthday Party")
        def description = new Description("Birthday Party")
        def date = Timestamp.from(Instant.now())
        def eventToSave = new Event(name, description, date, true, user)

        when:
        def eventCreated = eventToSave.save(repository, validator)

        then:
        assert eventCreated != null
        assert eventCreated.name == name
        assert eventCreated.description == description
        assert eventCreated.date == date
        assert eventCreated.indConfirmPresence
        assert eventCreated.user == user

    }

    def "should throw a Business Exception of duplicated name and date when to save"() {

        given:
        def date = Timestamp.from(Instant.now())
        def eventOne = new Event(new Name("Birthday One"), new Description("Test"), date, true, user)
        def eventTwo = new Event(new Name("Birthday One"), new Description("Test"), date, true, user)

        when:
        eventOne.save(repository, validator)
        eventTwo.save(repository, validator)

        then:
        thrown(BusinessException)
    }

    def "should to update a event"() {

        given:
        def event = buildEvent()
        def date = Timestamp.from(Instant.now())

        when:
        def eventCreated = event.save(repository, validator)
        def eventUpdated = eventCreated.update(repository,
                validator,
                new Name("Event"),
                new Description("Description"),
                date,
                true)

        then:
        assert eventUpdated != null
        assert eventUpdated.name == new Name("Event")
        assert eventUpdated.description == new Description("Description")
        assert eventUpdated.date == date
        assert eventUpdated.indConfirmPresence

    }

    def "should throw a Business Exception of duplicated name and date when to update"() {

        given:
        def date = Timestamp.from(Instant.now())
        def eventOne = new Event(new Name("Birthday One"), new Description("Test"), date, true, user)
        def eventTwo = new Event(new Name("Birthday Two"), new Description("Test"), date, true, user)

        when:
        eventOne.save(repository, validator)
        def eventSaved = eventTwo.save(repository, validator)

        eventSaved.update(repository, validator, new Name("Birthday One"), new Description("Test"), date, true)

        then:
        thrown(BusinessException)
    }

    private def buildEvent = {
        def name = new Name("Birthday Party")
        def description = new Description("Birthday Party")
        def date = Timestamp.from(Instant.now())
        return new Event(name, description, date, true, user)
    }

}
