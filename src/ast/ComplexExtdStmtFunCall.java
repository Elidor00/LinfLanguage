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
    private ComplexExtdFunType type;

    private HashSet<ComplexExtdIDValue> rwIDs = new HashSet<>();
    private HashSet<ComplexExtdIDValue> deletedIDs = new HashSet<>();


    ComplexExtdStmtFunCall(String id) {
        this.id = id;
    }

    void addPar(ComplexExtdExp exp) {
        actualParList.add(exp);
    }

    public ComplexExtdFunType getType() {
        return type;
    }

    HashSet<ComplexExtdIDValue> getRwIDs() {
        return rwIDs;
    }

    HashSet<ComplexExtdIDValue> getDeletedIDs() {
        return deletedIDs;
    }

    @Override
    public ComplexExtdType checkType() {
        rwIDs.addAll(type.getRwIDs());
        deletedIDs.addAll(type.getDeletedIDs());
        // checking number of parameters
        if (formalParTypes.size() != actualParList.size()) {
            System.out.println(new TypeError("Function" + id + " requires " + formalParTypes.size() + " parameters, but " + actualParList.size() + " were given."));
            System.exit(-1);
        } else {
            for (int i = 0; i < formalParTypes.size(); i++) {
                ComplexExtdExp exp = actualParList.get(i);
                ComplexExtdType formalType = formalParTypes.get(i);
                ComplexExtdType actualType = exp.checkType();

                // checking types of single parameters
                if (!formalType.getClass().equals(actualType.getClass())) {
                    System.out.println(new TypeError("In function " + id + ", " + formalType + " is required but " + actualType + " was found."));
                    System.exit(-1);
                }

                if (formalType.isReference()) {
                    if (exp.isID()) {
                        // checking deleted parameters
                        ComplexExtdIDValue idValue = exp.toComplexExtdIDValue();
                        formalType.setRefTo(idValue.toString());
                        if (formalType.isDeleted()) {
                            if (deletedIDs.contains(idValue)) {
                                System.out.println(new TypeError("Double Deletion. In function " + id + ", parameter \"" + idValue + "\" will be deleted twice."));
                                System.exit(-1);
                            } else {
                                deletedIDs.add(idValue);
                            }
                        }
                        if (formalType.isRwAccess()) {
                            rwIDs.add(idValue);
                        }
                    } else {
                        // checking parameters passed by reference
                        System.out.println(new TypeError("In function " + id + ", parameter should be passed by reference but \"" + exp + "\" was found."));
                        System.exit(-1);
                    }
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
            ComplexExtdType envType = env.getStEntry(id).getType();
            if (envType instanceof ComplexExtdFunType) {
                type = (ComplexExtdFunType) envType;
                formalParTypes.addAll(type.getParTypes());

                for (int i = 0; i < formalParTypes.size(); i++) {
                    ComplexExtdExp exp = actualParList.get(i);
                    res.addAll(exp.checkSemantics(env));
                    if(formalParTypes.get(i).isDeleted() && exp.isID()){
                        env.deleteName(exp.toString());
                    }
                }
            } else {
                res.add(new SemanticError(id + " used as a function, but is bound to " + envType + "."));
            }
        }

        return res;
    }
}

