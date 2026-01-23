package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DataTypeEnum;
import com.coteware.springscroll.script.literals.Literal;

import java.util.Optional;

public class ExpressionResult<S,E extends Literal<S>> {
    private final E literal;
    public ExpressionResult(E literal) {
        this.literal = literal;
    }

    public Optional<E> getLiteral() {
        return Optional.ofNullable(literal);
    }

    public DataTypeEnum getType() {
        return literal.getDataType();
    }
}
