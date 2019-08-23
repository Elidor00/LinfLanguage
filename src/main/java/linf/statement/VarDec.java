package linf.statement;

import linf.error.semantic.SemanticError;
import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.List;

public class VarDec extends StmtDec {

    private final Exp exp;

    public VarDec(LinfType type, String id, Exp exp) {
        super(id,type);
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
    public List<SemanticError> checkSemantics(Environment env) {
        List<SemanticError> res = super.checkSemantics(env);
        res.addAll(exp.checkSemantics(env));
        return res;
    }

    @Override
    public String codeGen() {
        return exp.codeGen() + "push $a0\n";
    }
}
