package com.coteware.springscroll.script.literals;

import com.coteware.springscroll.script.declarations.DataTypeEnum;

public class DoubleLiteral extends NumericLiteral<Double>{
    public DoubleLiteral(Double value) {
        super(value, DataTypeEnum.DOUBLE);
    }

    @Override
    public Double getNegatedValue() {
        return super.getValue() * -1.0d;
    }
}
