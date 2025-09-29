package com.elfstack.toys.taskmanagement.ui.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;


@Route("parameter")
@PermitAll
public class MessExtView extends Div
        implements HasUrlParameter<String> {

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String prm) {
        setText(String.format("Hello, %s!", prm));
    }


}