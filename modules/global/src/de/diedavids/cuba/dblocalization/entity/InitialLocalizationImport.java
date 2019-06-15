package de.diedavids.cuba.dblocalization.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.FileDescriptor;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@MetaClass(name = "ddcdl_InitialLocalizationImport")
public class InitialLocalizationImport extends BaseUuidEntity {
    private static final long serialVersionUID = 9049202370890089851L;

    @NotNull
    @MetaProperty(mandatory = true)
    protected FileDescriptor messagesFile;

    @NotNull
    @MetaProperty(datatype = "Locale", mandatory = true)
    protected Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public FileDescriptor getMessagesFile() {
        return messagesFile;
    }

    public void setMessagesFile(FileDescriptor messagesFile) {
        this.messagesFile = messagesFile;
    }
}