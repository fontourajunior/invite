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
class User(@Embedded val name: Name, @Embedded val phone: Phone, @Embedded val password: Password) : BaseEntity<Long>() {

    fun save(userRepository: UserRepository): User =
            userRepository.save(this)

    fun update(userRepository: UserRepository): User =
        userRepository.save(this)

}