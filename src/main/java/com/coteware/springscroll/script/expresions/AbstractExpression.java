package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DataTypeEnum;
import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.literals.AbstractLiteral;
import com.coteware.springscroll.script.literals.Literal;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractExpression<S,T extends Literal<S>> implements Expression<S,T> {

    protected Map<String,DeclarationSpec<?>> declarationSpecMap = new HashMap<>();

    protected String echoDeclarationSpecMap() {
        return declarationSpecMap.entrySet().stream()
                .map(entry -> {
                    DeclarationSpec<?> declarationSpec = entry.getValue();
                    String declarationSpecName = declarationSpec.getName();
                    DataTypeEnum dataTypeEnum = declarationSpec.getVariable().getDataTypeEnum();

                    Literal<?> value = declarationSpec.getVariable().getValue();
                    return String.format("%s of %s is %s",declarationSpecName,dataTypeEnum, null != value?value.getValue():"");
                }).collect(Collectors.joining(","));
    }

    public <M> void add(DeclarationSpec<M> declarationSpec) {
        declarationSpecMap.put(declarationSpec.getName(),declarationSpec);
    }

    @Override
    public ExpressionResult<S,T> evaluate() {
        return doEvaluate();
    }

    protected abstract ExpressionResult<S,T> doEvaluate();

    public String echo() {
        return this.getClass().getSimpleName() + " " + doEcho();
    }

    public abstract String doEcho();
}
