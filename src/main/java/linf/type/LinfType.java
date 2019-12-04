package linf.type;


import linf.Node;
import linf.utils.STentry;

public abstract class LinfType implements Node {
    private STentry refTo;
    private boolean isReference = false;
    private boolean isDeleted = false;

    public STentry getRefTo() {
        return refTo;
    }

    public void setRefTo(STentry refTo) {
        assert !(refTo.getType() instanceof FunType);
        this.refTo = refTo;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isReference() {
        return isReference;
    }

    public void setReference() {
        isReference = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinfType linfType = (LinfType) o;
        return isReference() == linfType.isReference();
    }

    @Override
    public int hashCode() {
        return (isReference ? 1 : 0);
    }

    @Override
    public String toString() {
        if (isReference()) {
            return "var ";
        } else {
            return "";
        }
    }
}