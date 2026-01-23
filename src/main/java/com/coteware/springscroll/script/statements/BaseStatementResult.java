package com.coteware.springscroll.script.statements;

import java.util.Optional;

public class BaseStatementResult implements StatementResult {
    private final StatementResultType statementResultType;
    private final Statement nextStatement;

    public BaseStatementResult(StatementResultType statementResultType, Statement nextStatement) {
        this.statementResultType = statementResultType;
        this.nextStatement = nextStatement;
    }

    @Override
    public StatementResultType getStatementResultType() {
        return statementResultType;
    }

    @Override
    public Optional<Statement> getNextStatement() {
        return Optional.ofNullable(nextStatement);
    }
}
