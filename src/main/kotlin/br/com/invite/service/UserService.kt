package br.com.invite.service

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import br.com.invite.domain.user.User
import br.com.invite.exception.NotFoundException
import br.com.invite.repository.UserRepository
import br.com.invite.resource.representation.UserRepresentation
import br.com.invite.resource.request.CreateUserRequest
import br.com.invite.resource.request.UpdateUserRequest
import br.com.invite.validator.UserValidator
import org.springframework.stereotype.Service

interface UserService {
    fun save(createUser: CreateUserRequest): UserRepresentation
    fun findOne(id: Long): UserRepresentation
    fun findAll(): List<UserRepresentation>
    fun update(id: Long, createUser: UpdateUserRequest): UserRepresentation
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val userValidator: UserValidator) : UserService {

    override fun save(createUser: CreateUserRequest): UserRepresentation =
            modelToRepresentation(createUser.toEntity().save(userRepository, userValidator))

    override fun update(id: Long, createUser: UpdateUserRequest): UserRepresentation {
        val userUpdated = findById(id).update(userRepository,
                                              userValidator,
                                              Name(createUser.name),
                                              Phone(createUser.phone),
                                              Password(createUser.password))
        return modelToRepresentation(userUpdated)
    }

    override fun findOne(id: Long): UserRepresentation =
            modelToRepresentation(findById(id))

    override fun findAll(): List<UserRepresentation> {
        val usersAll = userRepository.findAll()
        if (usersAll.isEmpty()) {
            throw NotFoundException()
        }
        return usersAll.map { modelToRepresentation(it) }
    }

    private fun CreateUserRequest.toEntity(): User =
            User(name = Name(name), phone = Phone(phone), password = Password(password))

    private fun modelToRepresentation(user: User): UserRepresentation =
            UserRepresentation(user.id, user.name.name, user.phone.phone)

    private fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow(::NotFoundException)
    }


}
