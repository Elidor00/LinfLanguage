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
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FunCall extends StmtDec {
    private final List<Exp> actualParList = new ArrayList<>();
    private final List<LinfType> formalParTypes = new ArrayList<>();
    private final HashSet<IDValue> rwIDs = new HashSet<>();
    private final HashSet<IDValue> deletedIDs = new HashSet<>();
    private STentry entry;
    private int nestingLevel;


    public FunCall(String id) {
        this.id = id;
    }

    public void addPar(Exp exp) {
        actualParList.add(exp);
    }

    HashSet<IDValue> getRwIDs() {
        return rwIDs;
    }

    HashSet<IDValue> getDeletedIDs() {
        return deletedIDs;
    }

    @Override
    public LinfType checkType() throws TypeError {
        FunType type = (FunType) entry.getType();
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
            entry = env.getStEntry(id);
            nestingLevel = env.nestingLevel;
            if (entry.getType() instanceof FunType) {
                FunType type = (FunType) entry.getType();
                formalParTypes.addAll(type.getParTypes());

                for (int i = 0; i < formalParTypes.size(); i++) {
                    Exp exp = actualParList.get(i);
                    res.addAll(exp.checkSemantics(env));
                    if (formalParTypes.get(i).isDeleted() && exp.isID()) {
                        env.deleteName(exp.toString());
                    }
                }
            } else {
                res.add(new SymbolUsedAsFunctionError(id, entry.getType()));
            }
        }

        return res;
    }

    /**
     * f() { g(); }
     * g() { print 0; }
     * 1. the control link which points to AR of caller of g
     * 2. actual parameters
     * 3. the ACCESS/STATIC link
     */
    @Override
    public String codeGen() {
        // Control link
        StringBuilder builder = new StringBuilder("push $fp\n");

        // Actual parameters
        for (Exp exp : actualParList) {
            builder.append(exp.codeGen())
                    .append(" push $a0\n");
        }

        // Access link
        builder.append("lw $al 0($fp)\n");
        for (int i = 0; i < nestingLevel - entry.getNestinglevel(); i++) {
            builder.append("lw $al 0($al)");
        }
        builder.append("push $al");

        return builder.append("jal ")
                .append(((FunType) type).getFunLabel().replace(":", ""))
                .append("\n pop \n")
                .toString();

    }
}

