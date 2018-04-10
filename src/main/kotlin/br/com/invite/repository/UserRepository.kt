package br.com.invite.repository

import br.com.invite.commons.Name
import br.com.invite.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

    fun countUserByName
            (name: Name): Int

}