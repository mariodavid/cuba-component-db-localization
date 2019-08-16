package de.diedavids.cuba.dblocalization.web;

import com.haulmont.cuba.client.sys.MessagesClientImpl;
import de.diedavids.cuba.dblocalization.config.DbMessagesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Set;

public class DbMessagesClientImpl extends MessagesClientImpl {


    private static final Logger log = LoggerFactory.getLogger(DbMessagesClientImpl.class);
    @Inject
    protected DbMessagesConfig dbMessagesConfig;
    private long nextNotification = 0;

    @Override
    protected String searchOnePack(String pack, String key, Locale locale, Locale truncatedLocale, Set<String> passedPacks) {
        String cacheKey = makeCacheKey(pack, key, locale, truncatedLocale);

        String msg = strCache.get(cacheKey);
        if (msg != null)
            return msg;


        // if db-based message lookup is enabled, lookup in the DB first
        if (dbMessagesConfig.getEnabled()) {
            log.debug("DB based message lookup active. Remote Lookup will be executed");
            // switch to search remotely first so that DB based entries are taken first
            if (locale.equals(truncatedLocale)) {
                msg = searchRemotely(pack, key, locale);
                if (msg != null) {
                    cache(cacheKey, msg);
                }
            }

            msg = searchFiles(pack, key, locale, truncatedLocale, passedPacks);
            if (msg == null) {
                msg = searchClasspath(pack, key, locale, truncatedLocale, passedPacks);
            }
        }

        // else default behavior
        else {
            if (nextNotification < System.currentTimeMillis()) {
                log.info("DB based message lookup inactive. Fallback to default behavior.");
                if (dbMessagesConfig.getLogWarningDelay() < 1000) {
                    nextNotification = Long.MAX_VALUE;
                } else {
                    nextNotification = System.currentTimeMillis() + dbMessagesConfig.getLogWarningDelay();
                }
            }

            msg = searchFiles(pack, key, locale, truncatedLocale, passedPacks);
            if (msg == null) {
                msg = searchClasspath(pack, key, locale, truncatedLocale, passedPacks);
            }
            if (msg == null && locale.equals(truncatedLocale)) {
                msg = searchRemotely(pack, key, locale);
                if (msg != null) {
                    cache(cacheKey, msg);
                }
            }
        }

        return msg;
    }
}
