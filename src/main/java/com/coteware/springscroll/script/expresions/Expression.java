package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.literals.Literal;
import com.coteware.springscroll.spel.SpelService;

/**
 * an expression eventually resolves to a literal
 * @param <S>
 * @param <T> the type of literal based on S
 */
public interface Expression<S,T extends Literal<S>> {
    <M> void add(Literal<M> literal);

    default <M> void add(DeclarationSpec<M> declarationSpec) {

    }

    ExpressionResult<S,T> evaluate();

    String echo();

    default void setSpelService(SpelService spelService) {

    }
}
