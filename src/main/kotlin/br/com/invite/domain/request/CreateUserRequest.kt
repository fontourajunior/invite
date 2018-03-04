package br.com.invite.domain.request

import br.com.invite.commons.Name
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotNull

data class CreateUserRequest @JsonCreator constructor(
        @JsonProperty("name") @field: [Valid NotNull] val name: Name,
        @JsonProperty("phone") @field: [Valid NotNull] val phone: Int,
        @JsonProperty("password") @field: [Valid] val password: String)

data class UpdateUserRequest @JsonCreator constructor(
        @JsonProperty("name") @field: [Valid NotNull] val name: Name,
        @JsonProperty("phone") @field: [Valid NotNull] val phone: Int,
        @JsonProperty("password") @field: [Valid] val password: String)