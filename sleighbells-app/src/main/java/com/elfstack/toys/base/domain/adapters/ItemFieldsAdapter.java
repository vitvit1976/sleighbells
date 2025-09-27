package com.elfstack.toys.base.domain.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class ItemFieldsAdapter extends XmlAdapter<String, Class<?>> {
    @Override
    public Class<?> unmarshal(String v) {
        Class<?> type = null;
        try {
            type = Class.forName(v);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return type;
    }

    @Override
    public String marshal(Class<?> v) {
        return v.getSimpleName();
    }
}