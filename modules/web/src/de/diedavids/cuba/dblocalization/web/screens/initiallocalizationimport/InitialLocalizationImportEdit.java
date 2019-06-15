package de.diedavids.cuba.dblocalization.web.screens.initiallocalizationimport;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ContentMode;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.dblocalization.entity.InitialLocalizationImport;
import de.diedavids.cuba.dblocalization.service.LocalizationService;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@UiController("ddcdl_InitialLocalizationImport.edit")
@UiDescriptor("initial-localization-import-edit.xml")
@EditedEntityContainer("initialLocalizationImportDc")
@LoadDataBeforeShow
public class InitialLocalizationImportEdit extends StandardEditor<InitialLocalizationImport> {
    @Inject
    protected Logger log;

    @Inject
    protected LookupField<Locale> localeLookupField;

    @Inject
    protected GlobalConfig globalConfig;
    @Inject
    protected LocalizationService localizationService;
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected Dialogs dialogs;

    @Subscribe
    protected void onInit(InitEvent event) {
        Map<String, Locale> availableLocales = globalConfig.getAvailableLocales();
        localeLookupField.setOptionsMap(availableLocales);
    }

    @Subscribe("helpBtn")
    protected void onHelpBtnClick(Button.ClickEvent event) {

        Dialogs.MessageDialogBuilder messageDialog = dialogs.createMessageDialog()
                .withMessage(messageBundle.getMessage("explanation"))
                .withCaption(messageBundle.getMessage("initialImportHelpTitle"))
                .withWidth("500px")
                .withContentMode(ContentMode.HTML);
        messageDialog.show();

    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPostCommit(DataContext.PostCommitEvent event) {


        localizationService.initialImport(getEditedEntity().getMessagesFile(), getEditedEntity().getLocale());

        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(messageBundle.getMessage("initialImportSuccessful"))
                .show();


    }
    
    
}