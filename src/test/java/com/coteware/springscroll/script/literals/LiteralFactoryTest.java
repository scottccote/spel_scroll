package com.coteware.springscroll.script.literals;

import com.coteware.antlr.SpelScriptParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LiteralFactoryTest {

    @Test
    void createBooleanLiteral() {
        LiteralFactory literalFactory = new LiteralFactory();
        ParseTree parseTree = mock(SpelScriptParser.BooleanContext.class);
        when(parseTree.getText()).thenReturn("true");
        Literal<?> literal = literalFactory.createLiteral(parseTree);
        assertNotNull(literal);
        assertTrue(literal instanceof BooleanLiteral);
        assertEquals("true", ((BooleanLiteral) literal).getValue().toString());
    }

    @Test
    void createDoubleLiteral() {
        Double value = 1.7 * Math.pow(10, 308);
        String valueString = value.toString();
        LiteralFactory literalFactory = new LiteralFactory();
        ParseTree parseTree = mock(SpelScriptParser.NumericContext.class);
        when(parseTree.getText()).thenReturn(valueString);
        Literal<?> literal = literalFactory.createLiteral(parseTree);
        assertNotNull(literal);
        assertTrue(literal instanceof DoubleLiteral);
        assertEquals(valueString, ((DoubleLiteral) literal).getValue().toString());
    }

    @Test
    void createFloatLiteral() {
        Float value = 1.7f;
        String valueString = value.toString();
        LiteralFactory literalFactory = new LiteralFactory();
        ParseTree parseTree = mock(SpelScriptParser.NumericContext.class);
        when(parseTree.getText()).thenReturn(valueString);
        Literal<?> literal = literalFactory.createLiteral(parseTree);
        assertNotNull(literal);
        assertTrue(literal instanceof FloatLiteral);
        assertEquals(valueString, ((FloatLiteral) literal).getValue().toString());
    }

    @Test
    void createIntegerLiteral() {
        Integer value = 17;
        String valueString = value.toString();
        LiteralFactory literalFactory = new LiteralFactory();
        ParseTree parseTree = mock(SpelScriptParser.NumericContext.class);
        when(parseTree.getText()).thenReturn(valueString);
        Literal<?> literal = literalFactory.createLiteral(parseTree);
        assertNotNull(literal);
        assertTrue(literal instanceof IntegerLiteral);
        assertEquals(valueString, ((IntegerLiteral) literal).getValue().toString());
    }

    @Test
    void createStringLiteral() {
        String valueString = "big bob's buggy barn";
        LiteralFactory literalFactory = new LiteralFactory();
        ParseTree parseTree = mock(SpelScriptParser.LiteralContext.class);
        when(parseTree.getText()).thenReturn(valueString);
        Literal<?> literal = literalFactory.createLiteral(parseTree);
        assertNotNull(literal);
        assertTrue(literal instanceof StringLiteral);
        assertEquals(valueString, ((StringLiteral) literal).getValue().toString());
    }
}