package br.com.invite.service

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.user.User
import br.com.invite.exception.NotFoundException
import br.com.invite.repository.UserRepository
import br.com.invite.resource.representation.UserRepresentation
import br.com.invite.resource.representation.UserRepresentation.Companion.buildModelToRepresentation
import br.com.invite.resource.request.CreateUserRequest
import br.com.invite.resource.request.UpdateUserRequest
import br.com.invite.validator.UserValidator
import org.springframework.stereotype.Service

interface UserService {
    fun save(createUser: CreateUserRequest): UserRepresentation
    fun findOne(id: Long): UserRepresentation
    fun findAll(): List<UserRepresentation>
    fun update(id: Long, createUser: UpdateUserRequest): UserRepresentation
    fun findById(id: Long): User
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val userValidator: UserValidator) : UserService {


    override fun save(createUser: CreateUserRequest): UserRepresentation =
            buildModelToRepresentation(createUser.toEntity().save(userRepository, userValidator))

    override fun update(id: Long, createUser: UpdateUserRequest): UserRepresentation {
        val userUpdated = findById(id).update(userRepository,
                                              userValidator,
                                              Name(createUser.name),
                                              Phone(createUser.phone),
                                              Password(createUser.password))
        return UserRepresentation.buildModelToRepresentation(userUpdated)
    }

    override fun findOne(id: Long): UserRepresentation =
            buildModelToRepresentation(findById(id))

    override fun findAll(): List<UserRepresentation> =
            userRepository.findAll().map { buildModelToRepresentation(it) }

    private fun CreateUserRequest.toEntity(): User =
            User(name = Name(name), phone = Phone(phone), password = Password(password))

    override fun findById(id: Long): User =
            userRepository.findById(id).orElseThrow(::NotFoundException)


}
