package com.coteware.springscroll.script.declarations;

import com.coteware.springscroll.script.variables.IntegerVariable;

public class IntegerDeclarationSpec extends AbstractDeclarationSpec implements DeclarationSpec {

    public IntegerDeclarationSpec(String name) {
        super(name, DataTypeEnum.INTEGER);
        setVariable(new IntegerVariable(name));
    }
}
