package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ComplexExtdFunType extends ComplexExtdType {
    private List<ComplexExtdType> parTypes = new ArrayList<>();
    private HashSet<ComplexExtdIDValue> rwIDs = new HashSet<>();
    private HashSet<ComplexExtdIDValue> deletedIDs = new HashSet<>();

    List<ComplexExtdType> getParTypes() {
        return parTypes;
    }

    void addParType(ComplexExtdType type) {
        this.parTypes.add(type);
    }

    HashSet<ComplexExtdIDValue> getRwIDs() {
        return rwIDs;
    }

    void setRwIDs(HashSet<ComplexExtdIDValue> rwIDs) {
        this.rwIDs.addAll(rwIDs);
    }

    void setDeletedIDs(HashSet<ComplexExtdIDValue> deletedIDs) {
        this.deletedIDs.addAll(deletedIDs);
    }

    HashSet<ComplexExtdIDValue> getDeletedIDs() {
        return deletedIDs;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public ComplexExtdType checkType() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("( ");
        for (ComplexExtdType type : parTypes) {
            str.append(type.toString());
            str.append(" ");
        }
        str.append(") -> void");
        return str.toString();
    }
}
