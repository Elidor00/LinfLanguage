package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;

public class ComplexExtdStmtAssignment extends ComplexExtdStmt {
    private String name;
    private ComplexExtdIDValue id;
    private ComplexExtdExp exp;
    private ComplexExtdType lhSideType;

    ComplexExtdStmtAssignment(String name, ComplexExtdExp exp) {
        this.name = name;
        this.exp = exp;
        this.id = new ComplexExtdIDValue(name);
    }

    String getName() {
        return name;
    }

    public ComplexExtdIDValue getId() {
        return id;
    }

    public ComplexExtdExp getExp() {
        return exp;
    }

    @Override
    public ComplexExtdType checkType() {
        ComplexExtdType rhSideType = exp.checkType();
        if (!rhSideType.getClass().equals(lhSideType.getClass())) {
            System.out.println(new TypeError("Cannot assign " + rhSideType + " to " + lhSideType + "."));
            System.exit(-1);
        }
        return rhSideType;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        STentry entry = env.getStEntry(name);


        if (entry == null) {
            res.add(new SemanticError("Identifier " + name + " not bound in current environment."));
        } else {
            id.setEntry(entry);
            lhSideType = entry.getType();
            if (!env.isLocalName(name)) {
                lhSideType.setRwAccess();
            }
        }

        res.addAll(exp.checkSemantics(env));
        return res;
    }
}
