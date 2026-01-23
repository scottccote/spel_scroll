package com.coteware.springscroll.script.variables;

import com.coteware.springscroll.script.declarations.DataTypeEnum;
import com.coteware.springscroll.script.literals.Literal;

public interface Variable<T> {
    String getName();
    DataTypeEnum getDataTypeEnum();
    Literal<T> getValue();
    void setValue(Literal<T> value);
}
