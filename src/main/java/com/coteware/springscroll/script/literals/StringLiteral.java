package com.coteware.springscroll.script.literals;

import com.coteware.springscroll.script.declarations.DataTypeEnum;

public class StringLiteral extends AbstractLiteral<String> {
    public StringLiteral(String value) {
        super(value, DataTypeEnum.STRING);
    }
}
