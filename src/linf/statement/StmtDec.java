package linf.statement;

import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;
import linf.utils.SemanticError;

import java.util.ArrayList;

public abstract class StmtDec extends Stmt {
    protected String id;
    protected LinfType type;

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (env.isLocalName(id)) {
            res.add(new SemanticError("Identifier " + id + " already declared."));
        } else {
            STentry entry = new STentry(env.nestingLevel, type);
            env.addName(id, entry);
        }

        return res;
    }
}
