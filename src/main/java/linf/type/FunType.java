package linf.type;

import linf.error.semantic.SemanticError;
import linf.expression.IDValue;
import linf.utils.Environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FunType extends LinfType {
    private List<LinfType> parTypes = new ArrayList<>();
    private HashSet<IDValue> rwIDs = new HashSet<>();
    private HashSet<IDValue> deletedIDs = new HashSet<>();

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

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
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
}
