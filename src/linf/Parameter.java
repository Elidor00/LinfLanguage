package linf;

import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;
import linf.utils.SemanticError;

import java.util.ArrayList;

public class Parameter implements BaseNode {
    private IDValue id;

    Parameter(LinfType type, String name) {
        this.id = new IDValue(name);
        id.setType(type);
    }

    public STentry getEntry() {
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
        id.setEntry(new STentry(env.nestingLevel + 1, id.getType()));
        return new ArrayList<>();
    }
}
