package br.com.invite.service

import br.com.invite.domain.guest.Guest
import br.com.invite.exception.NotFoundException
import br.com.invite.repository.GuestRepository
import br.com.invite.resource.representation.GuestRepresentation
import br.com.invite.resource.representation.GuestRepresentation.Companion.modelToGuestRepresentation
import br.com.invite.resource.request.CreateGuestRequest
import br.com.invite.resource.request.UpdateGuestRequest
import org.springframework.stereotype.Service

interface GuestService {
    fun save(createGuestRequest: CreateGuestRequest): GuestRepresentation
    fun update(id: Long, updateGuestRequest: UpdateGuestRequest): GuestRepresentation
    fun findOne(id: Long): GuestRepresentation
    fun findAllByEvent(eventId: Long): List<GuestRepresentation>
}

@Service
class GuestServiceImpl(private val guestRepository: GuestRepository,
                       private val eventService: EventService,
                       private val userService: UserService) : GuestService {

    override fun save(createGuest: CreateGuestRequest): GuestRepresentation =
            modelToGuestRepresentation(createGuest.toEntity().save(guestRepository))

    override fun update(id: Long, updateGuestRequest: UpdateGuestRequest): GuestRepresentation =
            modelToGuestRepresentation(
                    findById(id).update(guestRepository, updateGuestRequest.indConfirmPresence))

    override fun findOne(id: Long): GuestRepresentation =
            modelToGuestRepresentation(findById(id))

    override fun findAllByEvent(eventId: Long): List<GuestRepresentation> =
        guestRepository.findAllByEventId(eventId).map { modelToGuestRepresentation(it) }

    private fun findById(id: Long): Guest =
            guestRepository.findById(id).orElseThrow(::NotFoundException)

    private fun findEventById(eventId: Long) = eventService.findById(eventId)

    private fun findUserById(userId: Long) = userService.findById(userId)

    private fun CreateGuestRequest.toEntity() = Guest(findEventById(eventId), findUserById(userId), indConfirmPresence)

}
