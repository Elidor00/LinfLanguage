package ast;


import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;

public class ComplexExtdParameter implements Node {
    private ComplexExtdIDValue id;

    ComplexExtdParameter(ComplexExtdType type, String name) {
        this.id = new ComplexExtdIDValue(name);
        id.setType(type);
    }

    STentry getEntry() {
        return id.getEntry();
    }

    public ComplexExtdType getType() {
        return id.getType();
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
        id.setEntry(new STentry(env.nestingLevel + 1, id.getType()));
        return new ArrayList<>();
    }
}
