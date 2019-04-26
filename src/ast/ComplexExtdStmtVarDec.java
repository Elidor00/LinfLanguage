package ast;

import java.util.ArrayList;

public class ComplexExtdStmtVarDec extends ComplexExtdStmtDec {

    private ComplexExtdExp exp;

    public ComplexExtdStmtVarDec(ComplexExtdType type, String id, ComplexExtdExp exp) {
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    public ComplexExtdType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        ComplexExtdType staticType = this.type;
        ComplexExtdType dynamicType = exp.setValueType();
        if (!dynamicType.getClass().equals(staticType.getClass())) {
            System.out.println(new TypeError("Cannot assign " + dynamicType + " to " + staticType));
            System.exit(-1);
        }
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = super.checkSemantics(env);

        res.addAll(exp.checkSemantics(env));

        return res;
    }

}