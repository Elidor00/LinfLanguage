package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class ComplexExtdStmtFunDec extends ComplexExtdStmtDec {
    private List<ComplexExtdParameter> parList = new ArrayList<>();
    private ComplexExtdStmtBlock body;

    ComplexExtdStmtFunDec(String id, ComplexExtdStmtBlock body) {
        this.id = id;
        this.body = body;
        this.type = new ComplexExtdFunType();
    }

    void addPar(ComplexExtdParameter par) {
        parList.add(par);
        ComplexExtdFunType t = (ComplexExtdFunType) type;
        t.addParType(par.getType());
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return body.checkType(env);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = super.checkSemantics(env);

        env.openScope();

        for (ComplexExtdParameter par : parList) {
            res.addAll(par.checkSemantics(env));
        }

        res.addAll(body.checkSemantics(env));

        env.closeScope();

        return res;
    }

}
