package com.coteware.springscroll.script.declarations;

import com.coteware.springscroll.script.variables.FloatVariable;

public class FloatDeclarationSpec extends AbstractDeclarationSpec<Float> {
    public FloatDeclarationSpec(String name) {
        super(name, DataTypeEnum.FLOAT);
        setVariable(new FloatVariable(name));
    }
}
