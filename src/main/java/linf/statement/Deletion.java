package linf.statement;

import linf.error.semantic.SemanticError;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;
import java.util.List;


public class Deletion extends LinfStmt {
    private final IDValue id;

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
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(id.checkSemantics(env));
        if (env.getStEntry(id) != null) {
            env.deleteName(id.toString());
        }
        return res;
    }

    @Override
    public String codeGen() {
        return "";
    }
}