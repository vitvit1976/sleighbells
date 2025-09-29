package com.elfstack.toys.taskmanagement.ui.view;

import com.elfstack.toys.taskmanagement.domain.SimpleDict;
import com.elfstack.toys.taskmanagement.domain.SimpleEntity;
import com.elfstack.toys.taskmanagement.domain.SimpleField;
import com.elfstack.toys.taskmanagement.service.DataService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.HasListDataView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;


public class MessagesFields extends CustomField<SimpleEntity> {
    private Binder<SimpleEntity> binder = new Binder<>();
    private SimpleEntity simpleEntity;
    private Boolean isError = false;

    public MessagesFields(SimpleEntity simpleEntity) {
        this.simpleEntity = simpleEntity;
        if (simpleEntity != null) init();
    }

    private void init() {
        TabSheet tabSheet = new TabSheet();
        VerticalLayout layout = new VerticalLayout();
        int i = 0;
        for (SimpleField f : simpleEntity.getFieldMap().keySet()) {
            if (i % 6 == 0 && i > 0) {
                tabSheet.add("This_" + i / 6, layout);
                layout = new VerticalLayout();
            }
            i++;
            AbstractField field = f.getTypeField().getField();
            FormLayout formLayout = new FormLayout();
            formLayout.addFormItem(field, f.getCaption());
            layout.add(formLayout);

            if (field instanceof HasListDataView) {
                ((HasListDataView) field).setItems(f.getSubData());
                if (field instanceof Select)
                    ((Select) field).setItemLabelGenerator(x -> ((SimpleDict) x).getVal().toString());
                if (field instanceof ComboBox)
                    ((ComboBox) field).setItemLabelGenerator(x -> ((SimpleDict) x).getVal().toString());
                if (field instanceof ListBox)
                    ((ListBox) field).setItemLabelGenerator(x -> ((SimpleDict) x).getVal().toString());
                if (field instanceof RadioButtonGroup)
                    ((RadioButtonGroup) field).setItemLabelGenerator(x -> ((SimpleDict) x).getVal().toString());
                field.getStyle().set("font-size", "0.5em");

            }
            if (f.getTypeField().getCompClass().isInstance(MultiSelectComboBox.class)) {
                MultiSelectComboBox<SimpleDict> box = (MultiSelectComboBox) field;
                binder.forField(box).
                        bind(x -> (Set<SimpleDict>) x.getValue(f.getFieldName()),
                                (x, y) -> x.setValue(f, y));
            } else {
                field.getStyle().set("transform", "scale(1.2)");
                binder.forField(field).
                       // withNullRepresentation(DataService.getRandomValue(f)).
                        bind(x -> f.getFieldClass().cast(((SimpleEntity) x).getValue(f.getFieldName())),
                                (x, y) -> ((SimpleEntity) x).setValue(f, f.getFieldClass().cast(y)));
            }
        }
        tabSheet.add("Last", layout);
        setPresentationValue(simpleEntity);
        add(tabSheet);
    }

    @Override
    protected SimpleEntity generateModelValue() {
        try {
            binder.writeBean(simpleEntity);
        } catch (ValidationException e) {
            isError = true;
        }
        return simpleEntity;
    }

    @Override
    protected void setPresentationValue(SimpleEntity simpleEntity) {
        binder.readBean(simpleEntity);
    }

    public boolean existsErrors() {
        return isError;
    }

    private static final Logger log = LoggerFactory.getLogger(MessagesFields.class);
}
