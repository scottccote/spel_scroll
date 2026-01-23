package com.coteware.springscroll.script.literals;

import com.coteware.antlr.SpelScriptParser;
import com.coteware.springscroll.script.exceptions.ScrollAssemblyException;
import org.antlr.v4.runtime.tree.ParseTree;

public class LiteralFactory {
    public LiteralFactory() {  }

    public Literal<?> createLiteral(ParseTree parseTree) {
        if (null == parseTree) {
            throw new ScrollAssemblyException("Cannot create literal from null parseTree");
        }
        return switch (parseTree) {
            case SpelScriptParser.NumericContext numericContext -> createNumericLiteral(numericContext);
            case SpelScriptParser.Numeric_negativeContext numericNegativeContext ->
                    createNegativeNumericLiteral(numericNegativeContext);
            case SpelScriptParser.BooleanContext booleanContext -> createBooleanLiteral(booleanContext);
            default -> new StringLiteral(parseTree.getText().substring(1, parseTree.getText().length() - 1));
        };
    }

    private Literal<? extends Number> createNegativeNumericLiteral(ParseTree parseTree) {
        NumericLiteral<?> numericLiteral = createNumericLiteral(parseTree);
        numericLiteral.negate();
        return numericLiteral;
    }

    private NumericLiteral<? extends Number> createNumericLiteral(ParseTree parseTree) {
        String text = parseTree.getText();
        //TODO hack hack hack - not proud - "that'l do pig"
        try {
            Integer integer = Integer.valueOf(text);
            return new IntegerLiteral(integer);
        } catch (NumberFormatException e) {
            Float floatValue = Float.valueOf(text);
            if ("infinity".equalsIgnoreCase(floatValue.toString()) ||
            "nan".equalsIgnoreCase(floatValue.toString())) {
                try {
                    Double doubleValue = Double.valueOf(text);
                    return new DoubleLiteral(doubleValue);
                } catch (NumberFormatException e3) {
                    throw new UnsupportedOperationException("Cannot parse '" + text + "' as a numeric literal");
                }
            }
            return new FloatLiteral(floatValue);
        }
    }

    private BooleanLiteral createBooleanLiteral(ParseTree parseTree) {
        String text = parseTree.getText();
        try {
            Boolean bool = Boolean.valueOf(text);;
            return new BooleanLiteral(bool);
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException("Cannot parse '" + text + "' as a boolean literal");
        }
    }
}
