package com.coteware.springscroll.script.expresions;

public interface UnaryLogicalOperation extends ExpressionBuilder {
    LogicalOperation setIsNot(boolean isNot);
    LogicalOperation logicalOperation();
}
