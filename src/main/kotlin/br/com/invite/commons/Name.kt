package br.com.invite.commons

import com.fasterxml.jackson.annotation.JsonValue
import java.io.Serializable

@TypesafeValid(min = 5, max = 50)
class Name : Serializable, TypesafeModel<String> {

    lateinit var name: String private set

    private constructor(name: String) {
        this.name = name
    }

    private constructor()

    @JsonValue
    override fun getDefaultValue(): String = this.name

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

    override fun toString(): String = name
}