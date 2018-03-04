package br.com.invite.service

import br.com.invite.repository.UserRepository
import br.com.invite.domain.user.User
import br.com.invite.domain.request.CreateUserRequest
import br.com.invite.domain.request.UpdateUserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface UserService {
    fun save(createUser: CreateUserRequest)
    fun findOne(id: Long): User
    fun findAll(): List<User>
    fun update(id: Long, createUser: UpdateUserRequest)
}

@Service
class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserService {

    override fun save(createUserRequest: CreateUserRequest) {
        User.save(userRepository, createUserRequest.name, createUserRequest.phone, createUserRequest.password)
    }

    override fun update(id: Long, updateUserRequest: UpdateUserRequest) {
        val userRestored = userRepository.findOne(id)
        userRestored.update(userRepository, id, updateUserRequest.name, updateUserRequest.phone, updateUserRequest.password)
    }

    override fun findOne(id: Long): User {
        return userRepository.findOne(id)
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()

    }

}
