package br.com.invite.domain.guest

import br.com.invite.domain.core.BaseEntity
import br.com.invite.domain.event.Event
import br.com.invite.domain.user.User
import br.com.invite.repository.GuestRepository
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "guest", schema = "public")
class Guest(@ManyToOne @NotNull
            var event: Event,

            @ManyToOne @NotNull
            var user: User,

            var indConfirmPresence: Boolean?) : BaseEntity() {

    fun save(guestRepository: GuestRepository): Guest = guestRepository.save(this)

    fun update(guestRepository: GuestRepository,
               indConfirmPresence: Boolean): Guest {

        this.indConfirmPresence = indConfirmPresence

        return guestRepository.save(this)
    }

}