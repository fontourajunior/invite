package br.com.invite.commons

import spock.lang.Specification

import javax.validation.ConstraintViolationException

class NameTest extends Specification {

    def "should to instance new name"() {

        given:
        def name = new Name("Homer Simpson")

        expect:
        name != null
        name.getName() == "Homer Simpson"
    }

    def "should throw exception when blank name"() {

        when:
        new Name("")

        then:
        def ex = thrown(ConstraintViolationException)
        ex.getMessage() == "name: Não pode estar em branco"
    }

    def "should throw exception when null name"() {

        when:
        new Name(null)

        then:
        thrown(IllegalArgumentException)
    }

}
