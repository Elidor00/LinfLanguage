package linf.statement;

import linf.Parameter;
import linf.expression.IDValue;
import linf.type.FunType;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;
import linf.utils.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class StmtFunDec extends StmtDec {
    private List<Parameter> parList = new ArrayList<>();
    private StmtBlock body;

    public StmtFunDec(String id, StmtBlock body) {
        this.id = id;
        this.body = body;
        this.type = new FunType();
    }

    public void addPar(Parameter par) {
        parList.add(par);
        ((FunType) type).addParType(par.getType());
    }

    public StmtBlock getBody() {
        return body;
    }


    @Override
    public LinfType checkType() {
        body.checkType();
        HashSet<IDValue> delIDs = body.getDeletedIDs();
        HashSet<IDValue> rwIDs = body.getRwIDs();
        for (Parameter par : parList) {
            IDValue parID = par.getId();
            delIDs.remove(parID);
            rwIDs.remove(parID);
        }
        ((FunType) type).setDeletedIDs(delIDs);
        ((FunType) type).setRwIDs(rwIDs);
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = super.checkSemantics(env);
        HashMap<String, STentry> scope = new HashMap<>();
        for (Parameter par : parList) {
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
