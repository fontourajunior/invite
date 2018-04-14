package br.com.invite.validator

import br.com.invite.commons.Name
import br.com.invite.exception.BusinessException
import br.com.invite.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface UserValidator {
    fun validateCreateUser(name: Name)
    fun validateUpdatedUser(name: Name)
}

@Service
class UserValidatorImpl @Autowired constructor(private val userRepository: UserRepository) : UserValidator {

    override fun validateCreateUser(name: Name) {
        if (userRepository.countUserByName(name) > 0) {
            throw BusinessException("Duplicated Name.")
        }
    }

    override fun validateUpdatedUser(name: Name) {
        validateCreateUser(name)
    }

}