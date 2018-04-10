package br.com.invite.repository

import br.com.invite.domain.event.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository: JpaRepository<Event, Long> {
}