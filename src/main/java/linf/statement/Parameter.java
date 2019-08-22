package linf.statement;

import linf.error.semantic.SemanticError;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;

public class Parameter extends LinfStmt {
    private final IDValue id;
    private int offset;

    public Parameter(LinfType type, String name) {
        this.id = new IDValue(name);
        type.setParameter(true);
        id.setType(type);
    }

    void setOffset(int offset) {
        this.offset = offset;
    }

    STentry getEntry() {
        return id.getEntry();
    }

    public LinfType getType() {
        return id.getType();
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
        id.setEntry(new STentry(env.nestingLevel + 1, offset + 2, id.getType()));
        return new ArrayList<>();
    }

    @Override
    public String codeGen() {
        return "";
    }
}
