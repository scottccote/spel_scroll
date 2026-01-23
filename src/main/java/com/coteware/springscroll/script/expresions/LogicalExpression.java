package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import com.coteware.springscroll.script.literals.BooleanLiteral;
import com.coteware.springscroll.script.literals.Literal;

import java.util.Optional;

public class LogicalExpression <S,T extends Literal<S>> extends AbstractExpression<S,T> {


    private boolean isNull = false;
    private boolean isNot = false;


    @Override
    public <M> void add(Literal<M> literal) {
        throw new UnsupportedOperationException("literals are not added to logical expressions");
    }

    @Override
    public String doEcho() {
        return echoDeclarationSpecMap();
    }

    @Override
    public ExpressionResult<S, T> doEvaluate() {
        if (1 != declarationSpecMap.size()) {
            throw new ScrollAssemblyException("a logical expression should have exactly one declaration");
        }

        Optional<DeclarationSpec<?>> maybeDeclaration = declarationSpecMap.values().stream().findFirst();

        if (!maybeDeclaration.isPresent()) {
            throw new ScrollAssemblyException("No declaration specified for logical expression");
        }

        DeclarationSpec<?> declaration = maybeDeclaration.get();
        final boolean isBoolean;
        switch(declaration.getVariable().getDataTypeEnum()) {
            case BOOLEAN:
                isBoolean = true;
                break;
            default:
                isBoolean = false;
        }
        final boolean isNullCheck = isNull();
        final boolean isNotCheck = isNot();


        if(isNotCheck) {
            if (isBoolean) {
                // return the not of the boolean value
                Boolean booleanVariableValue = (Boolean) declaration.getVariable().getValue().getValue();
                return new ExpressionResult(new BooleanLiteral(!booleanVariableValue));
            } else if (isNullCheck) {
                // see if this is null return false if it is
                Literal<?> variableValue = declaration.getVariable().getValue();
                return new ExpressionResult(new BooleanLiteral(null != variableValue));
            } else {
                // this doesn't make sense if we are here - how to do a not of a string or numeric?  morethoughts ...
                throw new ScrollAssemblyException("cannot do NOT on a numeric or string variable");
            }
        } else {
            // this is not a not check
            if (isNullCheck) {
                // return true if the value is null
                Literal<?> variableValue = declaration.getVariable().getValue();
                return new ExpressionResult(new BooleanLiteral(null == variableValue.getValue()));
            } else {
                // this is neither a null or a not check - just return the variable contained in the declaration
                return new ExpressionResult(declaration.getVariable().getValue());
            }
        }
    }

    public void setNull() {
        this.isNull = true;
    }
    public boolean isNull() {
        return isNull;
    }

    public void setNot() {
        this.isNot = true;
    }
    public boolean isNot() {
        return isNot;
    }



    public <M> void add(DeclarationSpec<M> declarationSpec) {
        if (!declarationSpecMap.isEmpty()) {
            throw new ScrollAssemblyException("declarationSpec already added to the logical expression");
        }
        declarationSpecMap.put(declarationSpec.getName(),declarationSpec);
    }
}
