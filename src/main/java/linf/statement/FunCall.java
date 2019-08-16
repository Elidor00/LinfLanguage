package linf.statement;

import linf.error.semantic.FunctionDeclarationOutOfScopeError;
import linf.error.semantic.SemanticError;
import linf.error.semantic.SymbolUsedAsFunctionError;
import linf.error.type.*;
import linf.expression.Exp;
import linf.expression.IDValue;
import linf.type.FunType;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FunCall extends StmtDec {
    private final List<Exp> actualParList = new ArrayList<>();
    private final List<LinfType> formalParTypes = new ArrayList<>();
    private final HashSet<IDValue> rwIDs = new HashSet<>();
    private final HashSet<IDValue> deletedIDs = new HashSet<>();
    private FunType type;


    public FunCall(String id) {
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
    public LinfType checkType() throws TypeError {
        rwIDs.addAll(type.getRwIDs());
        deletedIDs.addAll(type.getDeletedIDs());
        // checking number of parameters
        if (formalParTypes.size() != actualParList.size()) {
            throw new WrongParameterNumberError(id, formalParTypes.size(), actualParList.size());
        } else {
            for (int i = 0; i < formalParTypes.size(); i++) {
                Exp exp = actualParList.get(i);
                LinfType formalType = formalParTypes.get(i);
                LinfType actualType = exp.checkType();

                // checking types of single parameters
                if (!formalType.getClass().equals(actualType.getClass())) {
                    throw new WrongParameterTypeError(id, formalType, actualType);
                }

                if (formalType.isReference()) {
                    if (exp.isID()) {
                        // checking deleted parameters
                        IDValue idValue = exp.toIDValue();
                        formalType.setRefTo(idValue.toString());
                        if (formalType.isDeleted()) {
                            if (deletedIDs.contains(idValue)) {
                                throw new DoubleDeletionError(idValue);
                            } else {
                                deletedIDs.add(idValue);
                            }
                        }
                        if (formalType.isRwAccess()) {
                            rwIDs.add(idValue);
                        }
                    } else {
                        // checking parameters passed by reference
                        throw new ReferenceParameterError(id, exp);
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
            res.add(new FunctionDeclarationOutOfScopeError(id));
        } else {
            LinfType envType = env.getStEntry(id).getType();
            if (envType instanceof FunType) {
                type = (FunType) envType;
                formalParTypes.addAll(type.getParTypes());

                for (int i = 0; i < formalParTypes.size(); i++) {
                    Exp exp = actualParList.get(i);
                    res.addAll(exp.checkSemantics(env));
                    if (formalParTypes.get(i).isDeleted() && exp.isID()) {
                        env.deleteName(exp.toString());
                    }
                }
            } else {
                res.add(new SymbolUsedAsFunctionError(id, envType));
            }
        }

        return res;
    }

    @Override
    public String codeGen() {
        return null;
    }
}

