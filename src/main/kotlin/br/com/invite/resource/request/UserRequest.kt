package br.com.invite.resource.request

import br.com.invite.commons.Name
import br.com.invite.commons.Password
import br.com.invite.commons.Phone
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotNull

data class CreateUserRequest @JsonCreator constructor(
        @JsonProperty("name") @field: [Valid NotNull] val name: Name,
        @JsonProperty("phone") @field: [Valid NotNull] val phone: Phone,
        @JsonProperty("password") @field: [Valid] val password: Password)

data class UpdateUserRequest @JsonCreator constructor(
        @JsonProperty("name") @field: [Valid NotNull] val name: Name,
        @JsonProperty("phone") @field: [Valid NotNull] val phone: Phone,
        @JsonProperty("password") @field: [Valid] val password: Password)