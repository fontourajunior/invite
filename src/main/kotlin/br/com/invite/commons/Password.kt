package br.com.invite.commons

import br.com.invite.utils.EmbeddedEntity
import br.com.invite.validator.Validator.validate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@EmbeddedEntity
data class Password(@field:[NotNull NotBlank Size(min = 6, max = 20)] val password: String) {

    init {
        validate(this)
    }

}