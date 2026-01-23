package com.coteware.springscroll.script.declarations;

import com.coteware.springscroll.script.variables.StringVariable;

public class StringDeclarationSpec extends AbstractDeclarationSpec<String> {

    public StringDeclarationSpec(String name) {
        super(name, DataTypeEnum.STRING);
        setVariable(new StringVariable(name));
    }
}
