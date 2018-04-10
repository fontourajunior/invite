package br.com.invite.resource.representation

import br.com.invite.domain.event.Event
import java.sql.Timestamp

data class EventRepresentation(val id: Long,
                               val userId: Long,
                               val name: String,
                               val description: String,
                               val date: Timestamp,
                               val indConfirmPresence: Boolean) {

    companion object {

        fun modelToEventRepresentation(event: Event): EventRepresentation {
            return EventRepresentation(event.id,
                                       event.user.id,
                                       event.name.name,
                                       event.description.description,
                                       event.date,
                                       event.indConfirmPresence)
        }

    }
}