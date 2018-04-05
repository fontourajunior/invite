package br.com.invite.resource.request

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreateUserRequest(@field:[Valid NotNull NotBlank] val name: String,
                             @field:[Valid NotNull NotBlank Size(min = 10, max = 14) Pattern(message = "Informe apenas números", regexp = "^[0-9]{1,45}\$")] val phone: String,
                             @field:[Valid NotNull NotBlank] val password: String)

data class UpdateUserRequest(@field:[Valid NotNull NotBlank] val name: String,
                             @field:[Valid NotNull NotBlank Size(min = 10, max = 14) Pattern(message = "Informe apenas números", regexp = "^[0-9]{1,45}\$")] val phone: String,
                             @field:[Valid NotNull NotBlank] val password: String)

