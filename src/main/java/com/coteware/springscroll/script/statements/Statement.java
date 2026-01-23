package com.coteware.springscroll.script.statements;

import com.coteware.springscroll.script.ScopeMemory;
import com.coteware.springscroll.script.expresions.Expression;
import com.coteware.springscroll.spel.SpelService;

import java.util.Optional;

public interface Statement {
    default ScopeMemory newScopeMemory(ScopeMemory scopeMemory) {
        return new ScopeMemory(scopeMemory);
    }

    default void add(Expression build) {}

    Optional<Statement> nextSequenceStatement();

    void setNextSequenceStatement(Statement nextSequenceStatement);

    StatementResult execute();
    Optional<String> echo();
    void setSpelService(SpelService spelService);
}
