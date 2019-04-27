package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public interface Node {
    ComplexExtdType checkType(Environment env);
    ArrayList<SemanticError> checkSemantics(Environment env);
}

