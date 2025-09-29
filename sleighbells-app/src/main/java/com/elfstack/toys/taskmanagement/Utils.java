package com.elfstack.toys.taskmanagement;

import com.elfstack.toys.taskmanagement.domain.SimpleDS;
import com.elfstack.toys.taskmanagement.domain.SimpleEntity;
import com.elfstack.toys.taskmanagement.domain.SimpleField;
import com.elfstack.toys.taskmanagement.service.DataService;
import com.elfstack.toys.taskmanagement.ui.view.MessagesView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
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

    /*
    public static <T> void customGenWindow(List<TestField<T, Component>> list) {
        Dialog edt = new Dialog();
        Button btnOk = new Button("OK");
        Button btnCancel = new Button("отмена", x -> edt.close());
        VerticalLayout field = new VerticalLayout();
        list.forEach(x -> {
            field.add(x.getField());
        });
        edt.setModal(true);
        edt.add(field, new HorizontalLayout(btnOk, btnCancel));
        edt.open();
    }


    public static void confirmMessage(Predicate<NativeLabel> predicate, ComponentEventListener<ClickEvent<Button>> confirmListener) {
        confirmMessage(predicate, confirmListener, null, null);
    }

    public static void confirmMessage(Predicate<NativeLabel> predicate, ComponentEventListener<ClickEvent<Button>> confirmListener, File imageFile) {
        confirmMessage(predicate, confirmListener, null, imageFile);
    }
    */

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

    public static SimpleEntity getSimpleEntityFormXML(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SimpleEntity.class);
            Unmarshaller jaxbContextUnmarshaller = jaxbContext.createUnmarshaller();
            return (SimpleEntity) jaxbContextUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            return null;
        }
    }

    public static void setSimpleEntityToXML(SimpleEntity entity, File file) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(SimpleEntity.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.marshal(entity, file);
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String setMaperSimpleDS(SimpleDS entity){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static SimpleDS getMaperSimpleDS(String s){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.readValue(s, SimpleDS.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static void main(String[] args) {
        //File file = new File("/home/vitvit/Downloads/text.xml");
        //setSimpleEntityToXML(new SimpleEntity(DataService.getFields(true)), file);
        SimpleDS ds = new SimpleDS(DataService.getRandom(10), DataService.getRandom(10), DataService.getFields(true));
       // ds.setSimpleEntities(DataService.getSimpleEntities("test", true));
        String s =setMaperSimpleDS(ds);
        log.info(s);
        log.info("----------------------------");
        log.info(getMaperSimpleDS(s).toString());
    }

}
