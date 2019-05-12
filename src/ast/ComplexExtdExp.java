package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.HashSet;

public class ComplexExtdExp extends ComplexExtdBinaryOp<ComplexExtdTerm, ComplexExtdExp> {
    private boolean isNegative;
    private HashSet<ComplexExtdIDValue> rwIDs = new HashSet<>();

    void setTerm(ComplexExtdTerm term) {
        setLeft(term);
    }

    public boolean isNegative() {
        return isNegative;
    }

    void setNegative(boolean negative) {
        isNegative = negative;
    }

    HashSet<ComplexExtdIDValue> getRwIDs() {
        return rwIDs;
    }

    boolean isID() {
        ComplexExtdTerm term = getLeft();
        ComplexExtdFactor factor = term.getFactor();
        ComplexExtdValue value = factor.getValue();

        return term.getRight() == null &&
                factor.getRight() == null &&
                value instanceof ComplexExtdIDValue;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = super.checkSemantics(env);
        // populates rwIDs with all identifiers which are not local to this scope
        if (isID() && !(env.isLocalName(this.toString()))) {
            rwIDs.add((ComplexExtdIDValue) getLeft().getFactor().getValue());
        } else {
            ComplexExtdValue val = getLeft().getFactor().getValue();
            ComplexExtdExp right = getRight();
            if (val instanceof ComplexExtdExp) {
                rwIDs.addAll(((ComplexExtdExp) val).getRwIDs());
            }
            if (right != null) {
                rwIDs.addAll(right.getRwIDs());
            }
        }

        for (ComplexExtdIDValue id : rwIDs) {
            env.getStEntry(id).getType().setRwAccess();
        }
        return res;
    }

    ComplexExtdIDValue toComplexExtdIDValue() {
        if (isID()) {
            return (ComplexExtdIDValue) getLeft().getFactor().getValue();
        } else {
            throw new IllegalStateException("Expression is not an identifier!");
        }
    }
}
