package br.com.invite.service

import br.com.invite.repository.UserRepository
import br.com.invite.domain.user.User
import br.com.invite.exception.NotFoundException
import br.com.invite.resource.request.CreateUserRequest
import br.com.invite.resource.request.UpdateUserRequest
import br.com.invite.validator.UserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

interface UserService {
    fun save(createUser: CreateUserRequest): User
    fun findOne(id: Long): User
    fun findAll(): List<User>
    fun update(id: Long, createUser: UpdateUserRequest): User
}

@Service
class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository,
                                             private val userValidator: UserValidator) : UserService {

    override fun save(createUserRequest: CreateUserRequest): User {
        userValidator.validateCreateUser(createUserRequest.name)
        return createUserRequest.toEntity().also { it.id = Random().nextLong() }.save(userRepository)
    }

    override fun update(id: Long, updateUserRequest: UpdateUserRequest): User =
            updateUserRequest
                    .toEntity()
                    .also { it.id = id }
                    .update(userRepository)

    override fun findOne(id: Long): User {
        return userRepository.findById(id).get() ?: throw NotFoundException()
    }

    override fun findAll(): List<User> {
        val usersAll = userRepository.findAll()
        if(usersAll.isEmpty()) {
            throw NotFoundException()
        }
        return usersAll
    }

    private fun CreateUserRequest.toEntity(): User =
            User(name = name, phone = phone, password = password)

    private fun UpdateUserRequest.toEntity(): User =
            User(name = name, phone = phone, password = password)

}
