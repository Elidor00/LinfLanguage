package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.*;

import linf.error.type.TypeError;
import linf.error.type.WrongParameterTypeError;
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


public class FunCall implements DeletingStatement, RWStatement {
    private final String id;
    private final List<Exp> actualParList = new ArrayList<>();
    private final List<LinfType> formalParTypes = new ArrayList<>();
    private STentry entry;
    private int nestingLevel;

    private final HashSet<STentry> rwIDs = new HashSet<>();
    private final HashSet<STentry> deletedIDs = new HashSet<>();

    public FunCall(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addPar(Exp exp) {
        actualParList.add(exp);
    }

    public HashSet<STentry> getRWSet() {
        return rwIDs;
    }

    public HashSet<STentry> getDelSet() {
        return deletedIDs;
    }

    @Override
    public LinfType checkType() throws TypeError {

        if (getDeletedIDs().contains(entry)){
            throw new IncompatibleBehaviourError(entry.getName());
        }

        for (int i = 0; i < formalParTypes.size(); i++) {
            Exp exp = actualParList.get(i);
            LinfType formalType = formalParTypes.get(i);
            LinfType actualType = exp.checkType();

            // checking types of single parameters
            if (!formalType.getClass().equals(actualType.getClass())) {
                throw new WrongParameterTypeError(id, formalType, actualType);
            }
        }
        return null;
    }


    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!env.containsName(id)) {
            res.add(new UnboundSymbolError(id));
        } else {
            entry = env.getStEntry(id);
            nestingLevel = env.getNestingLevel();
            if (entry.getType() instanceof FunType) {
                FunType type = (FunType) entry.getType();

                rwIDs.addAll(type.getRwIDs());
                deletedIDs.addAll(type.getDeletedIDs());
                formalParTypes.addAll(type.getParTypes());

                if (formalParTypes.size() != actualParList.size()) {
                    // checking number of parameters
                    res.add(new WrongParameterNumberError(id, formalParTypes.size(), actualParList.size()));
                } else {
                    for (int i = 0; i < formalParTypes.size(); i++) {
                        Exp exp = actualParList.get(i);
                        res.addAll(exp.checkSemantics(env));
                        LinfType formalType = formalParTypes.get(i);

                        if (formalType.isReference() && !exp.isID()) {
                            // checking parameters passed by reference
                            res.add(new ReferenceParameterError(id, exp));
                        }

                        if (res.size() != 0) {
                            return res;
                        }

                        if (exp.isID()) {
                            rwIDs.add(entry);
                            if (formalType.isReference()) {
                                env.setReference(type.getParEntries().get(i), exp.toIDValue().getEntry());
                                // checking deleted parameters
                                IDValue idValue = exp.toIDValue();
                                STentry entry = env.getStEntry(idValue.toString());
                                if (formalType.isDeleted()) {
                                    if (deletedIDs.contains(entry)) {
                                        res.add(new VarParameterDoubleDeletionError(idValue, this));
                                    } else {
                                        deletedIDs.add(entry);
                                    }
                                }

                            }
                        }
                    }
                    for (STentry entry : deletedIDs) {
                        if (env.isDeleted(entry)) {
                            res.add(new IllegalDeletionError(entry.getName()));
                        }
                    }
                }
            } else {
                res.add(new SymbolUsedAsFunctionError(id, entry.getType()));
            }
        }

        return res;
    }

    /**
     * { int x = 0;
     * f() { g(); }
     * g() { print 0; print x;}
     * }
     * <p>
     * <p>
     * <p>

     * 1. actual parameters
     * 2. the control link which points to AR of caller of g
     * 3. the ACCESS/STATIC link
     * 4. the return address
     * 6. var1 <-------- fp
     * 6. var2  <-------------------- sp
     */
    @Override
    public String codeGen() {
        StringBuilder builder = new StringBuilder();

        // Actual parameters
        Collections.reverse(actualParList);
        Collections.reverse(formalParTypes);
        for (int i = 0; i < actualParList.size(); i++) {
            Exp exp = actualParList.get(i);
            LinfType formalType = formalParTypes.get(i);

            if (formalType.isReference()) {
                builder.append(exp.toIDValue().loadAddress());
            } else {
                builder.append(exp.codeGen());
            }
            builder.append("push $a0\n");
        }

        // Dynamic/control link, i.e. pointer to the env in which f is called
        return builder.append("push $fp\n")
                // Static/access link, i.e. pointer to the env in which f is declared
                .append(LinfLib.pushAl(nestingLevel - entry.getNestinglevel()))
                // Give control to called
                .append("b ")
                .append(((FunType) entry.getType()).getFunLabel().replace(":", ""))
                // restore $fp to point to the AR of caller
                .append("lw $fp 2($sp)\n")
                // pop access link, control link and parameters
                .append("addi $sp $sp ").append(actualParList.size() + 2).append("\n")
                .toString();
    }
}

