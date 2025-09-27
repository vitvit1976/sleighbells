package com.elfstack.toys.taskmanagement.ui.view;

import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("task-trash")
@PageTitle("trash")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task trash")
@PermitAll
public class TrashView extends Main {
}
