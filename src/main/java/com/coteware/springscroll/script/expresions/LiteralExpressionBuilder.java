package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import com.coteware.springscroll.script.literals.Literal;

public class LiteralExpressionBuilder<R,S extends Literal<R>> implements ExpressionBuilder<R,S,LiteralExpression<R,S>> {
    private LiteralExpression<R,S> expression = new LiteralExpression<>();
    private Literal<?> literal;

    @Override
    public <Q> LiteralExpressionBuilder<R,S> addLiteral(Literal<Q> literal) {
        this.expression.add(literal);
        this.literal = literal;
        return this;
    }

    @Override
    public LiteralExpression<R,S> build() {
        if (null == this.literal) {
            throw new ScrollAssemblyException("The literal required to build the expression.");
        }
        return expression;
    }

    @Override
    public void reset() {
        this.expression = new LiteralExpression<R,S>();
        this.literal = null;
    }

    @Override
    public <Q> ExpressionBuilder<R, S, LiteralExpression<R, S>> addDeclarationSpec(DeclarationSpec<Q> declarationSpec) {
        throw new ScrollAssemblyException("Cannot add declaration specification to a LiteralExpression");
    }
}
