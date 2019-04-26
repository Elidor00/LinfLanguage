package ast;

import java.util.ArrayList;
import java.util.List;


public class ComplexExtdStmtFunCall extends ComplexExtdStmtDec {
    private List<ComplexExtdExp> actualParList = new ArrayList<>();
    private List<STentry> actualParEntries = new ArrayList<>();
    private List<String> formalParIDs = new ArrayList<>();
    private STentry entry;
    private int nestingLevel;
    private ComplexExtdFunType type = new ComplexExtdFunType();

    public ComplexExtdStmtFunCall(String id) {
        this.id = id;
    }

    public void addPar(ComplexExtdExp exp) {
        actualParList.add(exp);
        type.addParType(exp.getType());
    }

    public List<ComplexExtdExp> getActualParList() {
        return actualParList;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        ComplexExtdFunType funType = (ComplexExtdFunType) entry.getType();
        List<ComplexExtdType> staticTypes = funType.getParTypes();
        List<ComplexExtdType> dynamicTypes = type.getParTypes();

        // checking number of parameters
        if (staticTypes.size() != dynamicTypes.size()) {
            System.out.println(new TypeError("Function" + id + " requires " + staticTypes.size() + "parameters, but " + dynamicTypes.size() + "were given."));
            System.exit(-1);
        } else {
            for (int i = 0; i < staticTypes.size(); i++) {
                ComplexExtdType sType = staticTypes.get(i);
                ComplexExtdType dType = dynamicTypes.get(i);
                String varID = actualParList.get(i).getTerm().getFactor().getValue().getIDName();

                if (sType.isRef() && !dType.isRef()) {
                    System.out.println(new TypeError("Parameter should be passed by reference but " + dType + " was found."));
                    System.exit(-1);
                } else {
                    if (!varID.equals("")) {
                        dType = actualParEntries.get(i).getType();
                    }
                }

                // checking types of single parameters
                if (!sType.getClass().equals(dType.getClass())) {
                    System.out.println(new TypeError(sType + " is required but " + dType + " was found."));
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

        nestingLevel = env.nestingLevel;
        entry = env.getStEntry(id);

        for (ComplexExtdExp exp : actualParList) {
            res.addAll(exp.checkSemantics(env));
            String varID = exp.getTerm().getFactor().getValue().getIDName();
            actualParEntries.add(env.getStEntry(varID));
        }

        return res;
    }
}
