package de.diedavids.cuba.dblocalization.web.screens.localization;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import de.diedavids.cuba.dblocalization.config.DbMessagesConfig;
import de.diedavids.cuba.dblocalization.entity.InitialLocalizationImport;
import de.diedavids.cuba.dblocalization.entity.Localization;
import de.diedavids.cuba.dblocalization.service.LocalizationService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Locale;

@UiController("ddcdl_Localization.browse")
@UiDescriptor("localization-browse.xml")
@LookupComponent("localizationsTable")
@LoadDataBeforeShow
public class LocalizationBrowse extends StandardLookup<Localization> {


    @Inject
    protected Messages messages;
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected LocalizationService localizationService;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected GroupTable<Localization> localizationsTable;
    @Inject
    protected CollectionContainer<Localization> localizationsDc;
    @Inject
    protected DataContext dataContext;
    @Inject
    protected DbMessagesConfig dbMessagesConfig;


    @Subscribe("localizationsTable.initialImport")
    protected void onLocalizationsTableInitialImport(Action.ActionPerformedEvent event) {
        screenBuilders.editor(InitialLocalizationImport.class, this)
                .withOpenMode(OpenMode.DIALOG)
                .build()
                .show();
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        localizationsTable.sort("key", Table.SortDirection.ASCENDING);
    }

    @Subscribe(id = "localizationsDc", target = Target.DATA_CONTAINER)
    protected void onLocalizationsDcCollectionChange(CollectionContainer.CollectionChangeEvent<Localization> event) {
        localizationsTable.expandAll();
    }

    @Subscribe("localizationsTable.removeAll")
    protected void onLocalizationsTableRemoveAll(Action.ActionPerformedEvent event) {
        localizationService.removeAll();
        getScreenData().loadAll();
    }

    @Subscribe("localizationsTable.applyChanges")
    protected void onLocalizationsTableApplyChanges(Action.ActionPerformedEvent event) {

        if (dbMessagesConfig.getEnabled()) {
            dataContext.commit();

            clearMessageCache();

            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("changesApplied"))
                    .show();
        }
        else {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withContentMode(ContentMode.HTML)
                    .withCaption(messageBundle.getMessage("dblocalizationNotActive"))
                    .show();
        }
    }

    private void clearMessageCache() {
        messages.clearCache();
        localizationService.clearCache();
    }


}