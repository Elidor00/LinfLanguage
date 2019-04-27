package ast;

import utils.Environment;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;

public class ComplexExtdStmtAssignment extends ComplexExtdStmt {
    private String id;
    private ComplexExtdExp exp;

    public ComplexExtdStmtAssignment(String id, ComplexExtdExp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        ComplexExtdType staticType = env.getStEntry(id).getType();
        ComplexExtdType dynamicType = exp.checkType(env);
        if (!dynamicType.getClass().equals(staticType.getClass())) {
            System.out.println(new TypeError("Cannot assign " + dynamicType + " to " + staticType));
            System.exit(-1);
        }
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!env.containsName(id)) {
            res.add(new SemanticError("Identifier " + id + " used before being declared"));
        }

        return res;
    }
}
