package linf.expression;

import linf.Node;
import linf.type.LinfType;


public abstract class LinfValue implements Node {
    String value;
    private LinfType type;

    void setValue(String value) {
        this.value = value;
    }

    public LinfType getType() {
        return type;
    }

    public void setType(LinfType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return value;
    }
}
