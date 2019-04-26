package ast;

import java.util.ArrayList;


public class ComplexExtdStmtDeletion extends ComplexExtdStmt {
    private boolean ifContext;
    private String id;

    ComplexExtdStmtDeletion(String id, boolean ifContext) {
        this.id = id;
        this.ifContext = ifContext;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return super.checkType(env);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!env.containsName(id)) {
            res.add(new SemanticError("Identifier not found. Cannot delete " + id + " before declaring it."));
        } else {
            if (ifContext) {

            } else {
                env.deleteName(id);
            }
        }

        return res;
    }
}