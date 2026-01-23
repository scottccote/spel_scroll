package com.coteware.springscroll.script.declarations;

import com.coteware.springscroll.script.variables.DoubleVariable;

public class DoubleDeclarationSpec extends AbstractDeclarationSpec<Double> {
    public DoubleDeclarationSpec(String name) {
        super(name, DataTypeEnum.DOUBLE);
        setVariable(new DoubleVariable(name));
    }
}
