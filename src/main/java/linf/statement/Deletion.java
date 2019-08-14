package linf.statement;

import linf.error.semantic.IllegalDeletionError;
import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundDeletionSymbolError;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;


public class Deletion extends LinfStmt {
    private IDValue id;

    public Deletion(String name) {
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
            res.add(new UnboundDeletionSymbolError(id));
        } else {
            id.setEntry(entry);
            if (id.getType().isDeleted()) {
                res.add(new IllegalDeletionError(id));
            } else {
                env.deleteName(id.toString());
            }
        }
        return res;
    }

    @Override
    public String codeGen() {
        return "";
    }
}