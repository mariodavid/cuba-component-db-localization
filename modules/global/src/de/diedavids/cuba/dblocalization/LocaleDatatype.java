package de.diedavids.cuba.dblocalization;

import com.google.common.base.Strings;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class LocaleDatatype implements Datatype<Locale> {

    @Override
    public Class getJavaClass() {
        return Locale.class;
    }

    @Nonnull
    @Override
    public String format(@Nullable Object value) {

        if (value == null) {
            return "";
        }

        Locale locale = (Locale) value;

        Configuration globalConfig = AppBeans.get(Configuration.class);

        GlobalConfig config = globalConfig.getConfig(GlobalConfig.class);

        Map<String, Locale> availableLocales = config.getAvailableLocales();

        Optional<String> first = availableLocales.keySet().stream()
                .filter(s -> availableLocales.get(s).equals(locale))
                .findFirst();


        if (first.isPresent()) {
            return first.get();
        } else {
            return ((Locale) value).toLanguageTag();
        }
    }

    @Nonnull
    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public Locale parse(@Nullable String value) throws ParseException {

        if (Strings.isNullOrEmpty(value))
            return null;

        return Locale.forLanguageTag(value);
    }

    @Nullable
    @Override
    public Locale parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }

}
