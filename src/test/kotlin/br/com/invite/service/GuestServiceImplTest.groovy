package br.com.invite.service

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
class GuestServiceImplTest extends Specification {

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

    def "must find all invitees by event id"() {

        given:
        def guestToSaveOne = new Guest(event, homerSimpson, true)
        def guestToSaveTwo = new Guest(event, bartSimpson, false)
        guestToSaveOne.save(repository)
        guestToSaveTwo.save(repository)

        when:
        def guests = guestService.findAllByEvent(event.getId())

        then:
        assert guests != null
        assert guests.size() == 2
    }

}
