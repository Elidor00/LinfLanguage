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
    private int offset = -1;

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (env.isLocalName(id)) {
            res.add(new DoubleDeclarationError(id));
        } else {
            if (this instanceof VarDec) {
                offset = env.offset++;
            }
            STentry entry = new STentry(env.nestingLevel, offset, type);
            env.addName(id, entry);
        }

        return res;
    }
}
