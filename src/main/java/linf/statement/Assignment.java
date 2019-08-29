package linf.statement;

import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import linf.expression.Exp;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.List;

public class Assignment extends LinfStmt {
    private final IDValue id;
    private final Exp exp;
    private LinfType lhSideType;
    private int nestingLevel;

    public Assignment(String name, Exp exp) {
        this.exp = exp;
        this.id = new IDValue(name);
    }

    public IDValue getId() {
        return id;
    }

    public Exp getExp() {
        return exp;
    }

    @Override
    public LinfType checkType() throws TypeError {
        LinfType rhSideType = exp.checkType();
        if (!rhSideType.getClass().equals(lhSideType.getClass())) {
            throw new IncompatibleTypesError(lhSideType, rhSideType);
        }
        return rhSideType;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();
        STentry entry = env.getStEntry(id);

        if (entry == null) {
            res.add(new UnboundSymbolError(id));
        } else {
            lhSideType = entry.getType();
            nestingLevel = env.nestingLevel;
            if (!env.isLocalName(id)) {
                lhSideType.setRwAccess();
            }
            res.addAll(id.checkSemantics(env));
        }
        res.addAll(exp.checkSemantics(env));
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
