package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;

public abstract class ComplexExtdStmtDec extends ComplexExtdStmt {
    protected String id;
    protected ComplexExtdType type;



    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if (env.containsName(id)) {
            res.add(new SemanticError("Identifier " + id + " already declared."));
        } else {
            STentry entry = new STentry(env.nestingLevel, type);
            env.addName(id, entry);
        }

        return res;
    }
}
