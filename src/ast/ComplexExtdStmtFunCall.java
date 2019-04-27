package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;
import java.util.List;


public class ComplexExtdStmtFunCall extends ComplexExtdStmtDec {
    private List<ComplexExtdExp> actualParList = new ArrayList<>();
    private STentry entry;


    public ComplexExtdStmtFunCall(String id) {
        this.id = id;
    }

    public void addPar(ComplexExtdExp exp) {
        actualParList.add(exp);
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        ComplexExtdFunType funType = (ComplexExtdFunType) entry.getType();
        List<ComplexExtdType> staticTypes = funType.getParTypes();

        // checking number of parameters
        if (staticTypes.size() != actualParList.size()) {
            System.out.println(new TypeError("Function" + id + " requires " + staticTypes.size() + " parameters, but " + actualParList.size() + " were given."));
            System.exit(-1);
        } else {
            for (int i = 0; i < staticTypes.size(); i++) {
                ComplexExtdExp exp = actualParList.get(i);
                ComplexExtdType sType = staticTypes.get(i);
                ComplexExtdType dType = exp.checkType(env);

                if (sType.isRef() && !(exp.isID())) {
                    System.out.println(new TypeError("In function " + id + ", parameter should be passed by reference but \"" + exp + "\" was found."));
                    System.exit(-1);
                }

                // checking types of single parameters
                if (!sType.getClass().equals(dType.getClass())) {
                    System.out.println(new TypeError("In function " + id + ", " + sType + " is required but " + dType + " was found."));
                    System.exit(-1);
                }
            }
        }

        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!env.containsName(id)) {
            res.add(new SemanticError("Function " + id + " used before declaration."));
        }

        entry = env.getStEntry(id);

        for (ComplexExtdExp exp : actualParList) {
            res.addAll(exp.checkSemantics(env));
        }

        return res;
    }
}

