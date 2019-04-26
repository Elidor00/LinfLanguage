package ast;

public abstract class ComplexExtdType implements Node {
    private boolean isRef;

    public void setRef(boolean ref) {
        isRef = ref;
    }

    public boolean isRef() {
        return isRef;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return null;
    }

}