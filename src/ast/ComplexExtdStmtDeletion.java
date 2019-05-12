package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;


public class ComplexExtdStmtDeletion extends ComplexExtdStmt {
    private ComplexExtdIDValue id;

    ComplexExtdStmtDeletion(String name) {
        this.id = new ComplexExtdIDValue(name);
    }

    public ComplexExtdIDValue getId() {
        return id;
    }

    @Override
    public ComplexExtdType checkType() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();
        STentry entry = env.getStEntry(id);
        if (entry == null) {
            res.add(new SemanticError("Identifier not found. Cannot delete " + id + " before declaring it."));
        } else {
            id.setEntry(entry);
            if (id.getType().isDeleted()) {
                System.out.println(new TypeError("Double Deletion. Identifier \"" + id + "\" was already deleted."));
                System.exit(-1);
            } else {
                env.deleteName(id.toString());
            }
        }
        return res;
    }
}