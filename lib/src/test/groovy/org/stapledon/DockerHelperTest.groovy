package org.stapledon

import spock.lang.Specification

class DockerHelperTest extends Specification {
    def "startContainer can start a container"() {
        when:
        def result = DockerHelper.startContainer("busybox")

        then:
        result
        DockerHelper.isRunning("busybox")

        cleanup:
        DockerHelper.stopContainer("busybox")
    }


    def "StopContainers can stop multiple containers, whether they exist or not"() {
        when:
        def result = DockerHelper.stopContainers(["concerto-server", "concerto-worker", "redis"])

        then:
        result
    }

    def "StopContainer stops a running container and returns true"() {
        setup:
        DockerHelper.startContainer("busybox --name busybox")

        when:
        def result = DockerHelper.stopContainer("busybox")

        then:
        result
    }

    def "IsRunning returns true container is running"() {
        setup:
        DockerHelper.startContainer("busybox")

        when:
        def result = DockerHelper.isRunning("busybox")

        then:
        result

        cleanup:
        DockerHelper.stopContainer("busybox")
    }

    def "IsRunning returns false container isn't running"() {
        setup:

        when:
        def result = DockerHelper.isRunning("idontexist")

        then:
        !result
    }


    def "hasSharedNetwork returns true when it can find the network"() {
        when:
        def result = DockerHelper.hasSharedNetwork("concertoShared")

        then:
        result
    }

    def "hasSharedNetwork returns false when it can't find the network"() {
        when:
        def result = DockerHelper.hasSharedNetwork("idontexist")

        then:
        !result
    }
}
