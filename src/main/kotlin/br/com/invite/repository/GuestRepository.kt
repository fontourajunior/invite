package br.com.invite.repository

import br.com.invite.domain.guest.Guest
import org.springframework.data.jpa.repository.JpaRepository

interface GuestRepository: JpaRepository<Guest, Long> {

    fun findAllByEventId(eventId: Long): List<Guest>

}