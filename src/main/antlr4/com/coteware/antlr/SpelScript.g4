grammar SpelScript;

options {
  caseInsensitive = true;
}

scroll
 : unit_statement+ EOF
 ;

unit_statement
  : UNIT_NAME block_name block
  ;

block_name
  : identifier ';'
  ;

block
  : (DECLARE seq_of_declare_specs)? BEGIN seq_of_statements (EXCEPTION exception_handler+)? END ';'
  ;

//block
//    : (DECLARE declare_spec*)? body
//    ;

seq_of_declare_specs
    : declare_spec+
    ;

declare_spec
    : variable_declaration
    ;

variable_declaration
    : identifier datatype  ';'
    ;

identifier
    :  id_expression
    ;

datatype
  : STRING
  | INTEGER
  | LONG
  | FLOAT
  | DOUBLE
  | BOOLEAN
  ;


label_name
    : id_expression
    ;


id_expression
    : regular_id
    ;

regular_id
  : REGULAR_ID
  ;

type_spec
  : id_expression
  ;

exception_name
    : identifier
    ;


index_name
    : identifier
    ;

seq_of_statements
  : (statement (';' | EOF ) | label_declaration)+
  ;


label_declaration
    : ltp1 = '<' '<' label_name '>' '>'
    ;

body
    : BEGIN seq_of_statements (EXCEPTION exception_handler+)? END label_name?
    ;


exception_handler
    : WHEN exception_name (OR exception_name)* THEN seq_of_statements
    ;

statement
    : block
    | assignment_statement
    | continue_statement
    | exit_statement
    | goto_statement
    | if_statement
    | loop_statement
    | null_statement
    | raise_statement
    | case_statement
    | print_statement
    ;


assignment_statement
    : id_expression ASSIGN_OP expression
    ;

continue_statement
    : CONTINUE label_name? (WHEN condition)?
    ;

exit_statement
    : EXIT label_name? (WHEN condition)?
    ;

goto_statement
    : GOTO label_name
    ;

if_statement
    : IF condition THEN seq_of_statements elsif_part* else_part? END IF
    ;

elsif_part
    : ELSIF condition THEN seq_of_statements
    ;

else_part
    : ELSE seq_of_statements
    ;

loop_statement
    : label_declaration? (WHILE condition | FOR cursor_loop_param)? LOOP seq_of_statements END LOOP label_name?
    ;

// Loop Specific Clause

cursor_loop_param
    : index_name IN REVERSE? lower_bound range_separator = '..' upper_bound
    ;

lower_bound
    : concatenation
    ;

upper_bound
    : concatenation
    ;

null_statement
    : NULL_
    ;

raise_statement
    : RAISE exception_name?
    ;


case_statement /*TODO [boolean isStatementParameter]
TODO scope    {
    boolean isStatement;
}
@init    {$case_statement::isStatement = $isStatementParameter;}*/
    : searched_case_statement
    | simple_case_statement
    ;

simple_case_statement
    : label_declaration? ck1 = CASE expression simple_case_when_part+ case_else_part? END CASE? label_name?
    ;

simple_case_when_part
    : WHEN expression THEN (/*TODO{$case_statement::isStatement}?*/ seq_of_statements | expression)
    ;

searched_case_statement
    : label_declaration? ck1 = CASE searched_case_when_part+ case_else_part? END CASE? label_name?
    ;

searched_case_when_part
    : WHEN condition THEN (/*TODO{$case_statement::isStatement}?*/ seq_of_statements | expression)
    ;

case_else_part
    : ELSE (/*{$case_statement::isStatement}?*/ seq_of_statements | expression)
    ;

print_statement
  : PRINT expressions
  | PRINT
  ;
// Expression & Condition

condition
    : expression
    ;

expressions
    : expression (',' expression)*
    ;

expression
    : logical_expression
    | literal
    | spring_expression
    ;



spring_expression
    : SPEL_START literal (identifier)? SPEL_END
    ;

logical_expression
    : unary_logical_operation
    ;

unary_logical_operation
    : (IS NOT)? logical_operation
    ;

logical_operation
    : (
        NULL_
        | type_spec
    )
    ;


literal
    : CHAR_STRING
    | numeric
    | numeric_negative
    | boolean
    ;

numeric
    : UNSIGNED_INTEGER '.'?
    | APPROXIMATE_NUM_LIT
    ;

numeric_negative
    : MINUS_SIGN numeric
    ;

boolean
    : BOOLEAN
    ;

