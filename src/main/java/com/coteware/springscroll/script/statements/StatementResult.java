package com.coteware.springscroll.script.statements;

import java.util.Optional;

public interface StatementResult {

    StatementResultType getStatementResultType();

    Optional<Statement> getNextStatement();
}
