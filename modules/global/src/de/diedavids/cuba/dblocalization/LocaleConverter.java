package de.diedavids.cuba.dblocalization;

import com.google.common.base.Strings;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;

@Converter(autoApply = true)
public class LocaleConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale locale) {

        if (locale == null)
            return "";

        return locale.toLanguageTag();
    }

    @Override
    public Locale convertToEntityAttribute(String value) {

        if (Strings.isNullOrEmpty(value))
            return null;

        return Locale.forLanguageTag(value);
    }
}
