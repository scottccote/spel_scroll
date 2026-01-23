package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.literals.Literal;

public class SpringExpressionBuilder implements ExpressionBuilder {
    private SpringExpression expression = new SpringExpression();

    public SpringExpressionBuilder addLiteral(Literal literal) {
        this.expression.add(literal);
        return this;
    }

    @Override
    public SpringExpressionBuilder addDeclarationSpec(DeclarationSpec declarationSpec) {
        this.expression.add(declarationSpec);
        return this;
    }

    @Override
    public Expression build() {
        return this.expression;
    }

    @Override
    public void reset() {
        this.expression = new SpringExpression();
    }
}
