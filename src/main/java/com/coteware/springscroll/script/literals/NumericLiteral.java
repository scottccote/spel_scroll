package com.coteware.springscroll.script.literals;

import com.coteware.springscroll.script.declarations.DataTypeEnum;

public abstract class NumericLiteral<T extends Number> extends AbstractLiteral<T> implements Negatable<T>{

    private boolean negated = false;

    public NumericLiteral(T value, DataTypeEnum dataTypeEnum) {
        super(value, dataTypeEnum);
    }

    public boolean isNegated() {
        return negated;
    }

    public void negate() {
        negated = !negated;
    }

    @Override
    public T getNotNegatedValue() {
        return super.getValue();
    }

    public abstract T getNegatedValue();

}
