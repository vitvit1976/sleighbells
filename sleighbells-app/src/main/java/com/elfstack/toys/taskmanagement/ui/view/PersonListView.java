package com.elfstack.toys.taskmanagement.ui.view;

import com.elfstack.toys.security.dev.SampleUsers;
import com.elfstack.toys.taskmanagement.domain.Person;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.Objects;


@Route("task-pers")
@PageTitle("pers")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task pers")
@PermitAll
public class PersonListView extends Main {

    public PersonListView() {
        Grid<Person> grid = new Grid<>(Person.class, false);
        Editor<Person> editor = grid.getEditor();
        grid.setItems(SampleUsers.ALL_USERS.stream().
                map(x -> new Person(x.getUsername(),
                                            x.getName(),
                        Objects.requireNonNull(x.getAppUser().getEmail()))).toList());
        Grid.Column<Person> firstNameColumn = grid
                .addColumn(Person::getFirstName).setHeader("First name")
                .setWidth("120px").setFlexGrow(0);
        Grid.Column<Person> lastNameColumn = grid.addColumn(Person::getLastName)
                .setHeader("Last name").setWidth("120px").setFlexGrow(0);
        Grid.Column<Person> emailColumn = grid.addColumn(Person::getEmail)
                .setHeader("Email");
        Grid.Column<Person> editColumn = grid.addComponentColumn(person -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                grid.getEditor().editItem(person);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        Binder<Person> binder = new Binder<>(Person.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField firstNameField = new TextField();
        firstNameField.setWidthFull();
        //Label firstNameValidationMessage = new Label("rrrrrrr");
        binder.forField(firstNameField)
                .asRequired("First name must not be empty")
                // .withStatusLabel(firstNameValidationMessage)
                .bind(Person::getFirstName, Person::setFirstName);
        firstNameColumn.setEditorComponent(firstNameField);

        TextField lastNameField = new TextField();

        lastNameField.setWidthFull();
        binder.forField(lastNameField).asRequired("Last name must not be empty")
                //  .withStatusLabel(lastNameValidationMessage)
                .bind(Person::getLastName, Person::setLastName);
        lastNameColumn.setEditorComponent(lastNameField);

        EmailField emailField = new EmailField();
        emailField.setWidthFull();
        // HasText emailValidationMessage = ;
        binder.forField(emailField).asRequired("Email must not be empty")
                .withValidator(
                        new EmailValidator("Enter a valid email address"))
                //  .withStatusLabel(emailValidationMessage)
                .bind(Person::getEmail, Person::setEmail);
        emailColumn.setEditorComponent(emailField);

        Button saveButton = new Button("Save", e -> editor.save());
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);
        add(grid, actions);
    }


    //
}
