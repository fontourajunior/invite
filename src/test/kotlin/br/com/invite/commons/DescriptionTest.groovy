package br.com.invite.commons

import spock.lang.Specification

import javax.validation.ConstraintViolationException

class DescriptionTest extends Specification {

    def "should to instance new description"() {

        given:
        def description = new Description("Homer Simpson")

        expect:
        description != null
        description.getDescription() == "Homer Simpson"
    }

    def "should throw exception when blank description"() {

        when:
        new Description("")

        then:
        def ex = thrown(ConstraintViolationException)
        ex.getMessage() == "description: Não pode estar em branco"
    }

    def "should throw exception when null description"() {

        when:
        new Description(null)

        then:
        thrown(IllegalArgumentException)
    }

}
