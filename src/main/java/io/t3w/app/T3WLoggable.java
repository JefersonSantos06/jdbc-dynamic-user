package io.t3w.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.function.Supplier;


public interface T3WLoggable {

    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    default void log(final Level level, final Supplier<String> supplier) {
        log(level, supplier, null);
    }

    default void log(final Level level, final Supplier<String> supplier, final Throwable throwable) {
        if (level != null && supplier != null) {
            final var logger = getLogger();
            if (logger.isEnabledForLevel(level))
                logger.atLevel(level).setCause(throwable).log(supplier);
        }
    }
}