package org.stapledon

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HealthCheck {

    final static Logger logger = LoggerFactory.getLogger(HealthCheck.getClass().name);


    /**@
     * Make a connection to a URL and look for specific text
     * @param url - URL to connect to
     * @param validationText - Text tro look for
     * @return true if text was return by the URL
     */
    static boolean isOk(String url, String validationText) {
        def ready = false
        try {
            def get = new URL(url).openConnection();
            def body = get.getContent().getText().toString()
            ready = body.contains(validationText)
            if (!ready)
                logger.info "${body}"
        } catch (Exception e1) {
            logger.info "${url} not ready: ${e1}"
        }
        ready
    }

}
