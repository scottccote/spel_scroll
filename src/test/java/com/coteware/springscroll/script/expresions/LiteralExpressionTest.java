package com.coteware.springscroll.script.expresions;

import com.coteware.antlr.SpelScriptParser;
import com.coteware.springscroll.script.literals.Literal;
import com.coteware.springscroll.script.literals.LiteralFactory;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("ensure literal expressions evaluate as expected")
class LiteralExpressionTest {

    LiteralExpression createLiteralExpression(Class<? extends ParseTree> parseTreeClazz, String text) {
        LiteralFactory literalFactory = new LiteralFactory();
        ParseTree parseTree = mock(parseTreeClazz);
        when(parseTree.getText()).thenReturn(text);

        var literal = literalFactory.createLiteral(parseTree);
        ExpressionBuilderFactory expressionBuilderFactory = new ExpressionBuilderFactory();
        return expressionBuilderFactory.useLiteralExpression().addLiteral(literal).build();
    }

    LiteralExpression createBooleanLiteralExpression(String text) {
        return createLiteralExpression(SpelScriptParser.BooleanContext.class,text);
    }

    LiteralExpression createDoubleLiteralExpression(String text) {
        return createLiteralExpression(SpelScriptParser.NumericContext.class,text);
    }

    LiteralExpression createFloatLiteralExpression(String text) {
        return createLiteralExpression(SpelScriptParser.NumericContext.class,text);
    }

    LiteralExpression createIntegerLiteralExpression(String text) {
        return createLiteralExpression(SpelScriptParser.NumericContext.class,text);
    }

    LiteralExpression createStringLiteralExpression(String text) {
        return createLiteralExpression(SpelScriptParser.LiteralContext.class,text);
    }

    @Test
    void booleanEvaluate() {

        ExpressionResult expressionResult = createBooleanLiteralExpression("true").evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> actual = expressionResult.getLiteral();
        assertTrue(actual.isPresent());
        assertEquals(Boolean.valueOf("true"), actual.get().getValue());
    }

    @Test
    void booleanEcho() {
        String echoString = createBooleanLiteralExpression("true").echo();
        assertNotNull(echoString);
        assertEquals("LiteralExpression BOOLEAN true", echoString);
    }

    @Test
    void doubleEvaluate() {

        Double value = 1.7 * Math.pow(10, 308);
        String valueString = value.toString();
        ExpressionResult expressionResult = createDoubleLiteralExpression(valueString).evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> actual = expressionResult.getLiteral();
        assertTrue(actual.isPresent());
        assertEquals(Double.valueOf(valueString), actual.get().getValue());
    }

    @Test
    void doubleEcho() {
        Double value = 1.7 * Math.pow(10, 308);
        String valueString = value.toString();
        String echoString = createDoubleLiteralExpression(valueString).echo();
        assertNotNull(echoString);
        assertEquals("LiteralExpression DOUBLE " + valueString, echoString);
    }

    @Test
    void floatEvaluate() {
        Float value = 1.7f;
        String valueString = value.toString();
        ExpressionResult expressionResult = createFloatLiteralExpression(valueString).evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> actual = expressionResult.getLiteral();
        assertTrue(actual.isPresent());
        assertEquals(Float.valueOf(valueString), actual.get().getValue());
    }

    @Test
    void floatEcho() {
        Float value = 1.7f;
        String valueString = value.toString();
        String echoString = createFloatLiteralExpression(valueString).echo();
        assertNotNull(echoString);
        assertEquals("LiteralExpression FLOAT " + valueString, echoString);
    }

    @Test
    void integerEvaluate() {

        ExpressionResult expressionResult = createIntegerLiteralExpression("123").evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> actual = expressionResult.getLiteral();
        assertTrue(actual.isPresent());
        assertEquals(Integer.valueOf("123"), actual.get().getValue());
    }

    @Test
    void integerEcho() {
        String echoString = createIntegerLiteralExpression("123").echo();
        assertNotNull(echoString);
        assertEquals("LiteralExpression INTEGER 123", echoString);
    }

    @Test
    void stringEvaluate() {

        ExpressionResult expressionResult = createStringLiteralExpression("big bob's buggy barn").evaluate();
        assertNotNull(expressionResult);
        Optional<Literal> actual = expressionResult.getLiteral();
        assertTrue(actual.isPresent());
        assertEquals("big bob's buggy barn", actual.get().getValue());
    }

    @Test
    void stringEcho() {
        String echoString = createStringLiteralExpression("big bob's buggy barn").echo();
        assertNotNull(echoString);
        assertEquals("LiteralExpression STRING big bob's buggy barn", echoString);
    }
}