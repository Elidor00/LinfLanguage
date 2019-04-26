package ast;

import java.util.ArrayList;

interface Node {
    ComplexExtdType checkType(Environment env);
    ArrayList<SemanticError> checkSemantics(Environment env);
}

