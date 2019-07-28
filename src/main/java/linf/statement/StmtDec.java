package linf.statement;

import linf.error.semantic.DoubleDeclarationError;
import linf.error.semantic.SemanticError;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;

public abstract class StmtDec extends LinfStmt {
    protected String id;
    protected LinfType type;

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (env.isLocalName(id)) {
            res.add(new DoubleDeclarationError(id));
        } else {
            STentry entry = new STentry(env.nestingLevel, type);
            env.addName(id, entry);
        }

        return res;
    }
}
