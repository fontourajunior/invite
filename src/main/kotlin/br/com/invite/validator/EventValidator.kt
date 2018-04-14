package br.com.invite.validator

import br.com.invite.commons.Name
import br.com.invite.exception.BusinessException
import br.com.invite.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp

interface EventValidator {
    fun validateCreate(name: Name, date: Timestamp)
    fun validateUpdated(name: Name, date: Timestamp)
}

@Service
class EventValidatorImpl @Autowired constructor(private val eventRepository: EventRepository) : EventValidator {

    override fun validateCreate(name: Name, date: Timestamp) {
        if (eventRepository.countEventByNameAndDate(name, date) > 0) {
            throw BusinessException("Duplicated Name.")
        }
    }

    override fun validateUpdated(name: Name, date: Timestamp) {
        validateCreate(name, date)
    }

}