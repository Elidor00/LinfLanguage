grammar LVM;

program: instruction* ;

instruction:
    (
      PUSH REGISTER
	  | POP
	  | ADD	REGISTER REGISTER REGISTER
	  | ADDI REGISTER REGISTER NUMBER
	  | SUB	REGISTER REGISTER REGISTER
	  | SUBI REGISTER REGISTER NUMBER
	  | MULT REGISTER REGISTER REGISTER
	  | DIV REGISTER REGISTER REGISTER
	  | MOVE REGISTER REGISTER
	  | STOREW REGISTER NUMBER '(' REGISTER ')'
	  | LOADW REGISTER NUMBER '(' REGISTER ')'
	  | LOADI REGISTER NUMBER
	  | LABEL ':'
	  | BRANCH LABEL
	  | BRANCHEQ REGISTER REGISTER LABEL
	  | BRANCHLESS REGISTER REGISTER LABEL
	  | BRANCHLESSEQ REGISTER REGISTER LABEL
	  | BRANCHGREATER REGISTER REGISTER LABEL
	  | BRANCHGREATEREQ REGISTER REGISTER LABEL
	  | JAL LABEL
	  | JR REGISTER
	  | PRINT
	  | REGISTER '<-' TOP
	  | HALT
    );


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

REGISTER : '$a0' | '$t1' | '$sp' | '$fp' | '$al' | '$ra' ;

LABEL	 : ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER	 : '0' | ('-')?(('1'..'9')('0'..'9')*) ;

WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);

ERR   	 : . -> channel(HIDDEN);

