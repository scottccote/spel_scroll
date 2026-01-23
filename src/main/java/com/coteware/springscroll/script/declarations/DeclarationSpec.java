package com.coteware.springscroll.script.declarations;

import com.coteware.springscroll.script.variables.Variable;

public interface DeclarationSpec<T> {
    String getName();
    Variable<T> getVariable();
    void setVariable(Variable<T> variable);
}
