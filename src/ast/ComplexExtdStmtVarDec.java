package ast;

import utils.Environment;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;

public class ComplexExtdStmtVarDec extends ComplexExtdStmtDec {

    private ComplexExtdExp exp;

    ComplexExtdStmtVarDec(ComplexExtdType type, String id, ComplexExtdExp exp) {
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
        ComplexExtdType rhSideType = exp.checkType(env);
        if (!rhSideType.getClass().equals(type.getClass())) {
            System.out.println(new TypeError("Cannot assign " + rhSideType + " to " + type));
            System.exit(-1);
        }
        return rhSideType;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = super.checkSemantics(env);

        res.addAll(exp.checkSemantics(env));

        return res;
    }

}
