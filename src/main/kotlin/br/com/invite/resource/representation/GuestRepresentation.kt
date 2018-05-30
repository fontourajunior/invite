package br.com.invite.resource.representation

import br.com.invite.domain.guest.Guest

data class GuestRepresentation(val id: Long,
                               val eventId: Long,
                               val userId: Long,
                               val indConfirmPresence: Boolean?) {

    companion object {
        fun modelToGuestRepresentation(guest: Guest): GuestRepresentation {
            return GuestRepresentation(guest.id,
                                       guest.event.id,
                                       guest.user.id,
                                       guest.indConfirmPresence)
        }

    }
}