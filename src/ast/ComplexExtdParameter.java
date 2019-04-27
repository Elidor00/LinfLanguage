package ast;


import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;

public class ComplexExtdParameter implements Node {
    private ComplexExtdType type;
    private String id;


    public ComplexExtdParameter(ComplexExtdType type, String id) {
        this.type = type;
        this.id = id;
    }

    public ComplexExtdType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if(!env.containsName(id)){
            STentry entry = new STentry(env.nestingLevel, type);
            env.addName(id, entry);
        } else {
            res.add(new SemanticError("Identifier " + id + " already defined."));
        }

        return res;
    }
}
