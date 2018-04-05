package br.com.invite.validator

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator
import java.util.*
import javax.validation.ConstraintViolationException
import javax.validation.MessageInterpolator
import javax.validation.Validation
import javax.validation.Validator

object Validator {
    val validator: Validator by lazy {
        Validation.buildDefaultValidatorFactory().validator
    }

    fun <T> validate(instance: T){
        val validate = validator.validate(instance)

        if (!validate.isEmpty()) {
            throw ConstraintViolationException(validate)
        }
    }
}