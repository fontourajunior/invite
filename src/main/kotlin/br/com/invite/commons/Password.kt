package br.com.invite.commons

import br.com.invite.validator.CustomModel
import br.com.invite.validator.CustomValidation
import com.fasterxml.jackson.annotation.JsonValue

@CustomValidation
class Password : CustomModel<String> {

    lateinit var password: String private set

    constructor(password: String) {
        this.password = password
    }

    private constructor()

    @JsonValue
    override fun getDefaultValue(): String = this.password

    override fun toString(): String = password

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


}