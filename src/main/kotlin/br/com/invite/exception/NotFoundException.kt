package br.com.invite.exception

import javassist.NotFoundException

class NotFoundException(message: String?) : NotFoundException(message) {

    constructor() : this(null)

}