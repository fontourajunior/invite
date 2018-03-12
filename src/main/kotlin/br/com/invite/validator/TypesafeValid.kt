package br.com.invite.validator


import java.lang.annotation.Documented
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.TYPE
import kotlin.properties.Delegates
import kotlin.reflect.KClass

interface TypesafeModel<T> {
    abstract fun getDefaultValue(): T
}

@Target(TYPE, ANNOTATION_CLASS, CLASS)
@Retention(RUNTIME)
@Constraint(validatedBy = [TypesafeValidator::class])
@Documented
annotation class TypesafeValid(val min: Int = 0,
                               val max: Int = Int.MAX_VALUE,
                               val message: String = "",
                               val groups: Array<KClass<*>> = arrayOf(),
                               val payload: Array<KClass<out Payload>> = arrayOf())



class TypesafeValidator : ConstraintValidator<TypesafeValid, TypesafeModel<*>> {

    private var min: Int by Delegates.notNull<Int>()
    private var max: Int by Delegates.notNull<Int>()
    private lateinit var message: String

    private val MIN_SIZE = 0
    private val MAX_SIZE = Int.MAX_VALUE


    override fun initialize(annotation: TypesafeValid) {
        min = annotation.min
        max = annotation.max
        message = annotation.message
    }

    override fun isValid(typesafe: TypesafeModel<*>, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()

        val value = typesafe.getDefaultValue()

        if (!validateNotNull(value, context)) {
            return false
        }

        if (!validateNotEmpty(value, context)) {
            return false
        }

        if (!validateSize(value, context)) {
            return false
        }

        return true
    }

    private fun validateNotNull(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            addConstraintViolation(context, "Is not Null")
            return false
        }
        return true
    }



    private fun validateNotEmpty(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value is String && value.isEmpty()) {
            addConstraintViolation(context, "Is not empty")
            return false
        }
        return true
    }

    private fun validateSize(value: Any?, context: ConstraintValidatorContext): Boolean {

        var notValid = false

        if (value is String) {
            if (min < MIN_SIZE || max > MAX_SIZE) {
                throw IllegalArgumentException("invalid min|max size. min >= 0 && max < $MAX_SIZE")
            }
            notValid = value.length < min || value.length > max
        }

        if (notValid) {
            addConstraintViolation(context, "size must be between $min and $max")
            return false
        }
        return true
    }

    private fun addConstraintViolation(context: ConstraintValidatorContext, messageError: String) {
        context.buildConstraintViolationWithTemplate(messageError)
                .addConstraintViolation()
    }
}