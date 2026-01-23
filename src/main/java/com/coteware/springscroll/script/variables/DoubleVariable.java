package com.coteware.springscroll.script.variables;

import com.coteware.springscroll.script.declarations.DataTypeEnum;

public class DoubleVariable extends AbstractVariable<Double>{
    public DoubleVariable(String name) {
        super(name, DataTypeEnum.DOUBLE);
    }
}
