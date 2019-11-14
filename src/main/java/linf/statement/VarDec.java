package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.SemanticError;
import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.HashSet;
import java.util.List;

public class VarDec extends StmtDec implements RWStatement {

    private final Exp exp;

    public VarDec(LinfType type, String id, Exp exp) {
        super(id, type);
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
    public HashSet<STentry> getRWSet() {
        return exp.getRwIDs();
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
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        List<SemanticError> res = super.checkSemantics(env);
        res.addAll(exp.checkSemantics(env));
        return res;
    }

    @Override
    public String codeGen() {
        return exp.codeGen() + "push $a0\n";
    }
}
