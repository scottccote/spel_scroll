package com.coteware.springscroll.antlr;

import com.coteware.antlr.SpelScriptBaseListener;
import com.coteware.antlr.SpelScriptParser;
import com.coteware.springscroll.script.Scroll;
import com.coteware.springscroll.script.UnitStatement;
import com.coteware.springscroll.script.expresions.LogicalOperation;
import com.coteware.springscroll.script.statements.Block;
import com.coteware.springscroll.script.ScopeMemory;
import com.coteware.springscroll.script.declarations.DataTypeEnum;
import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.declarations.DeclarationSpecFactory;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import com.coteware.springscroll.script.expresions.Expression;
import com.coteware.springscroll.script.expresions.ExpressionBuilderFactory;
import com.coteware.springscroll.script.literals.Literal;
import com.coteware.springscroll.script.literals.LiteralFactory;
import com.coteware.springscroll.script.statements.AssignmentStatement;
import com.coteware.springscroll.script.statements.PrintStatement;
import com.coteware.springscroll.script.statements.Statement;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;
import java.util.function.Supplier;

public class ScrollListener extends SpelScriptBaseListener {


    private List<String> msgs = new ArrayList<>();
    private Deque<Block> blockStack = new ArrayDeque<>();
    private final Scroll scroll;
    private Block currentBlock;

    private LiteralFactory literalFactory = new LiteralFactory();
    private String unitName;

    public ScrollListener(Scroll scroll) {
        this.scroll = scroll;
    }

    public List<String> getMsgs() {
        return Collections.unmodifiableList(msgs);
    }

    public SequencedCollection<Block> getBlockStack() {
        return Collections.unmodifiableSequencedCollection(this.blockStack);
    }

    public Optional<Block> getCurrentBlock() {
        return Optional.ofNullable(this.currentBlock);
    }

