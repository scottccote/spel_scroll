package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.literals.Literal;

public class ExpressionBuilderFactory {
    private ExpressionBuilder expressionBuilder;

    public static ExpressionBuilderFactory newInstance() {
        return new ExpressionBuilderFactory();
    }

   public LogicalExpressionBuilder useLogicalExpression() {
        if (null == this.expressionBuilder) {
            LogicalExpressionBuilder  logicalExpressionBuilder = new LogicalExpressionBuilder();
            this.expressionBuilder = logicalExpressionBuilder;
            return logicalExpressionBuilder;
        } else {
            return (LogicalExpressionBuilder) this.expressionBuilder;
        }
    }
    public LiteralExpressionBuilder useLiteralExpression() {
        if (null == this.expressionBuilder) {
            LiteralExpressionBuilder literalExpressionBuilder = new LiteralExpressionBuilder();
            this.expressionBuilder = literalExpressionBuilder;
            return literalExpressionBuilder;
        } else {
            return (LiteralExpressionBuilder) this.expressionBuilder;
        }
    }

    public SpringExpressionBuilder useSpringExpression() {
        if (null == this.expressionBuilder) {
            SpringExpressionBuilder springExpressionBuilder = new SpringExpressionBuilder();
            this.expressionBuilder = springExpressionBuilder;
            return springExpressionBuilder;
        } else {
            return (SpringExpressionBuilder) this.expressionBuilder;
        }
    }

    public Expression build() {
        return this.expressionBuilder.build();
    }

    public <T extends ExpressionBuilder>  T addLiteral(Literal literal) {
        return (T) this.expressionBuilder.addLiteral(literal);
    }


    public <T extends ExpressionBuilder>  T addDeclarationSpec(DeclarationSpec declarationSpec) {
        return (T) this.expressionBuilder.addDeclarationSpec(declarationSpec);
    }
//
    public void reset() {
        this.expressionBuilder.reset();
        this.expressionBuilder = null;
    }
}
