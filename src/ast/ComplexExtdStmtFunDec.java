package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
        ((ComplexExtdFunType) type).addParType(par.getType());
    }

    public ComplexExtdStmtBlock getBody() {
        return body;
    }


    @Override
    public ComplexExtdType checkType() {
        body.checkType();
        HashSet<ComplexExtdIDValue> delIDs = body.getDeletedIDs();
        HashSet<ComplexExtdIDValue> rwIDs = body.getRwIDs();
        for (ComplexExtdParameter par : parList) {
            ComplexExtdIDValue parID = par.getId();
            delIDs.remove(parID);
            rwIDs.remove(parID);
        }
        ((ComplexExtdFunType) type).setDeletedIDs(delIDs);
        ((ComplexExtdFunType) type).setRwIDs(rwIDs);
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = super.checkSemantics(env);
        HashMap<String, STentry> scope = new HashMap<>();
        for (ComplexExtdParameter par : parList) {
            String parID = par.getId().toString();
            if (parID.equals(id)) {
                res.add(new SemanticError("Error, can't use " + id + " both as function identifier and formal parameter."));
            }
            res.addAll(par.checkSemantics(env));
            scope.put(parID, par.getEntry());
        }
        body.setLocalEnv(scope);
        res.addAll(body.checkSemantics(env));
        return res;
    }

}
