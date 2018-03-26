package br.com.invite.commons

import br.com.invite.validator.CustomModel
import br.com.invite.validator.CustomValidation
import com.fasterxml.jackson.annotation.JsonValue
import java.io.Serializable

@CustomValidation(min = 5, max = 30)
class Phone : Serializable, CustomModel<Long> {

    var phone: Long = 0

    constructor(phone: Long) {
        this.phone = phone
    }

    private constructor()

    @JsonValue
    override fun getDefaultValue(): Long = this.phone

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Phone

        if (phone != other.phone) return false

        return true
    }

    override fun hashCode(): Int {
        return phone.hashCode()
    }


}