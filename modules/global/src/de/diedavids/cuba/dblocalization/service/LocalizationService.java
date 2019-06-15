package de.diedavids.cuba.dblocalization.service;

import com.haulmont.cuba.core.entity.FileDescriptor;

import java.util.Locale;

public interface LocalizationService {
    String NAME = "ddcdl_LocalizationService";

    void clearCache();

    void initialImport(FileDescriptor fileDescriptor, Locale locale);

    void removeAll();
}