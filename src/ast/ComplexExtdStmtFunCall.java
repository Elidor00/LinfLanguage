package ast;

import utils.Environment;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ComplexExtdStmtFunCall extends ComplexExtdStmtDec {
    private List<ComplexExtdExp> actualParList = new ArrayList<>();
    private List<ComplexExtdType> formalParTypes = new ArrayList<>();


    ComplexExtdStmtFunCall(String id) {
        this.id = id;
    }

    void addPar(ComplexExtdExp exp) {
        actualParList.add(exp);
    }

    @Override
    public ComplexExtdType checkType(Environment env) {

        // checking number of parameters
        if (formalParTypes.size() != actualParList.size()) {
            System.out.println(new TypeError("Function" + id + " requires " + formalParTypes.size() + " parameters, but " + actualParList.size() + " were given."));
            System.exit(-1);
        } else {

            for (int i = 0; i < formalParTypes.size(); i++) {
                ComplexExtdExp exp = actualParList.get(i);
                ComplexExtdType formalType = formalParTypes.get(i);
                ComplexExtdType actualType = exp.checkType(env);

                if (formalType.isReference() && !(exp.isID())) {
                    System.out.println(new TypeError("In function " + id + ", parameter should be passed by reference but \"" + exp + "\" was found."));
                    System.exit(-1);
                }


                // checking types of single parameters
                if (!formalType.getClass().equals(actualType.getClass())) {
                    System.out.println(new TypeError("In function " + id + ", " + formalType + " is required but " + actualType + " was found."));
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
        } else {
            HashSet<String> deletedRefs = new HashSet<>();
            ComplexExtdType envType = env.getStEntry(id).getType();
            if (envType instanceof ComplexExtdFunType) {
                ComplexExtdFunType funType = (ComplexExtdFunType) envType;
                formalParTypes.addAll(funType.getParTypes());

                for (int i = 0; i < formalParTypes.size(); i++) {
                    ComplexExtdExp exp = actualParList.get(i);
                    ComplexExtdType formalType = formalParTypes.get(i);
                    res.addAll(exp.checkSemantics(env));

                    if (formalType.isReference() && exp.isID()) {
                        String id = exp.toString();
                        formalType.setRefTo(id);

                        if (formalType.isDeleted() && !deletedRefs.contains(id)) {
                            deletedRefs.add(id);
                        } else {
                            res.add(new SemanticError("Double deletion! Identifier \"" + id + "\" will be deleted two or more times in function " + this.id + "."));
                        }
                    }
                }

                for (String ref : deletedRefs) {
                    env.getStEntry(ref).getType().setDeleted(true);
                }
            } else {
                res.add(new SemanticError(id + " used as a function, but is binded to " + envType + "."));
            }
        }

        return res;
    }
}

