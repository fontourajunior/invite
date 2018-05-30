package br.com.invite.commons

import br.com.invite.utils.EmbeddedEntity
import br.com.invite.validator.Validator.validate
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@EmbeddedEntity
data class Phone(@field:[NotNull NotBlank Size(min = 10, max = 14)
                 Pattern(message = "Informe apenas números", regexp = "^[0-9]{1,45}\$")]
                 val phone: String) {

    init {
        validate(this)
    }
}