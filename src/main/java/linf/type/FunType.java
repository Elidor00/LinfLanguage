package linf.type;

import linf.error.semantic.SemanticError;
import linf.utils.Environment;
import linf.utils.LinfLib;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FunType extends LinfType {
    private final List<STentry> parEntries = new ArrayList<>();
    private final HashSet<STentry> rwIDs = new HashSet<>();
    private final HashSet<STentry> deletedIDs = new HashSet<>();
    private boolean isPrototype = true;
    private String funLabel = LinfLib.freshFunLabel();

    public String getFunLabel() {
        return funLabel;
    }

    public void setFunLabel(String funLabel) {
        this.funLabel = funLabel;
    }

    public List<LinfType> getParTypes() {
        return parEntries.stream()
                .map(STentry::getType)
                .collect(Collectors.toList());
    }

    public List<STentry> getParEntries() {
        return parEntries;
    }

    public void addPar(STentry entry) {
        parEntries.add(entry);
    }

    public HashSet<STentry> getRwIDs() {
        return rwIDs;
    }

    public void setRwIDs(HashSet<STentry> rwIDs) {
        this.rwIDs.addAll(rwIDs);
    }

    public HashSet<STentry> getDeletedIDs() {
        return deletedIDs;
    }

    public void setDeletedIDs(HashSet<STentry> deletedIDs) {
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
        return "(" +
                getParTypes().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")) +
                ") -> void";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FunType funType = (FunType) o;

        return parEntries.equals(funType.parEntries);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + parEntries.hashCode();
        return result;
    }
}
