package gregtech.api.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class GTLog {

    public static Logger conditionalLogger(String name) {
        Logger logger = LogManager.getLogger(name);
        configureLogger(logger, false);
        return logger;
    }

    private static void configureLogger(Logger apiLogger, boolean enabled) {
        org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) apiLogger;
        logger.setAdditive(false);
        LoggerConfig loggerConfig = logger.getContext()
            .getConfiguration()
            .getLoggerConfig(logger.getName());
        loggerConfig.setLevel(enabled ? Level.INFO : Level.OFF);
        logger.getContext()
            .updateLoggers();
    }
}
