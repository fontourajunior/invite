package br.com.invite.commons

import spock.lang.Specification

import javax.validation.ConstraintViolationException

class PhoneTest extends Specification {

    def "should to instance new Phone"() {

        given:
        def phone = new Phone("34999552233")

        expect:
        phone != null
        phone.getPhone() == "34999552233"
    }

    def "should throw exception when blank phone"() {

        when:
        new Phone("")

        then:
        thrown(ConstraintViolationException)
    }

    def "should throw exception when null phone"() {

        when:
        new Phone(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should throw an exception when the number of digits is less than ten and greater than fourteen"() {

        when:
        new Phone("349911558")

        then:
        def ex = thrown(ConstraintViolationException)
        ex.getMessage() == "phone: tamanho deve estar entre 10 e 14"

        when:
        new Phone("123456789101112131415")

        then:
        def ex1 = thrown(ConstraintViolationException)
        ex1.getMessage() == "phone: tamanho deve estar entre 10 e 14"

    }

    def "should throw an exception when the value is other than numbers"() {

        when:
        new Phone("WRONG349999")

        then:
        def ex = thrown(ConstraintViolationException)
        ex.getMessage() == "phone: Informe apenas números"

    }

}
