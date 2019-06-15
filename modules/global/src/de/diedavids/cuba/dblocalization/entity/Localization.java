package de.diedavids.cuba.dblocalization.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import de.diedavids.cuba.dblocalization.LocaleConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@NamePattern(".%s (%s): %s|key,locale,value")
@Table(name = "DDCDL_LOCALIZATION", indexes = {
        @Index(name = "IDX_DDCDL_LOCALIZATION", columnList = "PACK, KEY_, LOCALE")
})
@Entity(name = "ddcdl_Localization")
public class Localization extends StandardEntity {
    private static final long serialVersionUID = -1370583057928109205L;

    @Column(name = "KEY_GROUP")
    protected String keyGroup;

    @NotNull
    @Column(name = "KEY_", nullable = false, length = 4000)
    protected String key;

    @NotNull
    @MetaProperty(datatype = "Locale", mandatory = true)
    @Column(name = "LOCALE", nullable = false)
    @Convert(converter = LocaleConverter.class)
    protected Locale locale;

    @Lob
    @Column(name = "VALUE_")
    protected String value;

    public String getKeyGroup() {
        return keyGroup;
    }

    public void setKeyGroup(String keyGroup) {
        this.keyGroup = keyGroup;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @PrePersist
    public void prePersist() {
        generateKeyGroup();
    }

    @PreUpdate
    public void preUpdate() {
        generateKeyGroup();
    }

    private void generateKeyGroup() {
        if (key.contains("/")) {
            setKeyGroup(createKeyGroupSlashSubstring(key));
        } else if (key.contains(".")) {
            setKeyGroup(createKeyGroupDotSubstring(key));
        } else {
            setKeyGroup(key);
        }
    }

    private String createKeyGroupSlashSubstring(String key) {
        String[] splashSplit = key.split("/");
        return splashSplit[0];
    }

    private String createKeyGroupDotSubstring(String key) {
        String[] dotSplit = key.split("\\.");
        List<String> strings = Arrays.asList(Arrays.copyOf(dotSplit, dotSplit.length - 1));

        return strings.stream()
                .collect(Collectors.joining("."));
    }

}