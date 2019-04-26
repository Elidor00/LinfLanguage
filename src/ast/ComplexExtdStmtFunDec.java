package ast;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ComplexExtdStmtFunDec extends ComplexExtdStmtDec {
    private List<ComplexExtdParameter> parList = new ArrayList<>();
    private ComplexExtdStmtBlock body;
    ComplexExtdFunType type = new ComplexExtdFunType();

    public ComplexExtdStmtFunDec(String id, ComplexExtdStmtBlock body) {
        this.id = id;
        this.body = body;
    }

    public void addPar(@NotNull ComplexExtdParameter par) {
        parList.add(par);
        type.addParType(par.getType());
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (env.containsName(id)) {
            res.add(new SemanticError("Identifier " + id + " already declared."));
        } else {
            STentry entry = new STentry(env.nestingLevel, type);
            env.addName(id, entry);
        }

        for (ComplexExtdParameter par : parList) {
            res.addAll(par.checkSemantics(env));
        }

        res.addAll(body.checkSemantics(env));

        return res;
    }

}
