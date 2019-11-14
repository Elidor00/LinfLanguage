package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.HashSet;
import java.util.List;

public class Print implements RWStatement {
    private final Exp exp;

    public Print(Exp exp) {
        this.exp = exp;
    }

    public Exp getExp() {
        return exp;
    }

    @Override
    public LinfType checkType() throws TypeError {
        exp.checkType();
        return null;
    }

    @Override
    public HashSet<STentry> getRWSet() {
        return exp.getRwIDs();
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        return exp.checkSemantics(env);
    }

    @Override
    public String codeGen() {
        return exp.codeGen() + "print\n";
    }
}
