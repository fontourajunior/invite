package br.com.invite.resource.request

import javax.validation.Valid
import javax.validation.constraints.NotNull

data class CreateGuestRequest(@field:[Valid NotNull] val eventId: Long,
                              @field:[Valid NotNull] val userId: Long,
                              @field:[Valid NotNull] val indConfirmPresence: Boolean)

data class UpdateGuestRequest(@field:[Valid NotNull] val indConfirmPresence: Boolean)