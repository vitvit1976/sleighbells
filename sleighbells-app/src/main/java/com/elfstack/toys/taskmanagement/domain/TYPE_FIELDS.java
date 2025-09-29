package com.elfstack.toys.taskmanagement.domain;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.timepicker.TimePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public enum TYPE_FIELDS {
    TFTextField(TextField.class),
 //   TFEmailField(EmailField.class),
    TFTextArea(TextArea.class),
    TFNumberField(NumberField.class, Double.class),
    TFIntegerField(IntegerField.class, Integer.class),
    TFPasswordField(PasswordField.class),
    TFCheckbox(Checkbox.class, Boolean.class),
    TFDateTimePicker(DateTimePicker.class, LocalDateTime.class),
    TFDatePicker(DatePicker.class, LocalDate.class),
    TFTimePicker(TimePicker.class, LocalTime.class),
    TFComboBox(ComboBox.class, SimpleDict.class, true),
    TFListBox(ListBox.class, SimpleDict.class, true),
   // TFMultiSelectListBox(MultiSelectListBox.class, Set.class, true),
   // TFMultiSelectComboBox(MultiSelectComboBox.class, Set.class, true),
    TFRadioButtonGroup(RadioButtonGroup.class, SimpleDict.class, true),
    TFSelect(Select.class, SimpleDict.class, true)
;

    private Class<? extends AbstractField> clazz;
    private Class<? extends Comparable> compClass;

    public Boolean getHasChild() {
        return hasChild;
    }

    private Boolean hasChild;

    TYPE_FIELDS(Class<? extends AbstractField> clazz) {
        this(clazz, false); 
    }

    TYPE_FIELDS(Class<? extends AbstractField> clazz, Class<? extends Comparable> compClass, Boolean hasChild) {
        this.clazz = clazz;
        this.compClass = compClass;
        this.hasChild = hasChild;
    }
    
    TYPE_FIELDS(Class<? extends AbstractField> clazz, Boolean hasChild) {
        this(clazz, String.class, hasChild);
    }

    TYPE_FIELDS(Class<? extends AbstractField> clazz, Class<? extends Comparable> compClass) {
        this(clazz, compClass, false);
    }

    public static TYPE_FIELDS getTypeField(int i) {
        try {
            return Arrays.asList(TYPE_FIELDS.values()).get(i);
        } catch (Exception e) {
            return TYPE_FIELDS.TFTextField;
        }
    }

    public Class<? extends AbstractField> getClazz() {
        return clazz;
    }

    public Class<?> getCompClass() {
        return compClass;
    }

    public AbstractField getField() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
