package linf.type;


import linf.Node;

public abstract class LinfType implements Node {
    private String refTo = "";
    private boolean isReference = false;
    private boolean isDeleted = false;
    private boolean rwAccess = false;

    public String getRefTo() {
        return refTo;
    }

    public void setRefTo(String refTo) {
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
}