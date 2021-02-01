package org.stapledon

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DockerHelper {

    final static Logger logger = LoggerFactory.getLogger(DockerHelper.getClass().name);

    static boolean startContainer(String container)
    {
        if (isRunning(container))
            return true;

        logger.trace("Starting {}", container)
        Process process = Runtime.getRuntime().exec("docker run " + container);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();
        String line = null;
        while ((line = reader.readLine()) != null)
            logger.trace("{}", line)

        isRunning container;
    }


    static boolean stopContainers(ArrayList<String> containers)
    {
        containers.each {stopContainer(it)}
    }


    static boolean stopContainer(String container)
    {
        if (!isRunning(container))
            return true;

        logger.trace("Stopping {}", container)
        Process process = Runtime.getRuntime().exec("docker stop " + container);
        process.waitFor();

        isRunning container;
    }

    static boolean isRunning(String container) {
        logger.trace("Checking if {} is running", container)
        Process process = Runtime.getRuntime().exec("docker ps --all");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();

        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.contains(container)) {
                logger.trace("{} is running", container)
                return true;
            }
        }

        logger.trace("{} isn't running", container)
        return false;
    }

    static boolean hasSharedNetwork(String networkName) {
        logger.trace("Checking network {} exists", networkName)
        Process process = Runtime.getRuntime().exec("docker network ls");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();

        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.contains(networkName)) {
                logger.trace("Network {} exists", networkName)
                return true;
            }
        }

        logger.trace("{} does't exist", networkName)
        return false;
    }
}
