package linf.statement;

import linf.error.semantic.IllegalDeletionError;
import linf.error.semantic.SemanticError;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.List;


public class Deletion extends LinfStmt {
    private final IDValue id;
    private STentry entry;

    public Deletion(String name) {
        this.id = new IDValue(name);
    }

    public IDValue getId() {
        return id;
    }

    STentry getEntry() {
        return entry;
    }

    @Override
    public LinfType checkType() {
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        entry = env.getLastEntry(id.toString(), env.nestingLevel);
        ArrayList<SemanticError> res = new ArrayList<>();
        List<SemanticError> idErrs = id.checkSemantics(env);
        if (entry != null) {
            if (env.isDeleted(entry)) {
                res.add(new IllegalDeletionError(id.toString()));
            } else if (idErrs.size() == 0) {
                env.deleteName(id.toString());
            }
        }
        res.addAll(idErrs);
        return res;
    }

    @Override
    public String codeGen() {
        return "";
    }
}