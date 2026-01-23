package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;

public class LogicalExpressionBuilder implements
        ExpressionBuilder,UnaryLogicalOperation,LogicalOperation {
    private boolean isBuilt;
    private boolean unaryOperation;
    private boolean isNull;
    private boolean isNot;
    private LogicalExpression expression;

    public LogicalExpressionBuilder() {
        init();
    }

    private void init() {
        isBuilt = false;
        unaryOperation = false;
        isNull = false;
        isNot = false;
        expression = new LogicalExpression();
    }

    @Override
    public UnaryLogicalOperation unaryOperation() {
        this.unaryOperation = true;
        return this;
    }

    @Override
    public Expression build() {

        if (this.expression.declarationSpecMap.isEmpty()) {
            throw new ScrollAssemblyException("You must specify at least one declaration specification");
        }

        if (!this.unaryOperation) {
            throw new ScrollAssemblyException("You must specify a unary operation");
        }

        if (this.isNull) {
            expression.setNull();
        }

        if (this.isNot) {
            expression.setNot();
        }

        this.isBuilt=true;

        return expression;
    }



    @Override
    public void reset() {
        init();
    }

    @Override
    public LogicalOperation addNull() {
        isNull = true;
        return this;
    }

    @Override
    public LogicalOperation addDeclarationSpec(DeclarationSpec declarationSpec) {
        this.expression.add(declarationSpec);
        return this;
    }

    @Override
    public LogicalOperation setIsNot(boolean isNot) {
        this.isNot = isNot;
        return this;
    }

    @Override
    public LogicalOperation logicalOperation() {
        return this;
    }
}
