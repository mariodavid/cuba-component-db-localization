package de.diedavids.cuba.dblocalization.core;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.MessagesImpl;
import com.haulmont.cuba.security.app.Authenticated;
import com.haulmont.cuba.security.app.Authentication;
import de.diedavids.cuba.dblocalization.entity.Localization;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class DbMessagesImpl extends MessagesImpl {


    @Inject
    private DataManager dataManager;


    @Inject
    Authentication authentication;


    @Override
    protected String internalGetMessage(String packs, String key, Locale locale, String defaultValue, boolean searchMainIfNotFound) {

        locale = messageTools.trimLocale(locale);

        String cacheKey = makeCacheKey(packs, key, locale, locale);

        String msg = strCache.get(cacheKey);
        if (msg != null)
            return msg;

        String notFound = notFoundCache.get(cacheKey);
        if (notFound != null)
            return defaultValue;

        msg = searchMessage(packs, key, locale, locale, new HashSet<>());
        if (msg != null) {
            cache(cacheKey, msg);
            return msg;
        }

        if (searchMainIfNotFound) {
            String tmpCacheKey = makeCacheKey(mainMessagePack, key, locale, locale);
            msg = searchMessage(tmpCacheKey, key, locale, locale, new HashSet<>());
            if (msg != null) {
                cache(cacheKey, msg);
                return msg;
            }
        }

        notFoundCache.put(cacheKey, key);
        return defaultValue;
    }

    @Override
    protected String searchOnePack(String pack, String key, Locale locale, Locale truncatedLocale, Set<String> passedPacks) {

        if (AppContext.isReady()) {


            authentication.begin();

            Optional<Localization> translatedMessage = dataManager.load(Localization.class)
                    .query("select e from ddcdl_Localization e where e.key = :key and e.locale = :locale")
                    .parameter("key", key)
                    .parameter("locale", locale)
                    .optional();

            authentication.end();
            if (translatedMessage.isPresent()) {
                return translatedMessage.get().getValue();
            } else {
                return super.searchOnePack(pack, key, locale, truncatedLocale, passedPacks);
            }

        } else {
            return super.searchOnePack(pack, key, locale, truncatedLocale, passedPacks);
        }

    }
}
