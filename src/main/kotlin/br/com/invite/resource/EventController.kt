package br.com.invite.resource

import br.com.invite.resource.representation.EventRepresentation
import br.com.invite.resource.representation.UserRepresentation
import br.com.invite.resource.request.CreateEventRequest
import br.com.invite.resource.request.UpdateEventRequest
import br.com.invite.resource.request.UpdateUserRequest
import br.com.invite.service.EventService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/events")
class EventController(private val eventService: EventService) {

    @ResponseStatus(CREATED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun save(@Valid @RequestBody eventRequest: CreateEventRequest): EventRepresentation {
        return eventService.save(eventRequest)
    }

    @ResponseStatus(OK)
    @PutMapping(path = ["{id}"], consumes = [APPLICATION_JSON_VALUE])
    fun update(@PathVariable id: Long,
               @RequestBody updateEventRequest: UpdateEventRequest): EventRepresentation {
        return eventService.update(id, updateEventRequest)
    }

    @ResponseStatus(OK)
    @GetMapping("{id}")
    fun findOne(@PathVariable id: Long) : EventRepresentation = eventService.findOne(id)

    @ResponseStatus(OK)
    @GetMapping
    fun findAll(): List<EventRepresentation> = eventService.findAll()

}