package linf.statement;

import linf.error.semantic.IllegalDeletionError;
import linf.error.semantic.SemanticError;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Deletion implements DeletingStatement {
    private final IDValue id;

    public Deletion(String name) {
        this.id = new IDValue(name);
    }

    public HashSet<STentry> getDelSet() {
        HashSet<STentry> set = new HashSet<>();
        set.add(id.getEntry());
        return set;
    }

    @Override
    public LinfType checkType() {
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(id.checkSemantics(env));
        if (res.size() == 0 && env.isDeleted(id.getEntry())) {
            res.add(new IllegalDeletionError(id.toString()));
        }
        return res;
    }

    @Override
    public String codeGen() {
        return "";
    }
}
