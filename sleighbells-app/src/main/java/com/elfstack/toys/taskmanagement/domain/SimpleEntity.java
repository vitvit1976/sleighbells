package com.elfstack.toys.taskmanagement.domain;

import com.elfstack.toys.taskmanagement.service.DataService;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

//@Entity
//@Table(name = "simpleEntity")
public class SimpleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "task_id")
    private Long id = DataService.getNextID();

   // @Transient
    private Map<SimpleField, Object> fieldMap = new HashMap<>();

    public Map<SimpleField, Object> getFieldMap() {
        return fieldMap;
    }

   /* public List<SimpleField> getFieldList() {
        return fieldMap.keySet().stream().toList();
    }

    */

    public Object getValue(String nmField) {
        for (Map.Entry<SimpleField, Object> e : fieldMap.entrySet())
            if (e.getKey().getFieldName().equals(nmField)) return e.getValue();
        return null;
    }

    public SimpleEntity() {
    }
    public SimpleEntity(List<SimpleField> fields) {
        fields.forEach(x -> fieldMap.put(x, null));
    }

    public SimpleEntity(Map<SimpleField, Object> fieldMap) {
        this.fieldMap.putAll(fieldMap);
    }

    public void setValue(SimpleField field, Object val) {
        fieldMap.put(field, val);
    }

    public void setValue(String nmField, Object val) {
        for (Map.Entry<SimpleField, Object> e : fieldMap.entrySet())
            if (e.getKey().getFieldName().equals(nmField)) e.setValue(val);
    }

    public void updValue(SimpleEntity entity) {
        this.fieldMap.putAll(entity.fieldMap);
    }

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
    }

    @Override
    public String toString() {
        return "SimpleEntity{" +
                "id=" + id +
                ", fieldMap=" + fieldMap +
                "}\n";
    }

    private static final Logger log = LoggerFactory.getLogger(SimpleEntity.class);

    public static void main(String[] args) {
        List<SimpleField> fields = DataService.getFields(true);
        List<SimpleEntity> simpleEntities = new ArrayList<>();

        for (int x = 0; x < 3; x++) {
            // for(SimpleField f: fields) f.setFieldValue(nmData +"_f" + x);
            Map<SimpleField, Object> addFields = new HashMap<>();
            fields.forEach(z -> addFields.put(z, DataService.getRandomValue(z)));
            simpleEntities.add(new SimpleEntity(addFields));
        }
        System.out.println(Objects.equals(simpleEntities.get(0).id, simpleEntities.get(1).id));
    }
}
