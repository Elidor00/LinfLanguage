package linf.statement;

import linf.error.semantic.SemanticError;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.List;

public class Parameter implements LinfStmt {
    private final String id;
    private LinfType type;
    private STentry entry;
    private int offset;

    public Parameter(LinfType type, String name) {
        assert type != null;
        assert name != null;
        this.id = name;
        this.type = type;
    }

    void setOffset(int offset) {
        this.offset = offset;
    }

    STentry getEntry() {
        return entry;
    }

    public String getId() {
        return id;
    }

    @Override
    public LinfType checkType() {
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        entry = new STentry(env.getNestingLevel() + 1, offset + 3, type, id);
        return new ArrayList<>();
    }

    @Override
    public String codeGen() {
        return "";
    }
}
