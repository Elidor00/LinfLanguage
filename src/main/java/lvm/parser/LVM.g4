grammar LVM;

//Parser Rules

program: instruction* ;

instruction:
    (
        PUSH   r1 = REGISTER
	  | POP
	  | ADD	   r1 = REGISTER r2 = REGISTER r3 = REGISTER
	  | ADDI   r1 = REGISTER r2 = REGISTER  n = NUMBER
	  | SUB	   r1 = REGISTER r2 = REGISTER r3 = REGISTER
	  | SUBI   r1 = REGISTER r2 = REGISTER  n = NUMBER
	  | MULT   r1 = REGISTER r2 = REGISTER r3 = REGISTER
	  | DIV    r1 = REGISTER r2 = REGISTER r3 = REGISTER
	  | MOVE   r1 = REGISTER r2 = REGISTER
	  | STOREW r1 = REGISTER n = NUMBER '(' r2 = REGISTER ')'
	  | LOADW  r1 = REGISTER n = NUMBER '(' r2 = REGISTER ')'
	  | LOADI  r1 = REGISTER n = NUMBER
	  | l = LABEL ':'
	  | BRANCH          l = LABEL
	  | BRANCHEQ        r1 = REGISTER r2 = REGISTER l = LABEL
	  | BRANCHLESS      r1 = REGISTER r2 = REGISTER l = LABEL
	  | BRANCHLESSEQ    r1 = REGISTER r2 = REGISTER l  = LABEL
	  | BRANCHGREATER   r1 = REGISTER r2 = REGISTER l = LABEL
	  | BRANCHGREATEREQ r1 = REGISTER r2 = REGISTER l = LABEL
	  | JAL l = LABEL
	  | JR r1 = REGISTER
	  | PRINT
	  | TOP r1 = REGISTER
	  | HALT
    );

//Lexer Rules

JR	                        : 'jr' ;
JAL	                        : 'jal' ;
TOP                         : 'top' ;
PRINT	                    : 'print' ;
HALT	                    : 'halt' ;
LOADI                       : 'li' ;
MOVE                        : 'move' ;
PUSH  	                    : 'push' ;
POP	                        : 'pop' ;
ADD	                        : 'add' ;
ADDI                        : 'addi';
SUB	                        : 'sub' ;
SUBI                        : 'subi' ;
MULT	                    : 'mult' ;
DIV	                        : 'div' ;
STOREW	                    : 'sw' ;
LOADW	                    : 'lw' ;
BRANCH	                    : 'b' ;
BRANCHEQ                    : 'beq' ;
BRANCHLESS                  : 'blr' ;
BRANCHLESSEQ                : 'blre' ;
BRANCHGREATER               : 'bgr' ;
BRANCHGREATEREQ             : 'bgre' ;

REGISTER : '$a0' | '$t1' | '$sp' | '$fp' | '$al' | '$ra' | '$ip';

LABEL	 : ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER	 : '0' | ('-')?(('1'..'9')('0'..'9')*) ;

WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);

ERR   	 : . -> channel(HIDDEN);

