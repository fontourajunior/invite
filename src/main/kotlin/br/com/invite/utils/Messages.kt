package br.com.invite.utils

import java.util.*

class Messages {

    companion object {
        val DUPLICATED_NAME : String = getMessage("duplicated.name")


        private fun getMessage(message: String): String {
            val bundle = ResourceBundle.getBundle("locale/messages")
            return bundle.getString(message)
        }
    }
}