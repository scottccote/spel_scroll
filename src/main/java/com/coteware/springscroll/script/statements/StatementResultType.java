package com.coteware.springscroll.script.statements;

public enum StatementResultType {
    /** jump to the statement */ JUMP,
    /** follow to the statement next in sequence */ NEXT,
    /** go to the exception handler block */ EXCEPTION,
    /** this is the last statement of a block */ TERMINUS
}
