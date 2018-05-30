package br.com.invite.service

import br.com.invite.commons.Description
import br.com.invite.commons.Name
import br.com.invite.domain.event.Event
import br.com.invite.exception.NotFoundException
import br.com.invite.repository.EventRepository
import br.com.invite.resource.representation.EventRepresentation
import br.com.invite.resource.representation.EventRepresentation.Companion.modelToEventRepresentation
import br.com.invite.resource.request.CreateEventRequest
import br.com.invite.resource.request.UpdateEventRequest
import br.com.invite.validator.EventValidator
import org.springframework.stereotype.Service

interface EventService {
    fun save(eventRequest: CreateEventRequest): EventRepresentation
    fun findOne(id: Long): EventRepresentation
    fun findAll(): List<EventRepresentation>
    fun update(id: Long, updateEvent: UpdateEventRequest): EventRepresentation
    fun findById(id: Long): Event
}

@Service
class EventServiceImpl(private val eventRepository: EventRepository,
                       private val eventValidator: EventValidator,
                       private val userService: UserService) : EventService {

    override fun save(createEvent: CreateEventRequest): EventRepresentation {
        return modelToEventRepresentation(createEvent.toEntity().save(eventRepository, eventValidator))
    }

    override fun update(id: Long, updateEvent: UpdateEventRequest): EventRepresentation {
        val eventUpdated = findById(id).update(eventRepository,
                                               eventValidator,
                                               Name(updateEvent.name),
                                               Description(updateEvent.description),
                                               updateEvent.date,
                                               updateEvent.indConfirmPresence)

        return modelToEventRepresentation(eventUpdated)
    }

    override fun findOne(id: Long): EventRepresentation =
            modelToEventRepresentation(findById(id))

    override fun findAll(): List<EventRepresentation> =
            eventRepository.findAll().map { modelToEventRepresentation(it) }

    override fun findById(id: Long): Event =
            eventRepository.findById(id).orElseThrow(::NotFoundException)

    private fun findUserById(userId: Long) = userService.findById(userId)

    private fun CreateEventRequest.toEntity() =
            Event(Name(name), Description(description), date, indConfirmPresence, findUserById(userId))


}