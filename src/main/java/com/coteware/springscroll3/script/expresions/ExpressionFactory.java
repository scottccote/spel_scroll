package com.coteware.springscroll3.script.expresions;

import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.ast.Literal;

public class ExpressionFactory {
    public Expression createExpression(Literal literal) {
        TypedValue literalValue = literal.getLiteralValue();
        Object objectLiteralValue = literalValue.getValue();
        LiteralExpression literalExpression = new LiteralExpression(objectLiteralValue);;
        return literalExpression;
    }
    public Expression createExpression(ExpressionTypeEnum expressionTypeEnum, Object expression) {
        switch (expressionTypeEnum) {
            case LOGICAL_EXPRESSION :
                break;
            case LITERAL:
                break;
            case SPRING_EXPRESSION:
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unsupported expression type: %s", expressionTypeEnum));
        }
        return null;
    }
}
