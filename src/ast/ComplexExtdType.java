package ast;

public abstract class ComplexExtdType implements Node {
    private String refTo = "";
    private boolean isReference = false;
    private boolean isDeleted = false;

    public String getRefTo() {
        return refTo;
    }

    void setRefTo(String refTo) {
        this.refTo = refTo;
    }

    public boolean isReference() {
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
}