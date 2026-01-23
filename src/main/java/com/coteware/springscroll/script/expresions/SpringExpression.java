package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.literals.Literal;
import com.coteware.springscroll.script.literals.LiteralFactory;
import com.coteware.springscroll.script.literals.StringLiteral;
import com.coteware.springscroll.spel.SpelRoot;
import com.coteware.springscroll.spel.SpelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpringExpression<S,T extends Literal<S>> extends AbstractExpression<S,T> {
    private String literal;
    private SpelService spelService;

    @Override
    public <Q> void add(Literal<Q> literal) {
        this.literal = literal.getValue().toString();
    }

    @Override
    public String doEcho() {
        return literal + echoDeclarationSpecMap();
    }

    @Override
    public void setSpelService(SpelService spelService) {
        this.spelService = spelService;
    }

    @Override
    protected ExpressionResult<S, T> doEvaluate() {
        T springResult = evaluateSpringExpression();
        return new ExpressionResult<>(springResult);
    }

    private T evaluateSpringExpression() {
        SpelRoot spelRoot = new SpelRoot();
        Optional<Map.Entry<String, DeclarationSpec<?>>> maybeDeclaration = this.declarationSpecMap.entrySet().stream().findFirst();
        if (maybeDeclaration.isPresent()) {
            DeclarationSpec declarationSpec = maybeDeclaration.get().getValue();
            Literal value = declarationSpec.getVariable().getValue();
            spelRoot.setParameter(value.getValue());
        }
        Optional<?> result = this.spelService.evaluate(literal, spelRoot);
        if (result.isPresent()) {
            return (T) new StringLiteral(result.get().toString());
        }

        return null;
    }
}
