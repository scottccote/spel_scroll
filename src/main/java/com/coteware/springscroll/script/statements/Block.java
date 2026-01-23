package com.coteware.springscroll.script.statements;

import com.coteware.springscroll.script.ScopeMemory;
import com.coteware.springscroll.script.expresions.ExpressionBuilderFactory;

import java.util.*;

public class Block extends AbstractStatement {
    private final ScopeMemory scopeMemory;
    private final ArrayList<Statement> statements = new ArrayList<>();
    private final ExpressionBuilderFactory expressionBuilder = ExpressionBuilderFactory.newInstance();


    public Block(ScopeMemory scopeMemory) {
        super(StatementTypeEnum.BLOCK);
        this.scopeMemory = scopeMemory;
    }

    public ScopeMemory getScopeMemory() {
        return scopeMemory;
    }

    public ExpressionBuilderFactory getExpressionBuilder() {
        return expressionBuilder;
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

    public List<Statement> getStatements() {
        return this.statements;
    }


    public <T extends Statement> T currentStatement() {
        return (T) statements.get(statements.size()-1);
    }

    @Override
    protected Optional<StatementResult> doExecute() {
        if (!this.statements.isEmpty()) {
            Statement statement = this.statements.getFirst();
            while (null != statement) {
                // execute the current statement and get its result
                statement.setSpelService(this.spelService);
                StatementResult result = statement.execute();
                StatementResultType statementResultType = result.getStatementResultType();
                switch (statementResultType) {
                    case NEXT -> {
                        // execute the next statement as known by the prior statement
                        statement = result.getNextStatement().orElseThrow(() -> new IllegalStateException("Next statement is empty"));
                    }
                    case TERMINUS -> {
                        // we are at the end of the block
                        // so line up the next statement of this block to be in the results (if one exists)
                        Optional<Statement> maybeNextStatement = nextSequenceStatement();
                        StatementResult blockStatementResult;
                        if (maybeNextStatement.isPresent()) {
                            blockStatementResult= new BaseStatementResult(StatementResultType.NEXT, maybeNextStatement.get());
                        } else {
                            blockStatementResult = new BaseStatementResult(StatementResultType.TERMINUS, null);
                        }
                        return Optional.of(blockStatementResult);
                    }
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> doEcho() {
        return Optional.empty();
    }
}
