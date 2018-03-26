package br.com.invite.resource.request

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import javax.validation.Valid
import javax.validation.constraints.NotNull

data class CreateUserRequest(@field:[Valid NotNull] val name: Name, @field:[Valid NotNull] val phone: Phone, @field:[Valid NotNull] val password: Password)

data class UpdateUserRequest(val name: Name, val phone: Phone, val password: Password)