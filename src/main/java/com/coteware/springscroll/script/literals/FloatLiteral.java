package com.coteware.springscroll.script.literals;

import com.coteware.springscroll.script.declarations.DataTypeEnum;

public class FloatLiteral extends NumericLiteral<Float> {
    public FloatLiteral(Float value) {
        super(value, DataTypeEnum.FLOAT);
    }


    @Override
    public Float getNegatedValue() {
        return super.getValue() * -1f;
    }
}
