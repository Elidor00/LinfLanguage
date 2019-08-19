package linf.type;


import linf.Node;
import linf.utils.STentry;

public abstract class LinfType implements Node {
    private STentry refTo;
    private boolean isParameter = false;
    private boolean isReference = false;
    private boolean isDeleted = false;
    private boolean rwAccess = false;

    public STentry getRefTo() {
        return refTo;
    }

    public void setRefTo(STentry refTo) {
        this.refTo = refTo;
    }

    public boolean isReference() {
        return isReference;
    }

    public void setReference() {
        isReference = true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isRwAccess() {
        return rwAccess;
    }

    public void setRwAccess() {
        this.rwAccess = true;
    }

    public boolean isParameter() {
        return isParameter;
    }

    public void setParameter(boolean parameter) {
        isParameter = parameter;
    }
}