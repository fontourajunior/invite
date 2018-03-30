package br.com.invite.commons

import br.com.invite.utils.EmbeddedEntity
import br.com.invite.validator.ValidationUtil
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@EmbeddedEntity
data class Test @JsonCreator constructor(@JsonProperty("test") @field:[NotBlank Min(5)] val test: String){

    init {
        ValidationUtil.validate(this)
    }
}