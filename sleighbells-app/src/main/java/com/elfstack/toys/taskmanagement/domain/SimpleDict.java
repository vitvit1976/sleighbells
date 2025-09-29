package com.elfstack.toys.taskmanagement.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SimpleDict implements Comparable<SimpleDict> {
    private SimpleEntity simpleEntity;

    public Object getKey() {
        return simpleEntity.getValue("key");
    }

    public void setKey(Object key) {
        this.simpleEntity.setValue("key", key);
    }

    public Object getVal() {
        return simpleEntity.getValue("value");
    }

    public void setVal(Object val) {
        this.simpleEntity.setValue("value", val);
    }

    public SimpleDict() {
    }

    public SimpleDict(SimpleEntity simpleEntities) {
        this.simpleEntity = simpleEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleDict that = (SimpleDict) o;
        return Objects.equals(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }

    @Override
    public int compareTo(@NotNull SimpleDict o) {
        try {
            return ((Comparable) getVal()).compareTo(o.getVal());
        } catch (Exception e) {
            return 0;
        }
    }


}
