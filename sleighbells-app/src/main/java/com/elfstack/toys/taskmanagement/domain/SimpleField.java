package com.elfstack.toys.taskmanagement.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleField {
    private String fieldName;
    private Class<?> fieldClass;
    private String caption;

    private TYPE_FIELDS typeField;
    private List<SimpleDict> subData;

    public SimpleField() {
    }

    public List<SimpleDict> getSubData() {
        return subData;
    }

    @XmlAttribute(name = "FieldName")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @XmlAttribute(name = "Caption")
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @XmlAttribute(name = "FieldClass")
    @XmlJavaTypeAdapter(ItemFieldsAdapter.class)
    public Class<?> getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(Class<?> fieldClass) {
        this.fieldClass = fieldClass;
    }

    public TYPE_FIELDS getTypeField() {
        return typeField;
    }

    public void setTypeField(TYPE_FIELDS typeField) {
        this.typeField = typeField;
    }


    public SimpleField(String fieldName, Class<?> fieldClass) {
        this(fieldName, fieldClass, fieldName, TYPE_FIELDS.TFTextField);
    }

    public SimpleField(String fieldName, Class<?> fieldClass, String caption) {
        this(fieldName, fieldClass, caption, TYPE_FIELDS.TFTextField);
    }

    public SimpleField(String fieldName, Class<?> fieldClass, String caption, TYPE_FIELDS typeField) {
        this(fieldName, fieldClass, caption, typeField, null);
    }

    public SimpleField(String fieldName, Class<?> fieldClass, String caption, TYPE_FIELDS typeField, List<SimpleDict>  subData) {
        this.fieldName = fieldName;
        this.fieldClass = fieldClass;
        this.caption = caption;
        this.typeField = typeField;
        this.subData = subData;
    }

    public SimpleField(SimpleField simpleField) {
        this.fieldName = simpleField.fieldName;
        this.fieldClass = simpleField.fieldClass;
        this.caption = simpleField.caption;
        this.typeField = simpleField.typeField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleField that = (SimpleField) o;
        return Objects.equals(fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName);
    }


    @Override
    public String toString() {
        return "SimpleField{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldClass=" + fieldClass +
                ", caption='" + caption + '\'' +
                ", typeField=" + typeField +
                ", subData=" + subData;
    }
}