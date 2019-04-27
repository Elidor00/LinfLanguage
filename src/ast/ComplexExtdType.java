package ast;

public abstract class ComplexExtdType implements Node {
    private boolean isRef;

    void setRef(boolean ref) {
        isRef = ref;
    }
    boolean isRef() {
        return isRef;
    }
}