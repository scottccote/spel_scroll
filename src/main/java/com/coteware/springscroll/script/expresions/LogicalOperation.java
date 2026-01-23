package com.coteware.springscroll.script.expresions;

import com.coteware.springscroll.script.declarations.DeclarationSpec;

public interface LogicalOperation extends ExpressionBuilder {
    UnaryLogicalOperation unaryOperation();

    LogicalOperation addNull();
    LogicalOperation addDeclarationSpec(DeclarationSpec declarationSpec);
    LogicalOperation setIsNot(boolean isNot);
}
