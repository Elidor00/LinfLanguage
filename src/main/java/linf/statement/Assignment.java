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
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        STentry entry = env.getStEntry(id);


        if (entry == null) {
            res.add(new UnboundSymbolError(id));
        } else {
            id.setEntry(entry);
            lhSideType = entry.getType();
            nestingLevel = env.nestingLevel;
            if (!env.isLocalName(id)) {
                lhSideType.setRwAccess();
            }
        }

        res.addAll(exp.checkSemantics(env));
        return res;
    }

    @Override
    public String codeGen() {
        if (id.isParameter()) {
            if (id.getEntry().getType().isReference()) {
                return exp.codeGen() +
                        "lw $t1 " + id.getEntry().getOffset() + "($fp)\n" +
                        "sw $a0 0($t1)\n";
            } else {
                return exp.codeGen() +
                        "sw $a0 " + id.getEntry().getOffset() + "($fp)\n";
            }
        } else {
            StringBuilder followChain = new StringBuilder("lw $al 2($fp)\n");
            for (int i = 0; i < nestingLevel - id.getEntry().getNestinglevel(); i++) {
                followChain.append("lw $al 0($al)\n");
            }
            return exp.codeGen() +
                    followChain +
                    "sw $a0 " + id.getEntry().getOffset() + "($al)\n";
        }

    }
}
