package com.elfstack.toys.taskmanagement.ui.view;

import com.elfstack.toys.taskmanagement.domain.TYPE_ANY_TASK;
import com.elfstack.toys.taskmanagement.service.TaskService;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.time.Clock;
import java.util.Arrays;
import java.util.List;

@Route("task-inbox")
@PageTitle("inbox")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task inbox")
@PermitAll
public class InboxView extends MessagesView {

    @Override
    public List<String> getNmFields() {
        return Arrays.asList("inbox1", "ibox_Next", "NextField", "more_field", "anyField");
    }

    public InboxView(TaskService taskService, Clock clock) {
        super(taskService, clock);
    }
}
