package br.com.invite.domain.event

import br.com.invite.commons.Description
import br.com.invite.commons.Name
import br.com.invite.domain.core.BaseEntity
import br.com.invite.domain.user.User
import br.com.invite.repository.EventRepository
import br.com.invite.validator.EventValidator
import java.sql.Timestamp
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "event", schema = "public")
class Event(@Embedded
            var name: Name,
            @Embedded
            var description: Description,
            @NotNull
            var date: Timestamp,
            @NotNull
            var indConfirmPresence: Boolean,
            @ManyToOne @JoinColumn(name = "user_id")
            var user: User) : BaseEntity() {


    fun save(eventRepository: EventRepository, eventValidator: EventValidator): Event =
            eventValidator.validateCreate(this.name, this.date).let { eventRepository.save(this) }


    fun update(eventRepository: EventRepository,
               eventValidator: EventValidator,
               name: Name,
               description: Description,
               date: Timestamp,
               indConfirmPresence: Boolean): Event {

        eventValidator.validateUpdated(name, date)

        this.name = name
        this.description = description
        this.date = date
        this.indConfirmPresence = indConfirmPresence

        return eventRepository.save(this)
    }

}