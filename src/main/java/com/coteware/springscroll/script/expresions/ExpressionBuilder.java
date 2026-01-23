package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.literals.Literal;

public interface ExpressionBuilder<R,S extends Literal<R>, T extends Expression<R,S>> {
    Expression<R,S> build();
    void reset();
    default <Q> ExpressionBuilder<R,S,T> addLiteral(Literal<Q> literal) {
        return this;
    }

    default <Q> ExpressionBuilder<R,S,T> addDeclarationSpec(DeclarationSpec<Q> declarationSpec) {
        return this;
    }

}
