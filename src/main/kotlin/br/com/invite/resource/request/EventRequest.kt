package br.com.invite.resource.request

import br.com.invite.domain.guest.Guest
import br.com.invite.resource.representation.GuestRepresentation
import java.sql.Timestamp
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreateEventRequest(@field:[Valid NotNull] val userId: Long,
                              @field:[Valid NotNull NotBlank] val name: String,
                              @field:[Valid NotNull NotBlank] val description: String,
                              @field:[Valid NotNull] val date: Timestamp,
                              @field:[Valid NotNull] val indConfirmPresence: Boolean) {

    companion object {
        fun modelToGuestRepresentation(guest: Guest): GuestRepresentation {
            return GuestRepresentation(guest.id,
                                       guest.event.id,
                                       guest.user.id,
                                       guest.indConfirmPresence)
        }

    }

}

data class UpdateEventRequest(@field:[Valid NotNull NotBlank] val name: String,
                              @field:[Valid NotNull NotBlank] val description: String,
                              @field:[Valid NotNull] val date: Timestamp,
                              @field:[Valid NotNull] val indConfirmPresence: Boolean)

