package com.elfstack.toys.taskmanagement.ui.view;

import com.elfstack.toys.taskmanagement.domain.SimpleEntity;
import com.elfstack.toys.taskmanagement.domain.SimpleField;
import com.elfstack.toys.taskmanagement.service.DataService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.HasListDataView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessagesFields extends CustomField<SimpleEntity> {
    private Binder<SimpleEntity> binder = new Binder<>();
    private SimpleEntity simpleEntity;
    private Boolean isError=false;


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

            ListBox b = new ComboBox();
            b.setItemLabelGenerator(

            if (field instanceof HasListDataView) {
                ((HasListDataView) field).setItems(f.getSubData());

            }

            if (!f.getTypeField().getHasChild())
                    binder.forField(field).
                    withNullRepresentation(DataService.getRandomValue(f.getTypeField())).
                            bind(x -> castObj(((SimpleEntity) x).getValue(f.getFieldName()), f.getFieldClass()),
                            (x, y) -> ((SimpleEntity) x).setValue(f, y));
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

    private String castGetValue(Class<?> clazz, Object val) {
        try {
            if (val == null) return "";
            return clazz.cast(val).toString();
        } catch (Exception e) {
            return "";
        }
    }

    private Object castObj(Object o, Class<?> c) {
        try {
            return c.cast(o);
        } catch (Exception e) {
            return null;
        }
    }

    private Object castSetValue(Class<?> clazz, String val) {
        try {
            log.info(val);
            if (val.isEmpty()) return null;
            return clazz.cast(val);
        } catch (Exception e) {
            return null;
        }
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
