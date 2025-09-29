package com.elfstack.toys.taskmanagement.domain;

import java.util.List;

public class SimpleDS {
    private String sql;
    private String connect;
    private List<SimpleField> fields;
    private List<SimpleEntity> simpleEntities;

    public String getSql() {
        return sql;
    }

    public String getConnect() {
        return connect;
    }

    public List<SimpleField> getFields() {
        return fields;
    }

    public List<SimpleEntity> getSimpleEntities() {
        return simpleEntities;
    }

    public SimpleDS() {
    }

    public void setSimpleEntities(List<SimpleEntity> simpleEntities) {
        this.simpleEntities = simpleEntities;
    }

    public SimpleDS(String sql, String connect, List<SimpleField> fields) {
        this.sql = sql;
        this.connect = connect;
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "SimpleDS{" +
                "sql='" + sql + '\'' +
                ", connect='" + connect + '\'' +
                ", fields=" + fields +
                '}';
    }
}
