package br.com.invite.domain.user

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.core.BaseEntity
import br.com.invite.domain.event.Event
import br.com.invite.repository.UserRepository
import br.com.invite.validator.UserValidator
import javax.persistence.CascadeType
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "user", schema = "public")
class User(@Embedded var name: Name,
           @Embedded var phone: Phone,
           @Embedded var password: Password,
           @OneToMany(mappedBy = "user",
                      fetch = FetchType.EAGER,
                      cascade = [(CascadeType.ALL)])
           var events: MutableList<Event> = ArrayList()) : BaseEntity() {

    fun save(userRepository: UserRepository, validator: UserValidator): User {
        validator.validateCreateUser(this.name)
        return userRepository.save(this)
    }

    fun update(userRepository: UserRepository, validator: UserValidator, name: Name, phone: Phone, password: Password): User {

        validator.validateUpdatedUser(name)

        this.name = name
        this.phone = phone
        this.password = password

        return userRepository.save(this)
    }

}
