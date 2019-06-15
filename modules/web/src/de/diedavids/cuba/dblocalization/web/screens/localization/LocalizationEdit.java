package de.diedavids.cuba.dblocalization.web.screens.localization;

import com.haulmont.cuba.client.ClientConfig;
import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.data.options.MapOptions;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.dblocalization.entity.Localization;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@UiController("ddcdl_Localization.edit")
@UiDescriptor("localization-edit.xml")
@EditedEntityContainer("localizationDc")
@LoadDataBeforeShow
public class LocalizationEdit extends StandardEditor<Localization> {


    @Inject
    protected LookupField<Locale> localeLookupField;

    @Inject
    protected GlobalConfig globalConfig;


    @Subscribe
    protected void onInit(InitEvent event) {

        Map<String, Locale> availableLocales = globalConfig.getAvailableLocales();

        localeLookupField.setOptionsMap(availableLocales);
    }
}