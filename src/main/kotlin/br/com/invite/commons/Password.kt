package br.com.invite.commons

import br.com.invite.validator.TypesafeModel
import br.com.invite.validator.TypesafeValid
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.io.Serializable

@TypesafeValid
class Password : Serializable, TypesafeModel<String> {

    lateinit var password: String private set

    private constructor(password: String) {
        this.password = password
    }

    private constructor()

    @JsonValue
    override fun getDefaultValue(): String = this.password

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Password

        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        return password.hashCode()
    }

    override fun toString(): String = password

    companion object {
        @JsonCreator
        @JvmStatic
        fun of(@JsonProperty("password") password: String)= Password(password)
    }
}