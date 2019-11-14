package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.DoubleDeclarationError;
import linf.error.semantic.SemanticError;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;
import java.util.List;

public abstract class StmtDec implements LinfStmt {
    String id;
    LinfType type;

    StmtDec(String id, LinfType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (env.isLocalName(id)) {
            res.add(new DoubleDeclarationError(id));
        } else {
            env.addName(id, type);
        }

        return res;
    }
}
