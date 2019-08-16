package de.diedavids.cuba.dblocalization.core;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.MessagesImpl;
import com.haulmont.cuba.security.app.Authentication;
import de.diedavids.cuba.dblocalization.config.DbMessagesConfig;
import de.diedavids.cuba.dblocalization.entity.Localization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class DbMessagesImpl extends MessagesImpl {

    private static final Logger log = LoggerFactory.getLogger(DbMessagesImpl.class);

    @Inject
    private DataManager dataManager;

    @Inject
    Authentication authentication;

    @Inject
    DbMessagesConfig dbMessagesConfig;

    private long nextNotification = 0;

    @Override
    protected String searchOnePack(String pack, String key, Locale locale, Locale truncatedLocale, Set<String> passedPacks) {

        if (AppContext.isReady()) {

            Optional<Localization> translatedMessage = searchKeyInDatabase(key, locale);
            if (translatedMessage.isPresent()) {
                Localization localization = translatedMessage.get();
                String value = localization.getValue();
                if (log.isDebugEnabled()) {
                    log.debug(String.format("DB based message found for key '%s': '%s' (%s)", key, value, localization.getId()));
                }
                return value;
            } else {
                return super.searchOnePack(pack, key, locale, truncatedLocale, passedPacks);
            }
        } else {
            return super.searchOnePack(pack, key, locale, truncatedLocale, passedPacks);
        }
    }

    private Optional<Localization> searchKeyInDatabase(String key, Locale locale) {
        if (dbMessagesConfig.getEnabled()) {
            log.debug("DB based message lookup active. DB Lookup will be executed");
            authentication.begin();
            Optional<Localization> translatedMessage = dataManager.load(Localization.class)
                    .query("select e from ddcdl_Localization e where e.key = :key and e.locale = :locale")
                    .parameter("key", key)
                    .parameter("locale", locale)
                    .optional();
            authentication.end();
            return translatedMessage;
        } else {
            if (nextNotification < System.currentTimeMillis()) {
                log.info("DB based message lookup inactive. Fallback to default behavior.");
                if (dbMessagesConfig.getLogWarningDelay() < 1000) {
                    nextNotification = Long.MAX_VALUE;
                } else {
                    nextNotification = System.currentTimeMillis() + dbMessagesConfig.getLogWarningDelay();
                }
            }
            return Optional.empty();
        }
    }
}