quoted_string
    : CHAR_STRING
    ;

concatenation
    :  BAR BAR concatenation
    ;

PERCENT         : '%';
AMPERSAND       : '&';
LEFT_PAREN      : '(';
RIGHT_PAREN     : ')';
DOUBLE_ASTERISK : '**';
ASTERISK        : '*';
PLUS_SIGN       : '+';
MINUS_SIGN      : '-';
COMMA           : ',';
SOLIDUS         : '/';
AT_SIGN         : '@';
ASSIGN_OP       : ':=';
HASH_OP         : '#';

NOT_EQUAL_OP              : '!=' | '<>' | '^=' | '~=';
CARRET_OPERATOR_PART      : '^';
TILDE_OPERATOR_PART       : '~';
EXCLAMATION_OPERATOR_PART : '!';
GREATER_THAN_OP           : '>';
LESS_THAN_OP              : '<';
COLON                     : ':';
SEMICOLON                 : ';';

BAR       : '|';
EQUALS_OP : '=';

LEFT_BRACKET  : '[';
RIGHT_BRACKET : ']';

UNIT_NAME                      : 'UNIT_NAME';
DECLARE                        : 'DECLARE';
BEGIN                          : 'BEGIN';
END                            : 'END';
IF                             : 'IF';
THEN                           : 'THEN';
ELSIF                          : 'ELSIF';
ELSE                           : 'ELSE';
CASE                           : 'CASE';
WHEN                           : 'WHEN';
OR                             : 'OR';
AND                            : 'AND';
NOT                            : 'NOT';
IS                             : 'IS';
WHILE                          : 'WHILE';
FOR                            : 'FOR';
LOOP                           : 'LOOP';
IN                             : 'IN';
REVERSE                        : 'REVERSE';
CONTINUE                       : 'CONTINUE';
EXIT                           : 'EXIT';
GOTO                           : 'GOTO';
NULL_                          : 'NULL';
RAISE                          : 'RAISE';
EXCEPTION                      : 'EXCEPTION';
SPEL_START                     : 'SPEL_START';
SPEL_END                       : 'SPEL_END';
PRINT                          : 'PRINT';

STRING                         : 'STRING';
INTEGER                        : 'INTEGER';
LONG                           : 'LONG';
DOUBLE                         : 'DOUBLE';
BOOLEAN                        : 'BOOLEAN';
FALSE                          : 'FALSE';
FLOAT                          : 'FLOAT';

//SPEL_STRING: SIMPLE_LETTER (
//              SIMPLE_LETTER
//             | '$'
//             | '_'
//             | '#'
//             | '@'
//             | '!'
//             | '?'
//             | '&'
//             | '^'
//             | '%'
//             | [0-9]
//             | '*'
//             | '.'
//             | '/'
//             | '('
//             | ')'
//             | '['
//             | ']'
//             | '{'
//             | '}'
//             | '|'
//             | '='
//             | '+'
//             | '-'
//             | '\''
//             | '>'
//             | '<'
//             | '~'
//             | '/'
//             | '"'
//             | ','
//             | ' '
//             )*;

REGULAR_ID: SIMPLE_LETTER (SIMPLE_LETTER | '$' | '_' | '#' | [0-9])*;
UNSIGNED_INTEGER    : [0-9]+;
APPROXIMATE_NUM_LIT : FLOAT_FRAGMENT ('E' ('+' | '-')? (FLOAT_FRAGMENT | [0-9]+))? ('D' | 'F')?;



// Rule #--- <CHAR_STRING> is a base for Rule #065 <char_string_lit> , it incorporates <character_representation>
// and a superfluous subtoken typecasting of the "QUOTE"
//CHAR_STRING: '\'' (~('\'' | '\r' | '\n') | '\'' '\'' | NEWLINE)* '\'';
// modified to use double quotes as SpEL internally uses the sinqle quote for strings
CHAR_STRING: '"' (~('"' | '\r' | '\n') | '"' '"' | NEWLINE)* '"';

SPACES: [ \t\r\n]+ -> channel(HIDDEN);

fragment NEWLINE_EOF    : NEWLINE | EOF;
fragment QUESTION_MARK  : '?';
fragment SIMPLE_LETTER  : [\p{Letter}];
fragment FLOAT_FRAGMENT : UNSIGNED_INTEGER* '.'? UNSIGNED_INTEGER+;
fragment NEWLINE        : '\r'? '\n';
fragment SPACE          : [ \t];