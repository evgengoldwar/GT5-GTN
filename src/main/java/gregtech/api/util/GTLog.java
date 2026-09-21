package gregtech.api.util;

import static gregtech.GTLoggers.GT_RECIPE_REMOVAL_LOGGER;
import static gregtech.GTLoggers.GT_RECIPE_REMOVAL_LOGGER_ENABLED;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.RollingRandomAccessFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.OnStartupTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

/** GregTech's dedicated logger configuration and explosion logging helpers. */
public class GTLog {

    public static Logger conditionalLogger(String name) {
        Logger logger = LogManager.getLogger(name);
        configureLogger(logger, false);
        return logger;
    }

    public static void configureRecipeRemovalLogger(File parentFile) {
        configureRollingLogger(
            GT_RECIPE_REMOVAL_LOGGER,
            GT_RECIPE_REMOVAL_LOGGER_ENABLED,
            new File(parentFile, "logs/UselessRecipeRemovals.log"),
            new File(parentFile, "logs/UselessRecipeRemovals-%i.log"),
            "GregTechUselessRecipeRemovalsFile");
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

    public static boolean configureRollingLogger(Logger apiLogger, boolean enabled, File file, File filePattern,
        String appenderName) {
        configureLogger(apiLogger, false);
        if (!enabled) return false;

        org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) apiLogger;

        Configuration configuration = logger.getContext()
            .getConfiguration();
        PatternLayout layout = PatternLayout
            .createLayout("[%d{yyyy-MM-dd HH:mm:ss}] %msg%n", configuration, null, null, null);
        CompositeTriggeringPolicy policy = CompositeTriggeringPolicy
            .createPolicy(OnStartupTriggeringPolicy.createPolicy());
        DefaultRolloverStrategy strategy = DefaultRolloverStrategy
            .createStrategy("3", null, "max", null, configuration);
        RollingRandomAccessFileAppender appender = RollingRandomAccessFileAppender.createAppender(
            file.getPath(),
            filePattern.getPath(),
            "true",
            appenderName,
            "true",
            policy,
            strategy,
            layout,
            null,
            "true",
            "false",
            null,
            configuration);
        if (appender == null) throw new IllegalStateException("Failed to create " + appenderName);

        try {
            appender.start();
            logger.addAppender(appender);
        } catch (RuntimeException e) {
            appender.stop();
            throw e;
        }
        configureLogger(apiLogger, true);
        return true;
    }
}
