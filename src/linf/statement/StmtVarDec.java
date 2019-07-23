package linf.statement;

import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.SemanticError;
import linf.utils.TypeError;

import java.util.ArrayList;

public class StmtVarDec extends StmtDec {

    private Exp exp;

    public StmtVarDec(LinfType type, String id, Exp exp) {
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    public LinfType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Exp getExp() {
        return exp;
    }

    @Override
    public LinfType checkType() {
        LinfType rhSideType = exp.checkType();
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
