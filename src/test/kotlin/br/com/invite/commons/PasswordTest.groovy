package br.com.invite.commons

import spock.lang.Specification

import javax.validation.ConstraintViolationException

class PasswordTest extends Specification {

    def "should to instance new Password"() {

        given:
        def password = new Password("password123")

        expect:
        password != null
        password.getPassword() == "password123"
    }

    def "should throw exception when blank password"() {

        when:
        new Password("")

        then:
        def ex = thrown(ConstraintViolationException)
    }

    def "should throw exception when null password"() {

        when:
        new Password(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw an exception when the number of digits is less than six and greater than twenty"() {

        when:
        new Password("12345")

        then:
        def ex = thrown(ConstraintViolationException)
        ex.getMessage() == "password: tamanho deve estar entre 6 e 20"

        when:
        new Password("123456789101112131415")

        then:
        def ex1 = thrown(ConstraintViolationException)
        ex1.getMessage() == "password: tamanho deve estar entre 6 e 20"

    }

}
