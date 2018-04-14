package br.com.invite.repository

import br.com.invite.commons.Name
import br.com.invite.domain.event.Event
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventRepository: JpaRepository<Event, Long> {

    fun countEventByNameAndDate(name: Name, date: Date) : Long

}