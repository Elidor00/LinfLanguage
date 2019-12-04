package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.SemanticError;
import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import linf.expression.Exp;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Assignment implements RWStatement {
    private final IDValue id;
    private final Exp exp;
    private LinfType lhSideType;
    private final HashSet<STentry> rwSet = new HashSet<>();

    public Assignment(String name, Exp exp) {
        this.exp = exp;
        this.id = new IDValue(name);
    }

    @Override
    public HashSet<STentry> getRWSet() {
        return rwSet;
    }

    @Override
    public LinfType checkType() throws TypeError {
        LinfType rhSideType = exp.checkType();
        //lhSideType may be null
        if (lhSideType != null) {
            if (!rhSideType.getClass().equals(lhSideType.getClass())) {
                throw new IncompatibleTypesError(lhSideType, rhSideType);
            }
        }
        return rhSideType;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>(id.checkSemantics(env));
        res.addAll(exp.checkSemantics(env));
        STentry entry = id.getEntry();

        if (res.size() == 0) {
            lhSideType = entry.getType();
            rwSet.add(entry);
            rwSet.addAll(exp.getRwIDs());

        }
        return res;
    }

    @Override
    public String codeGen() {
        return exp.codeGen() +
                id.LSideCodeGen();
    }

    @Override
    public String toString() {
        return id + " = " + exp + ";";
    }
}
