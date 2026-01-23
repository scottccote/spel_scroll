package com.coteware.springscroll.script.declarations;

public class DeclarationSpecFactory {
     public DeclarationSpec<?> createDeclarationSpec(String variableName,DataTypeEnum dataTypeEnum) {
        return switch (dataTypeEnum) {
            case STRING -> new StringDeclarationSpec(variableName);
            case INTEGER -> new IntegerDeclarationSpec(variableName);
            case FLOAT -> new FloatDeclarationSpec(variableName);
            case DOUBLE -> new DoubleDeclarationSpec(variableName);
            case BOOLEAN -> new BooleanDeclarationSpec(variableName);
            default ->
                    throw new UnsupportedOperationException(String.format("cannot create spec for variable [%s] of type [%s]", variableName, dataTypeEnum));
        };

    }
}
