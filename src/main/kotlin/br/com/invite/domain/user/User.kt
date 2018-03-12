package br.com.invite.domain.user

import br.com.invite.repository.UserRepository
import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.core.BaseEntity
import org.apache.coyote.http11.Constants.a
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user", schema = "public")
class User(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = 0,
           @Column @Embedded @NotNull var name: Name = Name.of(""),
           @Column @Embedded @NotNull var phone: Phone = Phone.of(0),
           @Column @Embedded @NotNull var password: Password = Password.of("")) : BaseEntity() {

    companion object {
        fun save(userRepository: UserRepository, name: Name, phone: Phone, password: Password): User =
            userRepository.save(User(name = name, phone = phone, password = password))
    }

    fun update(userRepository: UserRepository,
               name: Name,
               phone: Phone,
               password: Password): User {

        this.beforeUpdate()
        this.name = name
        this.phone = phone
        this.password = password
        return userRepository.save(this)
    }

}