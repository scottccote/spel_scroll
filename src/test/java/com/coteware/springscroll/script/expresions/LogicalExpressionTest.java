package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DataTypeEnum;
import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import com.coteware.springscroll.script.literals.Literal;
import com.coteware.springscroll.script.variables.Variable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogicalExpressionTest {

    @Test
    void evaluateString() {
        LogicalExpression logicalExpression = new LogicalExpression();
        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("testVar");

        Literal literal = mock(Literal.class);
        when(literal.getValue()).thenReturn("testValue");
        when(literal.getDataType()).thenReturn(DataTypeEnum.STRING);
        Variable variable   = mock(Variable.class);
        when(variable.getName()).thenReturn("testVar");
        when(variable.getValue()).thenReturn(literal);
        when(variable.getDataTypeEnum()).thenReturn(DataTypeEnum.STRING);
        when(declarationSpec.getVariable()).thenReturn(variable);
        logicalExpression.add(declarationSpec);

        ExpressionResult expressionResult = logicalExpression.evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> maybeResultLiteral = expressionResult.getLiteral();
        assertTrue(maybeResultLiteral.isPresent());
        Literal resultLiteral = maybeResultLiteral.get();
        assertEquals("testValue", resultLiteral.getValue());
    }


    @Test
    void evaluateInteger() {
        LogicalExpression logicalExpression = new LogicalExpression();
        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("testVar");

        Literal literal = mock(Literal.class);
        when(literal.getValue()).thenReturn(123);
        when(literal.getDataType()).thenReturn(DataTypeEnum.INTEGER);
        Variable variable   = mock(Variable.class);
        when(variable.getName()).thenReturn("testVar");
        when(variable.getValue()).thenReturn(literal);
        when(variable.getDataTypeEnum()).thenReturn(DataTypeEnum.INTEGER);
        when(declarationSpec.getVariable()).thenReturn(variable);
        logicalExpression.add(declarationSpec);

        ExpressionResult expressionResult = logicalExpression.evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> maybeResultLiteral = expressionResult.getLiteral();
        assertTrue(maybeResultLiteral.isPresent());
        Literal resultLiteral = maybeResultLiteral.get();
        assertEquals(123, resultLiteral.getValue());
    }

    @Test
    void evaluateDouble() {
        LogicalExpression logicalExpression = new LogicalExpression();
        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("testVar");

        Literal literal = mock(Literal.class);
        when(literal.getValue()).thenReturn(123.456);
        when(literal.getDataType()).thenReturn(DataTypeEnum.DOUBLE);
        Variable variable   = mock(Variable.class);
        when(variable.getName()).thenReturn("testVar");
        when(variable.getValue()).thenReturn(literal);
        when(variable.getDataTypeEnum()).thenReturn(DataTypeEnum.DOUBLE);
        when(declarationSpec.getVariable()).thenReturn(variable);
        logicalExpression.add(declarationSpec);

        ExpressionResult expressionResult = logicalExpression.evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> maybeResultLiteral = expressionResult.getLiteral();
        assertTrue(maybeResultLiteral.isPresent());
        Literal resultLiteral = maybeResultLiteral.get();
        assertEquals(123.456, resultLiteral.getValue());
    }

    @Test
    @DisplayName("check to see if the double value is null")
    void evaluateDoubleIsNull() {
        LogicalExpression logicalExpression = new LogicalExpression();
        logicalExpression.setNull();


        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("testVar");

        Literal literal = mock(Literal.class);
        when(literal.getValue()).thenReturn(null);
        when(literal.getDataType()).thenReturn(DataTypeEnum.DOUBLE);
        Variable variable   = mock(Variable.class);
        when(variable.getName()).thenReturn("testVar");
        when(variable.getValue()).thenReturn(literal);
        when(variable.getDataTypeEnum()).thenReturn(DataTypeEnum.DOUBLE);
        when(declarationSpec.getVariable()).thenReturn(variable);
        logicalExpression.add(declarationSpec);

        ExpressionResult expressionResult = logicalExpression.evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> maybeResultLiteral = expressionResult.getLiteral();
        assertTrue(maybeResultLiteral.isPresent());
        Literal resultLiteral = maybeResultLiteral.get();
        assertEquals(true, resultLiteral.getValue());
    }

    @Test
    @DisplayName("check to see if the double value is not null")
    void evaluateDoubleIsNotNull() {
        LogicalExpression logicalExpression = new LogicalExpression();
        logicalExpression.setNull();
        logicalExpression.setNot();

        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("testVar");

        Literal literal = mock(Literal.class);
        when(literal.getValue()).thenReturn(123.456);
        when(literal.getDataType()).thenReturn(DataTypeEnum.DOUBLE);
        Variable variable   = mock(Variable.class);
        when(variable.getName()).thenReturn("testVar");
        when(variable.getValue()).thenReturn(literal);
        when(variable.getDataTypeEnum()).thenReturn(DataTypeEnum.DOUBLE);
        when(declarationSpec.getVariable()).thenReturn(variable);
        logicalExpression.add(declarationSpec);

        ExpressionResult expressionResult = logicalExpression.evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> maybeResultLiteral = expressionResult.getLiteral();
        assertTrue(maybeResultLiteral.isPresent());
        Literal resultLiteral = maybeResultLiteral.get();
        assertEquals(true, resultLiteral.getValue());
    }

    @Test
    @DisplayName("check to see if the boolean value")
    void evaluateBoolean() {
        LogicalExpression logicalExpression = new LogicalExpression();


        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("testVar");

        Literal literal = mock(Literal.class);
        when(literal.getValue()).thenReturn(true);
        when(literal.getDataType()).thenReturn(DataTypeEnum.BOOLEAN);
        Variable variable   = mock(Variable.class);
        when(variable.getName()).thenReturn("testVar");
        when(variable.getValue()).thenReturn(literal);
        when(variable.getDataTypeEnum()).thenReturn(DataTypeEnum.BOOLEAN);
        when(declarationSpec.getVariable()).thenReturn(variable);
        logicalExpression.add(declarationSpec);

        ExpressionResult expressionResult = logicalExpression.evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> maybeResultLiteral = expressionResult.getLiteral();
        assertTrue(maybeResultLiteral.isPresent());
        Literal resultLiteral = maybeResultLiteral.get();
        assertEquals(true, resultLiteral.getValue());
    }

    @Test
    @DisplayName("check to see if the not boolean value")
    void evaluateNotBoolean() {
        LogicalExpression logicalExpression = new LogicalExpression();
        logicalExpression.setNot();

        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("testVar");

        Literal literal = mock(Literal.class);
        when(literal.getValue()).thenReturn(true);
        when(literal.getDataType()).thenReturn(DataTypeEnum.BOOLEAN);
        Variable variable   = mock(Variable.class);
        when(variable.getName()).thenReturn("testVar");
        when(variable.getValue()).thenReturn(literal);
        when(variable.getDataTypeEnum()).thenReturn(DataTypeEnum.BOOLEAN);
        when(declarationSpec.getVariable()).thenReturn(variable);
        logicalExpression.add(declarationSpec);

        ExpressionResult expressionResult = logicalExpression.evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> maybeResultLiteral = expressionResult.getLiteral();
        assertTrue(maybeResultLiteral.isPresent());
        Literal resultLiteral = maybeResultLiteral.get();
        assertEquals(false, resultLiteral.getValue());
    }

    @DisplayName("check to see if the not null boolean value")
    @Test
    void evaluateNotNullBoolean() {
        LogicalExpression logicalExpression = new LogicalExpression();
        logicalExpression.setNot();
        logicalExpression.setNull();

        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("testVar");

        Literal literal = mock(Literal.class);
        when(literal.getValue()).thenReturn(true);
        when(literal.getDataType()).thenReturn(DataTypeEnum.BOOLEAN);
        Variable variable   = mock(Variable.class);
        when(variable.getName()).thenReturn("testVar");
        when(variable.getValue()).thenReturn(literal);
        when(variable.getDataTypeEnum()).thenReturn(DataTypeEnum.BOOLEAN);
        when(declarationSpec.getVariable()).thenReturn(variable);
        logicalExpression.add(declarationSpec);

        ExpressionResult expressionResult = logicalExpression.evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> maybeResultLiteral = expressionResult.getLiteral();
        assertTrue(maybeResultLiteral.isPresent());
        Literal resultLiteral = maybeResultLiteral.get();
        assertEquals(false, resultLiteral.getValue());
    }

    @Test
    void echo() {
    }
}