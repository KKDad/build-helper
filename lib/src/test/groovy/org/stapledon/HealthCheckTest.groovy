package org.stapledon

import spock.lang.Specification

class HealthCheckTest extends Specification {
    def "IsOk"() {
        setup:

        when:
        def result = HealthCheck.isOk("https://127.0.0.1:5634", "foo")

        then:
        !result
    }
}
