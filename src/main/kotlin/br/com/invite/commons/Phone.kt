package br.com.invite.commons

import br.com.invite.validator.TypesafeModel
import br.com.invite.validator.TypesafeValid
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.io.Serializable

@TypesafeValid(min = 5, max = 30)
class Phone : Serializable, TypesafeModel<Long> {

    var phone: Long = 0

    private constructor(phone: Long) {
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

    companion object {
        @JsonCreator
        @JvmStatic
        fun of(@JsonProperty("phone") phone: Long)= Phone(phone)
    }
}