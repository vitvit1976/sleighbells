package com.elfstack.toys.taskmanagement.ui.view.converters;

import com.elfstack.toys.taskmanagement.domain.SimpleDict;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.util.Map;

public class ComboBoxConverter<KEY extends Comparable> implements Converter<SimpleDict, KEY> {

    private Map<KEY, SimpleDict> keyvalSimpleDict;
    private Class<KEY> keyClass;

    public ComboBoxConverter(Map<KEY, SimpleDict> keyvalSimpleDict, Class<KEY> keyClass) {
        this.keyvalSimpleDict = keyvalSimpleDict;
        this.keyClass = keyClass;
    }

    @Override
    public Result<KEY> convertToModel(SimpleDict simpleDict, ValueContext valueContext) {
        if (simpleDict == null) {
            return Result.ok(null);
        }
        return Result.ok(keyClass.cast(simpleDict.getVal()));
    }

    @Override
    public SimpleDict convertToPresentation(KEY key, ValueContext valueContext) {
        return key==null ? null : keyvalSimpleDict.get(key);
    }

}
