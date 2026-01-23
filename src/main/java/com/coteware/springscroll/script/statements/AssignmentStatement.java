package com.coteware.springscroll.script.statements;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import com.coteware.springscroll.script.expresions.Expression;
import com.coteware.springscroll.script.expresions.ExpressionResult;
import com.coteware.springscroll.script.literals.Literal;
import com.coteware.springscroll.script.variables.Variable;

import java.util.Optional;

public class AssignmentStatement extends AbstractStatement {
    private DeclarationSpec declarationSpec;
    private Expression expression;

    public AssignmentStatement() {
        super(StatementTypeEnum.ASSIGNMENT);
    }

    public void setDeclarationSpec(DeclarationSpec declarationSpec) {
        this.declarationSpec = declarationSpec;
    }

    @Override
    public void add(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Optional<String> doEcho() {
        return Optional.of(declarationSpec.getName() + " = " + this.expression.echo());
    }

    @Override
    public Optional<StatementResult> doExecute() {
        if (null == this.spelService) {
            throw new ScrollAssemblyException("No spel service available");
        }
        this.expression.setSpelService(this.spelService);
        ExpressionResult expressionResult = this.expression.evaluate();
        Variable variable = this.declarationSpec.getVariable();
        Optional<? extends Literal> maybeLiteral = expressionResult.getLiteral();
        if (maybeLiteral.isPresent()) {
            variable.setValue(maybeLiteral.get());
        } else {
            variable.setValue(null);
        }
        return Optional.empty();
    }
}
