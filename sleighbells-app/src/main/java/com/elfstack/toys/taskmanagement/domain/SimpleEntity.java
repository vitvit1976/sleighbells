package com.elfstack.toys.taskmanagement.domain;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "simpleEntity")
public class SimpleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "task_id")
    private Long id;

    @Transient
    private Map<SimpleField, Object> fieldMap = new HashMap<>();

    public Map<SimpleField, Object> getFieldMap() {
        return fieldMap;
    }

    public List<SimpleField> getFieldList() {
        return fieldMap.keySet().stream().toList();
    }

    public Object getValue(String nmField) {
        for (Map.Entry<SimpleField, Object> e : fieldMap.entrySet()) {
            if (e.getKey().getFieldName().equals(nmField)) return e.getValue();
        }
        return null;
    }
/*
    public String getStringValue(String nmField) {
        Object o = getValue(nmField);
        if (o == null) return "";
        else return o.toString();
    }
*/
    public SimpleEntity(List<SimpleField> fields) {
       fields.forEach(x->fieldMap.put(x, null));
    }

    public SimpleEntity(Map<SimpleField, Object> fieldMap) {
        this.fieldMap.putAll(fieldMap);
    }

    public void setValue(SimpleField field, Object val) {
        fieldMap.put(field, val);
    }

    public void updValue(SimpleEntity entity) {
        this.fieldMap.putAll(entity.fieldMap);
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleEntity entity = (SimpleEntity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/

    @Override
    public String toString() {
        return "SimpleEntity{" +
                "id=" + id +
                ", fieldMap=" + fieldMap +
                "}\n";
    }

    private static final Logger log = LoggerFactory.getLogger(SimpleEntity.class);
}
