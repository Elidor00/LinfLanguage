package linf;

import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.List;

public interface Node {

    List<SemanticError> checkSemantics(Environment env);

    LinfType checkType() throws TypeError;

    String codeGen();
}

