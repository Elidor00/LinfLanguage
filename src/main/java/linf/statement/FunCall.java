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
import linf.utils.LinfLib;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class FunCall extends LinfStmt {
    private String id;
    private List<Exp> actualParList = new ArrayList<>();
    private List<LinfType> formalParTypes = new ArrayList<>();
    private STentry entry;
    private int nestingLevel;

    private HashSet<IDValue> rwIDs = new HashSet<>();
    private HashSet<IDValue> deletedIDs = new HashSet<>();

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
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!env.containsName(id)) {
            res.add(new FunctionDeclarationOutOfScopeError(id));
        } else {
            entry = env.getStEntry(id);
            nestingLevel = env.nestingLevel;
            if (entry.getType() instanceof FunType) {
                FunType type = (FunType) entry.getType();
                formalParTypes.addAll(type.getParTypes());

                try {
                    for (int i = 0; i < formalParTypes.size(); i++) {
                        Exp exp = actualParList.get(i); // out of bound with actualparlist < formalparlist
                        LinfType formalType = formalParTypes.get(i);
                        res.addAll(exp.checkSemantics(env));

                        if (exp.isID()) {
                            if (formalType.isReference()) {
                                env.setReference(id, i, exp.toIDValue().getEntry());
                            }
                            if (formalType.isDeleted()) {
                                env.deleteName(exp.toString());
                            }
                        }
                    }
                    throw new WrongParameterNumberError(id, formalParTypes.size(), actualParList.size());
                    } catch (IndexOutOfBoundsException | WrongParameterNumberError e) {
                        e.getMessage();
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
     * 4. the return address
     */
    @Override
    public String codeGen() {
        // Dynamic/control link, i.e. pointer to the env in which f is called
        StringBuilder builder = new StringBuilder("push $fp\n");

        // Actual parameters
        Collections.reverse(actualParList);
        Collections.reverse(formalParTypes);
        for (int i = 0; i < actualParList.size(); i++) {
            Exp exp = actualParList.get(i);
            LinfType formalType = formalParTypes.get(i);

            if (formalType.isReference()) {
                STentry referred = formalType.getRefTo();
                int offset = referred.getOffset();

                builder.append(LinfLib.followChain(nestingLevel - entry.getNestinglevel()))
                        .append("move $a0 $al\n");

                if (offset != 0) {
                    builder.append("addi $a0 $a0 ")
                            .append(offset)
                            .append("\n");
                }
            } else {
                builder.append(exp.codeGen());
            }
            builder.append("push $a0\n");
        }

        // Static/access link, i.e. pointer to the env in which f is declared
        return builder.append(LinfLib.pushAl(nestingLevel - entry.getNestinglevel()))
                // Give control to called
                .append("b ")
                .append(((FunType) entry.getType()).getFunLabel().replace(":", ""))
                // pop access link and parameters
                .append("addi $sp $sp ").append(actualParList.size() + 1).append("\n")
                // pop control link
                .append("top $fp\n")
                .append("pop\n")
                .toString();
    }
}

