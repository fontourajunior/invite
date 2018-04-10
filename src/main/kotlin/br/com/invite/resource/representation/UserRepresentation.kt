package br.com.invite.resource.representation

import br.com.invite.domain.user.User

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class UserRepresentation(val id: Long,
                              val name: String,
                              val phone: String,
                              val events: List<EventRepresentation> = listOf()) {

    companion object {
        fun buildModelToRepresentation(user: User): UserRepresentation {
            return UserRepresentation(user.id,
                                      user.name.name,
                                      user.phone.phone,
                                      user.events.map { EventRepresentation.modelToEventRepresentation(it) })
        }
    }

}

