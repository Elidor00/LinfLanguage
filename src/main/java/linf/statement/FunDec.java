package linf.statement;

import linf.Parameter;
import linf.error.semantic.FunctionNameShadowingError;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.expression.IDValue;
import linf.type.FunType;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;
import lvm.utils.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FunDec extends StmtDec {
    private final List<Parameter> parList = new ArrayList<>();
    private final Block body;

    public FunDec(String id, Block body) {
        this.id = id;
        this.body = body;
        this.type = new FunType();
        ((FunType) this.type).setFunLabel(Strings.freshFunLabel());
    }

    public void addPar(Parameter par) {
        parList.add(par);
        par.setOffset(parList.size());
        ((FunType) type).addParType(par.getType());
    }

    @Override
    public LinfType checkType() throws TypeError {
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
                res.add(new FunctionNameShadowingError(id));
            }
            res.addAll(par.checkSemantics(env));
            scope.put(parID, par.getEntry());
        }
        body.setLocalEnv(scope);
        res.addAll(body.checkSemantics(env));
        return res;
    }

    @Override
    public String codeGen() {
        String funLabel = ((FunType) type).getFunLabel();
        String endLabel = Strings.freshLabel();
        String code = "jal " + endLabel.replace(":", "") +
                funLabel + "push $ra\n";
        code += body.codeGen();
        code += "top $ra\n" +
                "addi $sp $sp " + parList.size() + "\n" +
                "top $fp\npop\njr $ra\n" +
                endLabel;
        return code;
    }
}
