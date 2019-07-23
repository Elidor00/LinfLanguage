package linf;

import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.SemanticError;

import java.util.ArrayList;

public interface BaseNode {
    LinfType checkType();

    ArrayList<SemanticError> checkSemantics(Environment env);
}

