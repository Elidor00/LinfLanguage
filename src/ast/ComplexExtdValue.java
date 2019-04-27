package ast;

public abstract class ComplexExtdValue implements Node {
    private ComplexExtdType type;
    protected String value;

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(ComplexExtdType type) {
        this.type = type;
    }

    public ComplexExtdType getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }
}
