package br.com.invite.service

import br.com.invite.commons.Description
import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.event.Event
import br.com.invite.domain.user.User
import br.com.invite.repository.EventRepository
import br.com.invite.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.sql.Timestamp

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
@Transactional
class EventServiceImplTest extends Specification {

    @Autowired
    EventService eventService

    @Autowired
    EventRepository eventRepository

    @Autowired
    UserRepository userRepository

    private User user

    private Timestamp date

    def setup() {
        user = userRepository.save(new User(new Name("Bart Simpson"),
                new Phone("34324989328"),
                new Password("password123"),
                new ArrayList<Event>()))
        date = Timestamp.valueOf("2018-01-01 00:00:00.0")
    }

    def "should find one event for id"() {

        given:
        def event = eventRepository.save(new Event(new Name("Event Name"), new Description("Event description"), date, true, user))

        when:
        def eventFound = eventService.findOne(event.id)

        then:
        assert eventFound != null
        assert eventFound.name == "Event Name"
        assert eventFound.description == "Event description"
        assert eventFound.date == date
        assert eventFound.indConfirmPresence

    }

    def "should find all events"() {

        given:
        eventRepository.save(new Event(new Name("Event One"), new Description("Event one description"), date, true, user))
        eventRepository.save(new Event(new Name("Event Two"), new Description("Event two description"), date, false, user))

        when:
        def events = eventService.findAll()

        then:
        assert events != null
        assert events.size() == 2

        assert events[0].name == "Event One"
        assert events[0].description == "Event one description"
        assert events[0].date == date
        assert events[0].indConfirmPresence

        assert events[1].name == "Event Two"
        assert events[1].description == "Event two description"
        assert events[1].date == date
        assert !events[1].indConfirmPresence

    }

}
