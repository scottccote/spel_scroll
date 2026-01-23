package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogicalExpressionBuilderTest {

    @Test
    void build() {
        LogicalExpressionBuilder builder = new LogicalExpressionBuilder();
        Exception exception = assertThrows(ScrollAssemblyException.class, () -> builder.build());
        assertEquals("You must specify at least one declaration specification",exception.getMessage());

        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("test");
        LogicalOperation enhancedBuilder = builder.addDeclarationSpec(declarationSpec);
        assertNotNull(enhancedBuilder);
        Exception exception2 = assertThrows(ScrollAssemblyException.class, () -> builder.build());
        assertEquals("You must specify a unary operation",exception2.getMessage());

        UnaryLogicalOperation unaryLogicalOperation = enhancedBuilder.unaryOperation();
        assertNotNull(unaryLogicalOperation);

        Expression logicalExpression = unaryLogicalOperation.build();
        assertNotNull(logicalExpression);
    }

    @Test
    void reset() {
        LogicalExpressionBuilder builder = new LogicalExpressionBuilder();
        {
            DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
            when(declarationSpec.getName()).thenReturn("test");
            LogicalOperation enhancedBuilder = builder.addDeclarationSpec(declarationSpec);
            UnaryLogicalOperation unaryLogicalOperation = enhancedBuilder.unaryOperation();
            Expression logicalExpression = unaryLogicalOperation.build();
            assertNotNull(logicalExpression);
        }

        builder.reset();
        {
            DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
            when(declarationSpec.getName()).thenReturn("test");
            LogicalOperation enhancedBuilder = builder.addDeclarationSpec(declarationSpec);
            UnaryLogicalOperation unaryLogicalOperation = enhancedBuilder.unaryOperation();
            Expression logicalExpression = unaryLogicalOperation.build();
            assertNotNull(logicalExpression);
        }
    }

    @Test
    void addNull() {
        LogicalExpressionBuilder builder = new LogicalExpressionBuilder();
        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("test");
        LogicalOperation enhancedBuilder = builder.addDeclarationSpec(declarationSpec);
        UnaryLogicalOperation unaryLogicalOperation = enhancedBuilder.unaryOperation();
        LogicalOperation logicalOperation = unaryLogicalOperation.logicalOperation().addNull();
        Expression logicalExpression = logicalOperation.build();
        assertNotNull(logicalExpression);
        assertTrue(((LogicalExpression) logicalExpression).isNull());
    }

    @Test
    void addDeclarationSpec() {
        LogicalExpressionBuilder builder = new LogicalExpressionBuilder();
        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("test");
        LogicalOperation enhancedBuilder = builder.addDeclarationSpec(declarationSpec);
        assertNotNull(enhancedBuilder);
        Expression logicalExpression = enhancedBuilder.unaryOperation().build();
        assertNotNull(logicalExpression);
    }

    @Test
    void setIsNot() {
        LogicalExpressionBuilder builder = new LogicalExpressionBuilder();
        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("test");
        LogicalOperation enhancedBuilder = builder.addDeclarationSpec(declarationSpec);
        UnaryLogicalOperation unaryLogicalOperation = enhancedBuilder.unaryOperation();
        LogicalOperation logicalOperation = unaryLogicalOperation.logicalOperation().setIsNot(true);
        Expression logicalExpression = logicalOperation.build();
        assertNotNull(logicalExpression);
        assertTrue(((LogicalExpression) logicalExpression).isNot());
    }

    @Test
    void logicalOperation() {
        LogicalExpressionBuilder builder = new LogicalExpressionBuilder();
        DeclarationSpec declarationSpec = mock(DeclarationSpec.class);
        when(declarationSpec.getName()).thenReturn("test");
        LogicalOperation enhancedBuilder = builder.addDeclarationSpec(declarationSpec);
        assertNotNull(enhancedBuilder);
        Expression logicalExpression = enhancedBuilder.unaryOperation().logicalOperation().build();
        assertNotNull(logicalExpression);
    }
}