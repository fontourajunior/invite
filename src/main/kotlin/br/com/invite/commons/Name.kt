package br.com.invite.commons

import br.com.invite.validator.CustomModel
import br.com.invite.validator.CustomValidation
import com.fasterxml.jackson.annotation.JsonValue

@CustomValidation
class Name : CustomModel<String> {

    lateinit var name: String private set

    constructor(name: String) {
        this.name = name
    }

    private constructor()

    @JsonValue
    override fun getDefaultValue(): String = this.name



    override fun toString(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Name

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}