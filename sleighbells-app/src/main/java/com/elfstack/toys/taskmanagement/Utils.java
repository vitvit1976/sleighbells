package com.elfstack.toys.taskmanagement;

import com.elfstack.toys.taskmanagement.domain.SimpleEntity;
import com.elfstack.toys.taskmanagement.domain.SimpleField;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.ValueProvider;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

public class Utils {

    public static <T> T customWindow(CustomField<T> field, ComponentEventListener<ClickEvent<Button>> confirmListener,
                                     ComponentEventListener<ClickEvent<Button>> cancelListener) {
        Dialog edt = new Dialog();
        Button btnOk = new Button("OK", confirmListener);
        Button btnCancel = new Button("отмена", x -> edt.close());
        if (cancelListener != null) btnCancel.addClickListener(cancelListener);
        btnOk.addClickListener(x -> {
            String errMess = field.getErrorMessage();
            if (errMess == null || errMess.isEmpty()) edt.close();
            else Notification.show(errMess, 1000, Notification.Position.MIDDLE);
        });
        edt.setModal(true);
        edt.add(field, new HorizontalLayout(btnOk, btnCancel));
        edt.open();
        return field.getValue();
    }

    public static void confirmWindow(Div d, ComponentEventListener<ClickEvent<Button>> confirmListener,
                                     ComponentEventListener<ClickEvent<Button>> cancelListener) {
        Dialog edt = new Dialog();
        Button btnOk = new Button("OK", confirmListener);
        Button btnCancel = new Button("отмена", x -> edt.close());
        if (cancelListener != null) btnCancel.addClickListener(cancelListener);
        btnOk.addClickListener(x -> edt.close());
        edt.setModal(true);
        edt.add(d, new HorizontalLayout(btnOk, btnCancel));
        edt.open();
    }


    public static void showOverView() {
        Dialog dialog = new Dialog();
        dialog.add(new Text("You have unsaved changes that will be discarded if you navigate away."));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        Span message = new Span();

        Button confirmButton = new Button("Confirm", event -> {
            message.setText("Confirmed!");
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> {
            message.setText("Cancelled...");
            dialog.close();
        });
// Cancel action on ESC press
        Shortcuts.addShortcutListener(dialog, () -> {
            message.setText("Cancelled...");
            dialog.close();
        }, Key.ESCAPE);

        dialog.add(new Div(confirmButton, cancelButton));
        dialog.open();
    }


    public static Object castObj(Object o, Class<?> c) {
        try {
            return c.cast(o);
        } catch (Exception e) {
            return null;
        }
    }

    public static  <T> ValueProvider<T,?> getter(Field fld) {
        return bean->{
            try {
                return FieldUtils.readField(fld, bean, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    public static <T> Setter<T,?> setter(Field fld) {
        return (bean, value ) -> {
            try {
                FieldUtils.writeField(fld, bean, value, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        };
    }
}
