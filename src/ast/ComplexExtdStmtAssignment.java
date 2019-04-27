package ast;

import utils.Environment;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;

public class ComplexExtdStmtAssignment extends ComplexExtdStmt {
    private String id;
    private ComplexExtdExp exp;
    private ComplexExtdType lhSideType;

    public ComplexExtdStmtAssignment(String id, ComplexExtdExp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        ComplexExtdType rhSideType = exp.checkType(env);
        if (!rhSideType.getClass().equals(lhSideType.getClass())) {
            System.out.println(new TypeError("Cannot assign " + rhSideType + " to " + lhSideType));
            System.exit(-1);
        }
        return rhSideType;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!env.containsName(id)) {
            res.add(new SemanticError("Identifier " + id + " used before being declared"));
        } else {
            lhSideType = env.getStEntry(id).getType();
        }

        res.addAll(exp.checkSemantics(env));

        return res;
    }
}
