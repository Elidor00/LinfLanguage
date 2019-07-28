package linf;

import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;

public interface Node {

    String codeGen();

    LinfType checkType() throws TypeError;

    ArrayList<SemanticError> checkSemantics(Environment env);
}

