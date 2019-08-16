package linf.statement;

import linf.error.semantic.SemanticError;
import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;

public class VarDec extends StmtDec {

    private final Exp exp;

    public VarDec(LinfType type, String id, Exp exp) {
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
    public LinfType checkType() throws TypeError {
        LinfType rhSideType = exp.checkType();
        if (!rhSideType.getClass().equals(type.getClass())) {
            throw new IncompatibleTypesError(type, rhSideType);
        }
        return rhSideType;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = super.checkSemantics(env);

        res.addAll(exp.checkSemantics(env));

        return res;
    }

    @Override
    public String codeGen() {
        return null;
    }
}
