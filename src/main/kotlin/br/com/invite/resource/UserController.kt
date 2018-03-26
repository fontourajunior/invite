package br.com.invite.resource

import br.com.invite.domain.user.User
import br.com.invite.resource.representation.UserRepresentation
import br.com.invite.resource.request.CreateUserRequest
import br.com.invite.resource.request.UpdateUserRequest
import br.com.invite.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @ResponseStatus(CREATED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun create(@Valid @RequestBody createUser: CreateUserRequest): UserRepresentation {
        return userService.save(createUser)
    }

    @ResponseStatus(OK)
    @PutMapping(path = ["{id}"], consumes = [APPLICATION_JSON_VALUE])
    fun update(@PathVariable id: Long,
               @RequestBody createUser: UpdateUserRequest): UserRepresentation {
        return userService.update(id, createUser)
    }

    @ResponseStatus(OK)
    @GetMapping("{id}")
    fun findOne(@PathVariable id: Long) : UserRepresentation = userService.findOne(id)

    @ResponseStatus(OK)
    @GetMapping
    fun findAll(): List<UserRepresentation> = userService.findAll()


}