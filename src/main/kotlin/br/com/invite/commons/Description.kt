package br.com.invite.commons

import br.com.invite.utils.EmbeddedEntity
import br.com.invite.validator.Validator.validate
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@EmbeddedEntity
data class Description(@field:[Valid NotNull NotBlank] val description: String) {

    init {
        validate(this)
    }

}