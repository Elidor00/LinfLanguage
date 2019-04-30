package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;


public class ComplexExtdStmtDeletion extends ComplexExtdStmt {
    private String id;
    private ComplexExtdType idType;

    ComplexExtdStmtDeletion(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    ComplexExtdType getIdType() {
        return idType;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        STentry entry = env.getStEntry(id);

        if (entry == null) {
            res.add(new SemanticError("Identifier not found. Cannot delete " + id + " before declaring it."));
        } else {
            env.deleteName(id);
            idType = entry.getType();
        }

        return res;
    }
}