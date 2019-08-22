package linf.expression;

import linf.error.semantic.SemanticError;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashSet;

public class Exp extends BinaryOp<Term, Exp> {
    private final HashSet<IDValue> rwIDs = new HashSet<>();
    private boolean isNegative;

    public void setTerm(Term term) {
        setLeft(term);
    }

    public boolean isNegative() {
        return isNegative;
    }

    public void setNegative(boolean negative) {
        isNegative = negative;
    }

    public HashSet<IDValue> getRwIDs() {
        return rwIDs;
    }

    public boolean isID() {
        Term term = getLeft();
        Factor factor = term.getFactor();
        LinfValue value = factor.getValue();

        return term.getRight() == null &&
                factor.getRight() == null &&
                value instanceof IDValue;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = super.checkSemantics(env);
        // populates rwIDs with all identifiers which are not local to this scope
        if (isID() && !(env.isLocalName(this.toString()))) {
            rwIDs.add((IDValue) getLeft().getFactor().getValue());
        } else {
            LinfValue val = getLeft().getFactor().getValue();
            Exp right = getRight();
            if (val instanceof Exp) {
                rwIDs.addAll(((Exp) val).getRwIDs());
            }
            if (right != null) {
                rwIDs.addAll(right.getRwIDs());
            }
        }

        for (IDValue id : rwIDs) {
            STentry entry = env.getStEntry(id);
            if (entry != null) {
                entry.getType().setRwAccess();
            }
        }
        return res;
    }

    public IDValue toIDValue() {
        if (isID()) {
            return (IDValue) getLeft().getFactor().getValue();
        } else {
            throw new IllegalStateException("Expression is not an identifier!");
        }
    }

    @Override
    public String codeGen() {
        return null;
    }
}