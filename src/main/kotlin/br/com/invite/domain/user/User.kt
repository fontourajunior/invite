package br.com.invite.domain.user

import br.com.invite.repository.UserRepository
import br.com.invite.commons.Name
import br.com.invite.domain.core.BaseEntity
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
           @Column @Embedded @NotNull var name: Name,
           var phone: Int = 0,
           var password: String = "") : BaseEntity() {

    companion object {
        fun save(userRepository: UserRepository,
                 name: Name,
                 phone: Int,
                 password: String) {
            val userToSave = User(name = name, phone = phone, password = password)
            userRepository.save(userToSave)
        }
    }

    fun update(userRepository: UserRepository,
               id: Long,
               name: Name,
               phone: Int,
               password: String) {

        this.beforeUpdate()
        this.id = id
        this.name = name
        this.phone = phone
        this.password = password
        userRepository.save(this)
    }

}