package br.com.invite.validator

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator
import java.util.*
import javax.validation.ConstraintViolationException
import javax.validation.MessageInterpolator
import javax.validation.Validation
import javax.validation.Validator

object ValidationUtil {
    private val validator: Validator

    private val messageInterpolator: MessageInterpolator

    init {
        messageInterpolator = LocaleAwareMessageInterpolator()

        validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(messageInterpolator)
                .buildValidatorFactory()
                .validator
    }

    fun <T> validate(value: T) {
        val validate = validator.validate(value)

        if (!validate.isEmpty()) {
            throw ConstraintViolationException(validate)
        }
    }

}


class LocaleAwareMessageInterpolator : ResourceBundleMessageInterpolator() {

    private var defaultLocale = Locale.getDefault()

    fun setDefaultLocale(defaultLocale: Locale) {
        this.defaultLocale = defaultLocale
    }

    override fun interpolate(messageTemplate: String,
                             context: MessageInterpolator.Context): String {
        return interpolate(messageTemplate, context, defaultLocale)
    }

    override fun interpolate(messageTemplate: String,
                             context: MessageInterpolator.Context, locale: Locale): String {
        return super.interpolate(messageTemplate, context, locale)
    }
}