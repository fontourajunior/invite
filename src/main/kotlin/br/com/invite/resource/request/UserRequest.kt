package br.com.invite.resource.request

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone

data class CreateUserRequest(val name: Name, val phone: Phone, val password: Password)

data class UpdateUserRequest(val name: Name, val phone: Phone, val password: Password)