package com.elfstack.toys.taskmanagement.ui.view;

import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.PermitAll;

@Route("greet")
@PermitAll
public class SampleView extends Main implements HasUrlParameter<String> {
    @Override
    public void setParameter(BeforeEvent event, @Nullable String parameter) {
        setText(String.format("Hello, %s!", parameter));
    }
}
