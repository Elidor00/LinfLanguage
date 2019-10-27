package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.FunctionNameDeletionError;
import linf.error.semantic.SemanticError;
import linf.error.type.MismatchedPrototype;
import linf.error.type.TypeError;
import linf.type.FunType;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.LinfLib;
import linf.utils.STentry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FunDec extends FunPrototype {
    private final Block body;
    private STentry envEntry;

    public FunDec(String id, List<Parameter> parameters, Block body) {
        super(id, parameters);
        ((FunType) this.type).setPrototype(false);
        this.body = body;
    }

    @Override
    public LinfType checkType() throws TypeError {
        if (envEntry != null) {
            if (!type.equals(envEntry.getType())) {
                throw new MismatchedPrototype(id, (FunType) envEntry.getType(), (FunType) type);
            } else {
                String label = ((FunType) envEntry.getType()).getFunLabel();
                if (label != null) {
                    ((FunType) type).setFunLabel(label);
                }
            }
        }
        body.checkType();
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        envEntry = env.getStEntry(id);
        List<SemanticError> res = super.checkSemantics(env);
        HashMap<String, STentry> scope = new HashMap<>();
        for (Parameter par : getParList()) {
            scope.put(par.getId(), par.getEntry());
        }
        body.setLocalEnv(scope);
        res.addAll(body.checkSemantics(env));
        HashSet<STentry> delIDs = body.getDelSet();

        // deleted id function inside
        if (delIDs.contains(env.getStEntry(id))) {
            res.add(new FunctionNameDeletionError(id));
        }

        HashSet<STentry> rwIDs = body.getRWSet();
        for (int i = 0; i < getParList().size(); i++) {
            Parameter par = getParList().get(i);
            STentry parEntry = par.getEntry();
            if (envEntry != null) {
                env.setReference(((FunType) type).getParEntries().get(i),
                        parEntry);
            }
            delIDs.remove(parEntry);
            rwIDs.remove(parEntry);
        }
        ((FunType) type).setDeletedIDs(delIDs);
        ((FunType) type).setRwIDs(rwIDs);
        return res;
    }

    @Override
    public String codeGen() {
        String funLabel = ((FunType) type).getFunLabel();
        String endLabel = LinfLib.freshLabel();
        return "b " + endLabel.replace(":", "") +
                funLabel +
                "push $ra\n" +
                body.codeGen() +
                // pop return address
                "top $ra\n" +
                "pop\n" +
                // return control to caller
                "jr $ra\n" +
                endLabel;
    }
}
