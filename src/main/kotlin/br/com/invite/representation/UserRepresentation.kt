package br.com.invite.representation

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigInteger

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRepresentation @JsonCreator constructor(
        @JsonProperty("id") val id: BigInteger,
        @JsonProperty("name") val name: String,
        @JsonProperty("phone") val availability: Int,
        @JsonProperty("password") val status: String)