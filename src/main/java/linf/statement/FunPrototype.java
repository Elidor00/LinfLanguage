package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.FunctionNameShadowingError;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.type.FunType;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;
import java.util.List;

public class FunPrototype extends StmtDec {
    private final List<Parameter> parList = new ArrayList<>();

    public FunPrototype(String id, List<Parameter> parameters) {
        super(id, new FunType());
        parameters.forEach((par) -> {
            parList.add(par);
            par.setOffset(parList.size());
        });
    }

    List<Parameter> getParList() {
        return parList;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        List<SemanticError> errors = super.checkSemantics(env);
        for (Parameter par : parList) {
            String parID = par.getId();
            if (parID.equals(id)) {
                errors.add(new FunctionNameShadowingError(id));
            }
            errors.addAll(par.checkSemantics(env));
            ((FunType) type).addPar(par.getEntry());
        }
        return errors;
    }

    @Override
    public LinfType checkType() throws TypeError {
        return null;
    }

    @Override
    public String codeGen() {
        return "";
    }
}
