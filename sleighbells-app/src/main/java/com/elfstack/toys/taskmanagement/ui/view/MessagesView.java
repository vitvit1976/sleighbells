package com.elfstack.toys.taskmanagement.ui.view;

import com.elfstack.toys.security.AppRoles;
import com.elfstack.toys.taskmanagement.Utils;
import com.elfstack.toys.taskmanagement.domain.SimpleEntity;
import com.elfstack.toys.taskmanagement.domain.SimpleField;
import com.elfstack.toys.taskmanagement.domain.TYPE_ANY_TASK;
import com.elfstack.toys.taskmanagement.domain.Task;
import com.elfstack.toys.taskmanagement.service.DataService;
import com.elfstack.toys.taskmanagement.service.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.vaadin.klaudeta.PaginatedGrid;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.*;

@Route("any-view")
@PageTitle("Any view")
@Menu(order = 1, icon = "vaadin:star", title = "View ANY")

//@RolesAllowed(AppRoles.ADMIN)
@PermitAll // When security is enabled, allow all authenticated users
public class MessagesView extends Main implements RouterLayout {
    private List<SimpleEntity> simpleEntities;
    private PaginatedGrid<SimpleEntity, ?> grid = new PaginatedGrid<>();

    public List<String> getNmFields() {
        return Arrays.asList("AAA", "BField", "NextField");
    }

    private void initList(TaskService taskService) {
        if (!taskService.list(PageRequest.ofSize(1)).isEmpty()) return;
        for (int i = 0; i < 15; i++)
            taskService.createTask("task" + i * i, LocalDate.now().minusDays(i * i * i), TYPE_ANY_TASK.values()[(int) Math.floorMod(Math.round(Math.random() * 100), 3)]);
    }

    public MessagesView(TaskService taskService, Clock clock) {
        //   initList(taskService);
/*
        var dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(clock.getZone())
                .withLocale(getLocale());
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());
        Grid<Task> taskGrid = new Grid<>();
        taskGrid.setItems(query -> taskService.list(toSpringPageRequest(query)).stream().filter(x->x.getTypeAnyTask().equals(getType())));
        taskGrid.addColumn(Task::getDescription).setHeader("Описание");
        taskGrid.addColumn(task -> Optional.ofNullable(task.getDueDate()).map(dateFormatter::format).orElse("Never"))
                .setHeader("День");
        taskGrid.addColumn(task -> dateTimeFormatter.format(task.getCreationDate())).setHeader("Создан");
        taskGrid.addColumn(x->x.getTypeAnyTask().name()).setHeader("Тип");
        taskGrid.setSizeFull();
        setSizeFull();
        add(taskGrid);*/

        String route = RouteConfiguration.forSessionScope().getUrl(this.getClass());
        //  log.info("taskserv_list:\n" + taskService.list(PageRequest.ofSize(1)).toString());
        // List<SimpleEntity> simpleEntities = getItems(simpleFields);
        //   Grid<SimpleEntity> grid = new Grid<>(DataService.getSimpleEntities(route));
        //simpleEntities = DataService.getSimpleEntities(route);
        //   init();
        // log.info(simpleEntities.toString());

        simpleEntities = DataService.getSimpleEntities(route, true);
        System.out.println(String.format("%s: %d", route, simpleEntities.size()));

        grid.setItems(simpleEntities);

        for (SimpleField f : simpleEntities.get(0).getFieldList()) {
            grid.addColumn(x -> x.getValue(f.getFieldName()))
                    .setComparator((o1, o2) -> compareSimple(o1, o2, f))
                    .setHeader(f.getCaption())
                    .setResizable(true);
        }
        GridContextMenu<SimpleEntity> contextMenu = grid.addContextMenu();
        for (SimpleField f : simpleEntities.get(0).getFieldList()) {
            contextMenu.addItem(f.getCaption(), x -> {
                x.getItem().get().setValue(f, "EMPTY");
                grid.getDataProvider().refreshAll();
            });
        }
        grid.setPageSize(16);
        grid.setPaginatorSize(5);
        grid.setAllRowsVisible(true);
        // grid.setSortableColumns();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button btnAdd = new Button("Добавить", new Icon(VaadinIcon.PLUS),
                x -> {
                    MessagesFields mf = new MessagesFields(new SimpleEntity(simpleEntities.get(0).getFieldList()));
                    Utils.customWindow(mf,
                            y -> {
                                SimpleEntity entity = mf.generateModelValue();
                                log.info(entity.toString());
                                if (!mf.existsErrors())
                                    DataService.addEntity(entity, route);
                                grid.getDataProvider().refreshItem(entity);
                                grid.getDataProvider().refreshAll();
                                grid.select(entity);
                            },
                            z -> Notification.show("cancel"));
                });
        Button btnEdit = new Button("Редактировать", new Icon(VaadinIcon.EDIT),
                x -> {
                    if (!grid.getSelectedItems().stream().findFirst().isEmpty()) {
                        SimpleEntity selEntity = grid.getSelectedItems().stream().findFirst().get();
                        MessagesFields mf = new MessagesFields(selEntity);
                        Utils.customWindow(mf,
                                y -> {
                                    SimpleEntity entity = mf.generateModelValue();
                                    log.info(entity.toString());
                                    if (!mf.existsErrors())
                                        DataService.updEntity(selEntity, entity, route);
                                    grid.getDataProvider().refreshItem(entity);
                                    grid.getDataProvider().refreshAll();
                                },
                                z -> Notification.show("cancel"));
                    }
                });
        Button btnDel = new Button("Удалить", new Icon(VaadinIcon.DEL),
                x -> {
                    if (!grid.getSelectedItems().stream().findFirst().isEmpty()) {
                        SimpleEntity selEntity = grid.getSelectedItems().stream().findFirst().get();
                        Utils.confirmWindow(new Div("Удалить запись?"),
                                y -> {
                                    DataService.delEntity(selEntity, route);
                                    grid.setItems(simpleEntities);
                                    grid.getDataProvider().refreshAll();
                                    Notification.show("Удален");
                                },
                                z -> Notification.show("cancel"));
                    }
                });

        buttonLayout.add(btnAdd, btnEdit, btnDel);
        add(buttonLayout);
        add(grid);
        setSizeFull();
    }

    private Object getValSimple(SimpleEntity s, SimpleField f){
        Class c = f.getTypeField().getCompClass();
        return c.cast(s.getValue(f.getFieldName()));
    }

    private int compareSimple(SimpleEntity o1, SimpleEntity o2, SimpleField f) {
        try {
           return ((Comparable) getValSimple(o1, f)).compareTo(getValSimple(o2, f));
           // if (c.isInstance(Comparator.class))
             //   return Map.class.compare(o1.getValue(f.getFieldName()), o1.getValue(f.getFieldName()));
        } catch (Exception e) {
            return 0;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(MessagesView.class);
}
