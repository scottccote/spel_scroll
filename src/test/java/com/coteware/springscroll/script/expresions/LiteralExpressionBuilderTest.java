package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import com.coteware.springscroll.script.literals.BooleanLiteral;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LiteralExpressionBuilderTest {

    @Test
    void addDeclarationSpec() {
        LiteralExpressionBuilder<Boolean, BooleanLiteral> builder = new LiteralExpressionBuilder<>();
        DeclarationSpec<Boolean> declarationSpec = mock(DeclarationSpec.class);
        Exception exception = assertThrows(ScrollAssemblyException.class, () -> builder.addDeclarationSpec(declarationSpec));
        assertEquals("Cannot add declaration specification to a LiteralExpression", exception.getMessage());
    }

    @Test
    void testAddLiteral() {
        LiteralExpressionBuilder<Boolean, BooleanLiteral> builder = new LiteralExpressionBuilder<>();
        BooleanLiteral literal = mock(BooleanLiteral.class);
        LiteralExpressionBuilder<Boolean, BooleanLiteral> enhancedBuilder = builder.addLiteral(literal);
        assertNotNull(enhancedBuilder);
    }

    @Test
    void build() {
        LiteralExpressionBuilder<Boolean, BooleanLiteral> builder = new LiteralExpressionBuilder<>();

        Exception exception = assertThrows(ScrollAssemblyException.class, () -> builder.build());
        assertEquals("The literal required to build the expression.", exception.getMessage());

        BooleanLiteral literal = mock(BooleanLiteral.class);
        LiteralExpressionBuilder<Boolean, BooleanLiteral> enhancedBuilder = builder.addLiteral(literal);
        LiteralExpression<Boolean, BooleanLiteral> builtExpression = enhancedBuilder.build();
        assertNotNull(builtExpression);
    }

    @Test
    void reset() {
        LiteralExpressionBuilder<Boolean, BooleanLiteral> builder = new LiteralExpressionBuilder<>();
        BooleanLiteral literal = mock(BooleanLiteral.class);
        LiteralExpressionBuilder<Boolean, BooleanLiteral> enhancedBuilder = builder.addLiteral(literal);
        LiteralExpression<Boolean, BooleanLiteral> builtExpression = enhancedBuilder.build();
        enhancedBuilder.reset();
        Exception exception = assertThrows(ScrollAssemblyException.class, () -> builder.build());
        assertEquals("The literal required to build the expression.", exception.getMessage());
    }
}