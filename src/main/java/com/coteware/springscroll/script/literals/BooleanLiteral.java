package com.coteware.springscroll.script.literals;

import com.coteware.springscroll.script.declarations.DataTypeEnum;

public class BooleanLiteral extends AbstractLiteral<Boolean> implements Negatable<Boolean>{
    public BooleanLiteral(Boolean value) {
        super(value, DataTypeEnum.BOOLEAN);
    }

    private boolean negated = false;

    @Override
    public boolean isNegated() {
        return negated;
    }

    @Override
    public void negate() {
        negated = !negated;
    }

    @Override
    public Boolean getNotNegatedValue() {
        return super.getValue();
    }

    @Override
    public Boolean getNegatedValue() {
        return !super.getValue();
    }
}
