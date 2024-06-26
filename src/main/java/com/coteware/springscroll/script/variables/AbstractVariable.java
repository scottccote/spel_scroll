package com.coteware.springscroll.script.variables;

import com.coteware.springscroll.script.declarations.DataTypeEnum;

public class AbstractVariable<T> implements Variable<T>{
    private final String name;
    private final DataTypeEnum dataTypeEnum;
    private T value;

    public AbstractVariable(String name, DataTypeEnum dataTypeEnum) {
        this.name = name;
        this.dataTypeEnum = dataTypeEnum;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public DataTypeEnum getDataTypeEnum() {
        return dataTypeEnum;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }
}
