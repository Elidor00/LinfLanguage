package linf.statement;

import linf.expression.Exp;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;
import linf.utils.SemanticError;
import linf.utils.TypeError;

import java.util.ArrayList;

public class StmtAssignment extends Stmt {
    private String name;
    private IDValue id;
    private Exp exp;
    private LinfType lhSideType;

    public StmtAssignment(String name, Exp exp) {
        this.name = name;
        this.exp = exp;
        this.id = new IDValue(name);
    }

    String getName() {
        return name;
    }

    public IDValue getId() {
        return id;
    }

    public Exp getExp() {
        return exp;
    }

    @Override
    public LinfType checkType() {
        LinfType rhSideType = exp.checkType();
        if (!rhSideType.getClass().equals(lhSideType.getClass())) {
            System.out.println(new TypeError("Cannot assign " + rhSideType + " to " + lhSideType + "."));
            System.exit(-1);
        }
        return rhSideType;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        STentry entry = env.getStEntry(name);


        if (entry == null) {
            res.add(new SemanticError("Identifier " + name + " not bound in current environment."));
        } else {
            id.setEntry(entry);
            lhSideType = entry.getType();
            if (!env.isLocalName(name)) {
                lhSideType.setRwAccess();
            }
        }

        res.addAll(exp.checkSemantics(env));
        return res;
    }
}