    private <T extends Statement> Optional<T> currentStatement() {
        if (this.blockStack.peek().getStatements().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of((T) this.blockStack.peek().currentStatement());
    }

    private ExpressionBuilderFactory expressionBuilder() {
        return this.blockStack.peek().getExpressionBuilder();
    }

    private ScopeMemory scopeMemory() {
        return this.blockStack.peek().getScopeMemory();
    }

    private void addStatement(Statement statement) {
        this.blockStack.peek().addStatement(statement);
    }

    private void addMsg(String msg) {
        this.msgs.add(msg);
    }

    @Override
    public void enterScroll(SpelScriptParser.ScrollContext ctx) {
        addMsg("enterScroll");
    }

    @Override
    public void exitScroll(SpelScriptParser.ScrollContext ctx) {
        addMsg("exitScroll");
    }

    @Override
    public void enterUnit_statement(SpelScriptParser.Unit_statementContext ctx) {
        addMsg("enterUnit_statement");
        unitName = ctx.block_name().getText();
    }

    @Override
    public void exitUnit_statement(SpelScriptParser.Unit_statementContext ctx) {
        addMsg("exitUnit_statement");
        if (ctx.getParent() instanceof SpelScriptParser.ScrollContext) {

            Optional<Block> maybeBlock = getCurrentBlock();
            if (maybeBlock.isPresent()) {
                UnitStatement unitStatement = new UnitStatement(maybeBlock.get(),unitName);
                scroll.add(unitStatement);
            } else {
                throw new ScrollAssemblyException("Could not find current block");
            }

        }
    }

    @Override
    public void enterBlock(SpelScriptParser.BlockContext ctx) {
        addMsg("enterBlock");
        final Block block;
        if (null == blockStack.peek()) {
            block = new Block(new ScopeMemory());
        } else {
            block = new Block(new ScopeMemory(scopeMemory()));
        }
        blockStack.push(block);

    }

    @Override
    public void exitBlock(SpelScriptParser.BlockContext ctx) {
        addMsg("exitBlock");
        currentBlock = blockStack.pop();
    }

    @Override
    public void enterSeq_of_declare_specs(SpelScriptParser.Seq_of_declare_specsContext ctx) {
        addMsg("enterSeq_of_declare_specs");
    }

    @Override
    public void exitSeq_of_declare_specs(SpelScriptParser.Seq_of_declare_specsContext ctx) {
        addMsg("exitSeq_of_declare_specs");
    }

    @Override
    public void enterDeclare_spec(SpelScriptParser.Declare_specContext ctx) {
        addMsg("enterDeclare_spec");
    }

    @Override
    public void exitDeclare_spec(SpelScriptParser.Declare_specContext ctx) {
        addMsg("exitDeclare_spec");
    }


    @Override
    public void enterVariable_declaration(SpelScriptParser.Variable_declarationContext ctx) {
        addMsg("enterVariable_declaration");
    }

    //TODO move to top and make injectable
    private DeclarationSpecFactory declarationSpecFactory = new DeclarationSpecFactory();

    @Override
    public void exitVariable_declaration(SpelScriptParser.Variable_declarationContext ctx) {
        addMsg("exitVariable_declaration");
        String variableName = ctx.getChild(0).getText();
        String variableType = ctx.getChild(1).getText();
        DataTypeEnum dataType = DataTypeEnum.valueOf(variableType);
        DeclarationSpec declarationSpec = declarationSpecFactory.createDeclarationSpec(variableName,dataType);
        this.scopeMemory().add(declarationSpec);
    }

    @Override
    public void enterStatement(SpelScriptParser.StatementContext ctx) {
        addMsg("enterStatement");
    }

    @Override
    public void exitStatement(SpelScriptParser.StatementContext ctx) {
        addMsg("exitStatement");
    }

    @Override
    public void enterAssignment_statement(SpelScriptParser.Assignment_statementContext ctx) {
        addMsg("enterAssignment_statement");
        Optional<Statement> maybeStatement = currentStatement();
        AssignmentStatement assignmentStatement = new AssignmentStatement();
        if (maybeStatement.isPresent()) {
            maybeStatement.get().setNextSequenceStatement(assignmentStatement);
        }
        addStatement(assignmentStatement);
    }

    @Override
    public void exitAssignment_statement(SpelScriptParser.Assignment_statementContext ctx) {
        addMsg("exitAssignment_statement");
        String variableName = ctx.getChild(0).getText();
        Optional<DeclarationSpec> maybeDeclarationSpec = scopeMemory().get(variableName);
        Optional<AssignmentStatement> maybeAssignmentStatement = currentStatement();

        if (!maybeAssignmentStatement.isPresent()) {
            throw new ScrollAssemblyException("expected assignment statement");
        }

        if (maybeDeclarationSpec.isPresent()) {
            maybeAssignmentStatement.get().setDeclarationSpec(maybeDeclarationSpec.get());
        } else {
            throw new ScrollAssemblyException("no declaration found for variable " + variableName);
        }
    }

    @Override
    public void enterPrint_statement(SpelScriptParser.Print_statementContext ctx) {
        PrintStatement printStatement = new PrintStatement();
        Optional<Statement> maybeStatement = currentStatement();
        if (maybeStatement.isPresent()) {
            maybeStatement.get().setNextSequenceStatement(printStatement);
        }
        addStatement(printStatement);
    }

    @Override
    public void exitPrint_statement(SpelScriptParser.Print_statementContext ctx) {
        addMsg("exitPrint_statement");
    }

    @Override
    public void enterSeq_of_statements(SpelScriptParser.Seq_of_statementsContext ctx) {
        addMsg("enterSeq_of_statements");
    }

    @Override
    public void exitSeq_of_statements(SpelScriptParser.Seq_of_statementsContext ctx) {
        addMsg("exitSeq_of_statements");
    }

    @Override
    public void enterLiteral(SpelScriptParser.LiteralContext ctx) {
        addMsg("enterLiteral");
    }

    @Override
    public void exitLiteral(SpelScriptParser.LiteralContext ctx) {
        addMsg("exitLiteral");
        ParseTree child = ctx.getChild(0);
        Literal literal = literalFactory.createLiteral(child);
        expressionBuilder().addLiteral(literal);

    }

    @Override
    public void enterNumeric(SpelScriptParser.NumericContext ctx) {
        addMsg("enterNumeric");
    }

    @Override
    public void exitNumeric(SpelScriptParser.NumericContext ctx) {
        addMsg("exitNumeric");
    }

    @Override
    public void enterSpring_expression(SpelScriptParser.Spring_expressionContext ctx) {
        addMsg("enterSpring_expression");
        expressionBuilder().useSpringExpression();
    }

    @Override
    public void exitSpring_expression(SpelScriptParser.Spring_expressionContext ctx) {
        addMsg("exitSpring_expression");

        ScopeMemory scopeMemory = scopeMemory();
        int childCount = ctx.getChildCount();
        ExpressionBuilderFactory expressionBuilder = expressionBuilder();
        if (3 < childCount) {
            for (int i = 2; i < childCount - 1; i++) {
                String variableName = ctx.getChild(i).getText();
                Optional<DeclarationSpec> maybeDeclaration = scopeMemory.get(variableName);
                if (maybeDeclaration.isPresent()) {
                    expressionBuilder.addDeclarationSpec(maybeDeclaration.get());
                } else {
                    throw new ScrollAssemblyException("no declaration found for variable " + variableName);
                }
            }
        }

        Supplier<ScrollAssemblyException> assemblyExceptionSupplier = new Supplier<ScrollAssemblyException>() {
            @Override
            public ScrollAssemblyException get() {
                return new ScrollAssemblyException("");
            }
        };

        currentStatement().orElseThrow(assemblyExceptionSupplier).add(expressionBuilder.build());
    }

    @Override
    public void exitExpression(SpelScriptParser.ExpressionContext ctx) {
        addMsg("exitExpression");
        if (isLiteralExpression(ctx)) {
            Expression literalExpression = expressionBuilder().build();
            Supplier<ScrollAssemblyException> assemblyExceptionSupplier = new Supplier<ScrollAssemblyException>() {
                @Override
                public ScrollAssemblyException get() {
                    return new ScrollAssemblyException("");
                }
            };
            currentStatement().orElseThrow(assemblyExceptionSupplier).add(literalExpression);
        }
        expressionBuilder().reset();
    }

    @Override
    public void enterExpression(SpelScriptParser.ExpressionContext ctx) {
        addMsg("enterExpression");
        if (isLiteralExpression(ctx)) {
            expressionBuilder().useLiteralExpression();
        }
    }

    private boolean isLiteralExpression(SpelScriptParser.ExpressionContext ctx) {
        SpelScriptParser.LiteralContext literal = ctx.literal();
        return null != literal;
    }

    @Override
    public void enterLogical_expression(SpelScriptParser.Logical_expressionContext ctx) {
        addMsg("enterLogical_expression");
        expressionBuilder().useLogicalExpression();
    }

    @Override
    public void exitUnary_logical_operation(SpelScriptParser.Unary_logical_operationContext ctx) {
        addMsg("exitUnary_logical_operation");
        if (isNot(ctx)) {
            expressionBuilder().useLogicalExpression().unaryOperation().setIsNot(true);
        } else {
            expressionBuilder().useLogicalExpression().unaryOperation().setIsNot(false);
        }
    }

    private boolean isNot(SpelScriptParser.Unary_logical_operationContext ctx) {
        return false;
    }

    @Override
    public void exitLogical_operation(SpelScriptParser.Logical_operationContext ctx) {
        addMsg("exitLogical_operation");
        LogicalOperation logicalOperation = expressionBuilder().useLogicalExpression().logicalOperation();
        if (isNull(ctx)) {
            logicalOperation.addNull();
        } else {
            ScopeMemory scopeMemory = scopeMemory();
            String variableName = ctx.getChild(0).getText();
            Optional<DeclarationSpec> maybeDeclaration = scopeMemory.get(variableName);
            if (maybeDeclaration.isPresent()) {
                expressionBuilder().useLogicalExpression().logicalOperation().addDeclarationSpec(maybeDeclaration.get());
            } else {
                throw new ScrollAssemblyException("no declaration found for variable " + variableName);
            }

        }
    }

    private boolean isNull(SpelScriptParser.Logical_operationContext ctx) {
        return false;
    }

    @Override
    public void exitLogical_expression(SpelScriptParser.Logical_expressionContext ctx) {
        addMsg("exitLogical_expression");
        ExpressionBuilderFactory expressionBuilder = this.blockStack.peek().getExpressionBuilder();
        this.blockStack.peek().currentStatement().add(expressionBuilder.build());
    }

    @Override
    public void enterRegular_id(SpelScriptParser.Regular_idContext ctx) {
        addMsg("enterRegular_id");
    }

    @Override
    public void exitRegular_id(SpelScriptParser.Regular_idContext ctx) {
        addMsg("exitRegular_id");
    }

    @Override
    public void exitIdentifier(SpelScriptParser.IdentifierContext ctx) {
        addMsg("exitIdentifier");
    }
}
