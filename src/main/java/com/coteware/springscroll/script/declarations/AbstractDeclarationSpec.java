package com.coteware.springscroll.script.declarations;

import com.coteware.springscroll.script.variables.Variable;

public abstract class AbstractDeclarationSpec<T> implements DeclarationSpec<T> {
    protected final String name;
    protected Variable<T> variable;
    protected final DataTypeEnum dataTypeEnum;

    public AbstractDeclarationSpec(String name, DataTypeEnum dataTypeEnum) {
        this.name = name;
        this.dataTypeEnum = dataTypeEnum;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setVariable(Variable<T> variable) {
        if (!name.contentEquals(variable.getName())) {
            throw new IllegalArgumentException("Variable name mismatch");
        }

        if (this.dataTypeEnum != variable.getDataTypeEnum()) {
            throw new IllegalArgumentException("Variable data type mismatch");
        }

        this.variable = variable;
    }

    @Override
    public Variable<T> getVariable() {
        return variable;
    }
}
