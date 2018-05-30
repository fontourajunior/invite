package br.com.invite.domain.guest

import br.com.invite.commons.Description
import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.event.Event
import br.com.invite.domain.user.User
import br.com.invite.repository.EventRepository
import br.com.invite.repository.GuestRepository
import br.com.invite.repository.UserRepository
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
class GuestTest extends Specification {

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

    def "should to save a new guest"() {

        given:
        def guestToSaveOne = new Guest(event, homerSimpson, true)
        def guestToSaveTwo = new Guest(event, bartSimpson, false)

        when:
        def guestCreatedOne = guestToSaveOne.save(repository)
        def guestCreatedTwo = guestToSaveTwo.save(repository)


        then:
        assert guestCreatedOne != null
        assert guestCreatedOne.event == event
        assert guestCreatedOne.user == homerSimpson
        assert guestCreatedOne.indConfirmPresence

        assert guestCreatedTwo != null
        assert guestCreatedTwo.event == event
        assert guestCreatedTwo.user == bartSimpson
        assert !guestCreatedTwo.indConfirmPresence

    }

    def "should to update a guest"() {

        given:
        def guestToSaveOne = new Guest(event, homerSimpson, true)
        def guestToSaveTwo = new Guest(event, bartSimpson, false)

        when:
        def guestCreatedOne = guestToSaveOne.save(repository)
        def guestCreatedTwo = guestToSaveTwo.save(repository)

        def guestUpdated = guestCreatedTwo.update(repository, true)

        then:
        assert guestCreatedOne != null
        assert guestCreatedOne.event == event
        assert guestCreatedOne.user == homerSimpson
        assert guestCreatedOne.indConfirmPresence

        assert guestUpdated != null
        assert guestUpdated.event == event
        assert guestUpdated.user == bartSimpson
        assert guestUpdated.indConfirmPresence

    }

}
