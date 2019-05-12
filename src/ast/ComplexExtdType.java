package ast;

public abstract class ComplexExtdType implements Node {
    private String refTo = "";
    private boolean isReference = false;
    private boolean isDeleted = false;
    private boolean rwAccess = false;

    public String getRefTo() {
        return refTo;
    }

    void setRefTo(String refTo) {
        this.refTo = refTo;
    }

    boolean isReference() {
        return isReference;
    }

    void setReference() {
        isReference = true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    boolean isRwAccess() {
        return rwAccess;
    }

    void setRwAccess() {
        this.rwAccess = true;
    }
}