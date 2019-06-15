package de.diedavids.cuba.dblocalization.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import de.diedavids.cuba.dblocalization.entity.Localization;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service(LocalizationService.NAME)
public class LocalizationServiceBean implements LocalizationService {

    private static final Logger log = LoggerFactory.getLogger(LocalizationServiceBean.class);

    @Inject
    protected Messages messages;
    @Inject
    protected DataManager dataManager;
    @Inject
    protected FileLoader fileLoader;
    @Inject
    protected Persistence persistence;


    @Override
    public void clearCache() {
        messages.clearCache();
    }

    @Override
    public void initialImport(FileDescriptor fileDescriptor, Locale locale) {


        CommitContext commitContext = new CommitContext();

        try {
            InputStream stream = fileLoader.openStream(fileDescriptor);

            try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                Properties properties = new Properties();
                properties.load(reader);

                Enumeration<?> propertyNames = properties.propertyNames();


                while (propertyNames.hasMoreElements()) {
                    String key = (String) propertyNames.nextElement();

                    commitContext.addInstanceToCommit(
                            createLocalization(
                                    locale,
                                    key,
                                    properties.getProperty(key)
                            )
                    );
                }


                dataManager.commit(commitContext);

            } finally {
                IOUtils.closeQuietly(stream);
            }
        } catch (FileStorageException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        persistence.runInTransaction(em -> {
            em.setSoftDeletion(false);
            em.createQuery("DELETE from ddcdl_Localization e").executeUpdate();
        });
    }

    private Localization createLocalization(Locale locale, String key, String value) {
        Localization localization = dataManager.create(Localization.class);
        localization.setLocale(locale);
        localization.setKey(key);
        localization.setValue(value);

        if (key.contains("/")) {
            localization.setKeyGroup(createKeyGroupSlashSubstring(key));
        } else if (key.contains(".")) {
            localization.setKeyGroup(createKeyGroupDotSubstring(key));
        } else {
            localization.setKeyGroup(key);
        }
        return localization;
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