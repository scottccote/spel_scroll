package com.coteware.springscroll.script.declarations;

public class DeclarationSpecFactory {
    public DeclarationSpec createDeclarationSpec(String variableName,DataTypeEnum dataTypeEnum) {
        switch (dataTypeEnum) {
            case STRING:
                return new StringDeclarationSpec(variableName);
            case INTEGER:
                return new IntegerDeclarationSpec(variableName);
            case FLOAT:
                return new FloatDeclarationSpec(variableName);
            default:
                throw new UnsupportedOperationException(String.format("cannot create spec for variable [%s] of type [%s]",variableName, dataTypeEnum));
        }

    }
}
