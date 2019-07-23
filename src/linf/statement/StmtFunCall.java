package linf.statement;

import linf.expression.Exp;
import linf.expression.IDValue;
import linf.type.FunType;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.SemanticError;
import linf.utils.TypeError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class StmtFunCall extends StmtDec {
    private List<Exp> actualParList = new ArrayList<>();
    private List<LinfType> formalParTypes = new ArrayList<>();
    private FunType type;

    private HashSet<IDValue> rwIDs = new HashSet<>();
    private HashSet<IDValue> deletedIDs = new HashSet<>();


    public StmtFunCall(String id) {
        this.id = id;
    }

    public void addPar(Exp exp) {
        actualParList.add(exp);
    }

    public FunType getType() {
        return type;
    }

    HashSet<IDValue> getRwIDs() {
        return rwIDs;
    }

    HashSet<IDValue> getDeletedIDs() {
        return deletedIDs;
    }

    @Override
    public LinfType checkType() {
        rwIDs.addAll(type.getRwIDs());
        deletedIDs.addAll(type.getDeletedIDs());
        // checking number of parameters
        if (formalParTypes.size() != actualParList.size()) {
            System.out.println(new TypeError("Function" + id + " requires " + formalParTypes.size() + " parameters, but " + actualParList.size() + " were given."));
            System.exit(-1);
        } else {
            for (int i = 0; i < formalParTypes.size(); i++) {
                Exp exp = actualParList.get(i);
                LinfType formalType = formalParTypes.get(i);
                LinfType actualType = exp.checkType();

                // checking types of single parameters
                if (!formalType.getClass().equals(actualType.getClass())) {
                    System.out.println(new TypeError("In function " + id + ", " + formalType + " is required but " + actualType + " was found."));
                    System.exit(-1);
                }

                if (formalType.isReference()) {
                    if (exp.isID()) {
                        // checking deleted parameters
                        IDValue idValue = exp.toComplexExtdIDValue();
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
            LinfType envType = env.getStEntry(id).getType();
            if (envType instanceof FunType) {
                type = (FunType) envType;
                formalParTypes.addAll(type.getParTypes());

                for (int i = 0; i < formalParTypes.size(); i++) {
                    Exp exp = actualParList.get(i);
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

