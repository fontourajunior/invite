package br.com.invite.commons

import spock.lang.Specification

class NameTest extends Specification {

    def "should to save a new user"() {

        expect:
        new Name("") != null

    }

}
