package de.diedavids.cuba.dblocalization.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultBoolean;
import com.haulmont.cuba.core.config.defaults.DefaultLong;

@Source(type = SourceType.APP)
public interface DbMessagesConfig extends Config {


    /**
     * @return whether the user session log is enabled
     */
    @Property("dblocalization.enabled")
    @DefaultBoolean(false)
    boolean getEnabled();

    @Property("dblocalization.logWarningDelay")
    @DefaultLong(15 * 60 * 1000L)
    long getLogWarningDelay();
}