package com.coteware.springscroll.script.statements;

import com.coteware.springscroll.spel.SpelService;

import java.util.Optional;

public abstract class AbstractStatement implements Statement {
    private Statement nextSequenceStatement;
    private final StatementTypeEnum statementType;
    protected SpelService spelService;

    public AbstractStatement(StatementTypeEnum statementType) {
        this.statementType = statementType;
    }

    @Override
    public Optional<Statement> nextSequenceStatement() {
        return Optional.ofNullable(nextSequenceStatement);
    }

    @Override
    public void setNextSequenceStatement(Statement nextSequenceStatement) {
        this.nextSequenceStatement = nextSequenceStatement;
    }

    public StatementTypeEnum getStatementType() {
        return statementType;
    }

    @Override
    public StatementResult execute() {
        echo().ifPresent(System.out::println);
        Optional<StatementResult> maybeStatementResult = doExecute();
        if (maybeStatementResult.isPresent()) {
            return maybeStatementResult.get();
        } else {
            if (nextSequenceStatement().isPresent()) {
                return new BaseStatementResult(StatementResultType.NEXT, nextSequenceStatement);
            } else {
                return new BaseStatementResult(StatementResultType.TERMINUS,null);
            }
        }
    }

    protected abstract Optional<StatementResult> doExecute();

    public Optional<String> echo() {
        String msg = "SYSTEM ECHO:> " + this.getStatementType() + " " + doEcho().orElse("");
        return Optional.of(msg);
    }

    protected abstract Optional<String> doEcho();

    public void setSpelService(SpelService spelService) {
        this.spelService = spelService;
    }
}
