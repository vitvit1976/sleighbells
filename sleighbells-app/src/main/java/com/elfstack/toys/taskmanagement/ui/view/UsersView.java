package com.elfstack.toys.taskmanagement.ui.view;

import com.elfstack.toys.taskmanagement.domain.TYPE_FIELDS;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.AbstractNumberField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.provider.HasListDataView;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.Arrays;

@Route("task-user")
@PageTitle("user")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task user")
@PermitAll
public class UsersView extends Main {

    public UsersView() {
        for (TYPE_FIELDS t : TYPE_FIELDS.values()) {
            AbstractField field = t.getField();
            if (field instanceof MultiSelect<?,?>) continue;
            if (field instanceof HasListDataView<?, ?>) {
                HasListDataView view = (HasListDataView) field;
                view.setItems(Arrays.stream(TYPE_FIELDS.values()).map(Enum::name).toList());
            }
            if (field instanceof MultiSelect<?,?>) {
                MultiSelect view = (MultiSelect) field;
                view.select(Arrays.stream(TYPE_FIELDS.values()).filter(x->x.getField() instanceof MultiSelect<?,?>));
            }

            if (field instanceof AbstractNumberField<?, ?>) {
                ((AbstractNumberField) field).setStepButtonsVisible(true);
            }
            try {
                field.setValue(t.name());
            } catch (Exception e) {

            }

            FormLayout formLayout = new FormLayout();
            formLayout.addFormItem(field, t.name());
            add(formLayout);
        }
        /*
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setValue("Ex@mplePassw0rd");
        add(passwordField);

        PasswordField field = new PasswordField();
        field.setLabel("Label");
        field.setHelperText("Helper text");
        field.setPlaceholder("Placeholder");
        field.setTooltipText("Tooltip text");
        field.setClearButtonVisible(true);
        field.setPrefixComponent(VaadinIcon.LOCK.create());
        add(passwordField, field);

         */
    }
}
