package com.coteware.springscroll.script.statements;

import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import com.coteware.springscroll.script.expresions.Expression;
import com.coteware.springscroll.script.expresions.ExpressionResult;
import com.coteware.springscroll.script.literals.Literal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PrintStatement extends AbstractStatement {
    private List<Expression> expressions = new ArrayList<>();
    public PrintStatement() {
        super(StatementTypeEnum.PRINT);
    }

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }


    @Override
    public void add(Expression expression) {
        this.expressions.add(expression);
    }

    @Override
    protected Optional<StatementResult> doExecute() {
        if (null == this.spelService) {
            throw new ScrollAssemblyException("No spel service available");
        }
        System.out.print("CONSOLE:>");
        expressions.forEach(
                expression -> {
                    expression.setSpelService(this.spelService);
                    ExpressionResult expressionResult = expression.evaluate();
                    Optional<? extends Literal> maybeLiteral = expressionResult.getLiteral();
                    if (maybeLiteral.isPresent()) {
                        System.out.print(maybeLiteral.get().getValue());
                    } else {
                        System.out.print("null");
                    }
                    System.out.print(" ");
                }
        );
        System.out.println();

        return Optional.empty();
    }

    @Override
    protected Optional<String> doEcho() {
        return Optional.ofNullable(
                expressions.stream().map(Expression::echo)
                        .collect(Collectors.joining(",")));
    }
}
