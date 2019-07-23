package linf.expression;

import linf.BaseNode;
import linf.type.LinfType;


public abstract class Value implements BaseNode {
    private LinfType type;
    protected String value;

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(LinfType type) {
        this.type = type;
    }

    public LinfType getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }
}
