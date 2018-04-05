package br.com.invite.resource.representation

import br.com.invite.commons.Name
import br.com.invite.commons.Phone

data class UserRepresentation(val id: Long, val name: String, val phone: String)