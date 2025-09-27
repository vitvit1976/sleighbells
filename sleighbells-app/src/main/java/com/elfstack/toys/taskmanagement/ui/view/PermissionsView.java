package com.elfstack.toys.taskmanagement.ui.view;

import com.elfstack.toys.taskmanagement.domain.Country;
import com.elfstack.toys.taskmanagement.service.DataService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("task-perm")
@PageTitle("perm")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task perm")
@PermitAll
public class PermissionsView extends Main {
    public void showDialog() {
        Dialog dialog = new Dialog("New employee", createDialogLayout());
        Button saveButton = createSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        dialog.open();
    }

    public PermissionsView() {
        ComboBox<Country> comboBox = new ComboBox<>("Country");
        comboBox.setItems(DataService.getCountries());
        comboBox.setItemLabelGenerator(Country::getName);

        add(comboBox);

        Button showChooseCB = new Button("choose", e-> Notification.show(comboBox.getValue().toString()));
        Button button = new Button("Show dialog", e -> showDialog());
        button.getStyle().  
                set("position", "fixed").
                set("top", "0").
                set("right", "0").
                set("bottom", "0").
                set("left", "0").
                set("display", "flex").
                set("align-items", "center").
                set("justify-content", "center");
        add(button, showChooseCB);

    }

    private static VerticalLayout createDialogLayout() {
        TextField firstNameField = new TextField("First name");
        TextField lastNameField = new TextField("Last name");

        VerticalLayout dialogLayout = new VerticalLayout(firstNameField,
                lastNameField);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private static Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Add", e -> dialog.close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

}
