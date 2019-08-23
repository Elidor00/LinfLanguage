package linf.type;

import linf.error.semantic.SemanticError;
import linf.expression.IDValue;
import linf.utils.Environment;
import linf.utils.LinfLib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FunType extends LinfType {
    private List<LinfType> parTypes = new ArrayList<>();
    private HashSet<IDValue> rwIDs = new HashSet<>();
    private HashSet<IDValue> deletedIDs = new HashSet<>();
    private boolean isPrototype = true;
    private String funLabel = LinfLib.freshFunLabel();

    public String getFunLabel() {
        return funLabel;
    }

    public List<LinfType> getParTypes() {
        return parTypes;
    }

    public void addParType(LinfType type) {
        this.parTypes.add(type);
    }

    public HashSet<IDValue> getRwIDs() {
        return rwIDs;
    }

    public void setRwIDs(HashSet<IDValue> rwIDs) {
        this.rwIDs.addAll(rwIDs);
    }

    public HashSet<IDValue> getDeletedIDs() {
        return deletedIDs;
    }

    public void setDeletedIDs(HashSet<IDValue> deletedIDs) {
        this.deletedIDs.addAll(deletedIDs);
    }

    public boolean isPrototype() {
        return isPrototype;
    }

    public void setPrototype(boolean prototype) {
        isPrototype = prototype;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public LinfType checkType() {
        return null;
    }

    @Override
    public String codeGen() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("( ");
        for (LinfType type : parTypes) {
            str.append(type.toString());
            str.append(" ");
        }
        str.append(") -> void");
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunType funType = (FunType) o;
        return parTypes.equals(funType.parTypes);
    }

    @Override
    public int hashCode() {
        int result = parTypes.hashCode();
        result = 31 * result + rwIDs.hashCode();
        result = 31 * result + deletedIDs.hashCode();
        result = 31 * result + (isPrototype ? 1 : 0);
        result = 31 * result + funLabel.hashCode();
        return result;
    }
}
