package br.com.invite.resource

import br.com.invite.resource.representation.EventRepresentation
import br.com.invite.resource.representation.GuestRepresentation
import br.com.invite.resource.request.CreateEventRequest
import br.com.invite.resource.request.CreateGuestRequest
import br.com.invite.resource.request.UpdateEventRequest
import br.com.invite.resource.request.UpdateGuestRequest
import br.com.invite.service.GuestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
@RequestMapping("guests")
class GuestController @Autowired constructor(private val guestService: GuestService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun save(@Valid @RequestBody guestRequest: CreateGuestRequest): GuestRepresentation =
        guestService.save(guestRequest)

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = ["{id}"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun update(@PathVariable id: Long, @RequestBody updateGuestRequest: UpdateGuestRequest): GuestRepresentation =
            guestService.update(id, updateGuestRequest)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{eventId}")
    fun findAllByEventId(@PathVariable eventId: Long): List<GuestRepresentation> = guestService.findAllByEvent(eventId)
}