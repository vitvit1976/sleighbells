package com.elfstack.toys.taskmanagement.ui.view;

import com.elfstack.toys.base.ui.component.ViewToolbar;
import com.elfstack.toys.taskmanagement.domain.TYPE_ANY_TASK;
import com.elfstack.toys.taskmanagement.domain.Task;
import com.elfstack.toys.taskmanagement.service.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route("task-list")
@PageTitle("Task List")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task List")
@PermitAll // When security is enabled, allow all authenticated users
public class TaskListView extends Main {

    private final TaskService taskService;

    final TextField description;
    final DatePicker dueDate;
    final Button createBtn;
    final Button updateBtn;
    final Button delBtn;
    final Grid<Task> taskGrid;
    final Div anyDiv = customDiv();

    public TaskListView(TaskService taskService, Clock clock) {
        this.taskService = taskService;

        description = new TextField();
        description.setPlaceholder("What do you want to do?");
        description.setAriaLabel("Task description");
        description.setMaxLength(Task.DESCRIPTION_MAX_LENGTH);
        description.setMinWidth("20em");

        dueDate = new DatePicker();
        dueDate.setPlaceholder("Due date");
        dueDate.setAriaLabel("Due date");

        createBtn = new Button("Create", event -> createTask());
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(clock.getZone())
                .withLocale(getLocale());
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

        taskGrid = new Grid<>();
        taskGrid.setItems(query -> taskService.list(toSpringPageRequest(query)).stream());
        taskGrid.addColumn(Task::getDescription).setHeader("Description");
        taskGrid.addColumn(task -> Optional.ofNullable(task.getDueDate()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Due Date");
        taskGrid.addColumn(task -> dateTimeFormatter.format(task.getCreationDate())).setHeader("Creation Date");
        taskGrid.setSizeFull();

        updateBtn = new Button("Update", event -> updTask(taskGrid.getSelectedItems().stream().findFirst().get()));
        updateBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        delBtn = new Button("Delete", event-> delTask(taskGrid.getSelectedItems().stream().findFirst().get()));
        delBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Task List", ViewToolbar.group(description, dueDate, createBtn, updateBtn)));
        add(customDiv());
        add(taskGrid);
    }

    public Div customDiv(){
        Card imageCard = new Card();
        imageCard.addThemeVariants(CardVariant.LUMO_STRETCH_MEDIA);

        DownloadHandler imageHandler = DownloadHandler.forClassResource(
                getClass(), "/images/lapland.avif", "lapland.avif");
        Image image = new Image(imageHandler, "");
        imageCard.setMedia(image);

        imageCard.setTitle(new Div("Lapland"));
        imageCard.setSubtitle(new Div("The Exotic North"));
        imageCard.add("Lapland is the northern-most region of Finland and an active outdoor destination.");

// Card with stretched icon
        Card iconCard = new Card();
        iconCard.addThemeVariants(CardVariant.LUMO_STRETCH_MEDIA);

        Icon icon = LumoIcon.PHOTO.create();
        icon.getStyle()
                .setColor("var(--lumo-primary-color)")
                .setBackgroundColor("var(--lumo-primary-color-10pct)");
        iconCard.setMedia(icon);

        iconCard.setTitle(new Div("Lapland"));
        iconCard.setSubtitle(new Div("The Exotic North"));
        iconCard.add("Lapland is the northern-most region of Finland and an active outdoor destination.");
        return new Div(imageCard, iconCard);
    }


    private void delTask(Task task) {
        taskService.delTask(task);
        taskGrid.getDataProvider().refreshAll();
        Notification.show("Task delete", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void createTask() {
        taskService.createTask(description.getValue(), dueDate.getValue(), TYPE_ANY_TASK.SENT);
        taskGrid.getDataProvider().refreshAll();
        description.clear();
        dueDate.clear();
        Notification.show("Task added", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void updTask(Task task) {
        taskService.updateTask(task, description.getValue(), dueDate.getValue(), TYPE_ANY_TASK.SENT);
        taskGrid.getDataProvider().refreshAll();
        description.clear();
        dueDate.clear();
        Notification.show("Task updated", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

}
