grammar LVM;


program: instruction* ;

instruction:
      PUSH REGISTER                                 #push
	  | POP                                         #pop
	  | ADD	REGISTER REGISTER REGISTER              #add
	  | ADDI REGISTER REGISTER NUMBER               #addi
	  | SUB	REGISTER REGISTER REGISTER              #sub
	  | SUBI REGISTER REGISTER NUMBER               #subi
	  | MULT REGISTER REGISTER REGISTER             #mult
	  | DIV REGISTER REGISTER REGISTER              #div
	  | MOVE REGISTER REGISTER                      #move
	  | STOREW REGISTER NUMBER '(' REGISTER ')'     #sw
	  | LOADW REGISTER NUMBER '(' REGISTER ')'      #lw
	  | LOADI REGISTER NUMBER                       #li
	  | LABEL ':'                                   #label
	  | BRANCH LABEL                                #b
	  | BRANCHEQ REGISTER REGISTER LABEL            #beq
	  | BRANCHLESS REGISTER REGISTER LABEL          #blr
	  | BRANCHLESSEQ REGISTER REGISTER LABEL        #blre
	  | BRANCHGREATER REGISTER REGISTER LABEL       #bgr
	  | BRANCHGREATEREQ REGISTER REGISTER LABEL     #bgre
	  | JAL LABEL                                   #jal
	  | JR REGISTER                                 #jr
	  | PRINT                                       #print
	  | REGISTER '<-' TOP                           #top
	  | HALT                                        #halt;


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

