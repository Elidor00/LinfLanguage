package linf.statement;

import linf.error.semantic.DoubleDeclarationError;
import linf.error.semantic.SemanticError;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;

public abstract class StmtDec extends LinfStmt {
    String id;
    LinfType type;
    private int offset;

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (env.isLocalName(id)) {
            res.add(new DoubleDeclarationError(id));
        } else {
            env.addName(id, type);
        }

        return res;
    }
}
