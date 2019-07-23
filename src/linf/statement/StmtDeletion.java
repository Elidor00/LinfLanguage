package linf.statement;

import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;
import linf.utils.SemanticError;
import linf.utils.TypeError;

import java.util.ArrayList;


public class StmtDeletion extends Stmt {
    private IDValue id;

    public StmtDeletion(String name) {
        this.id = new IDValue(name);
    }

    public IDValue getId() {
        return id;
    }

    @Override
    public LinfType checkType() {
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