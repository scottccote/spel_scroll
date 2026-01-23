package com.coteware.springscroll.script.variables;

import com.coteware.springscroll.script.declarations.DataTypeEnum;

public class BooleanVariable extends AbstractVariable<Boolean>{

    public BooleanVariable(String name) {
        super(name, DataTypeEnum.BOOLEAN);
    }
}
