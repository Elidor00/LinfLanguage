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

public class Assignment extends LinfStmt {
    private IDValue id;
    private Exp exp;
    private LinfType lhSideType;

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
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        STentry entry = env.getStEntry(id);


        if (entry == null) {
            res.add(new UnboundSymbolError(id));
        } else {
            id.setEntry(entry);
            lhSideType = entry.getType();
            if (!env.isLocalName(id)) {
                lhSideType.setRwAccess();
            }
        }

        res.addAll(exp.checkSemantics(env));
        return res;
    }

    @Override
    public String codeGen() {
        return null;
    }
}
