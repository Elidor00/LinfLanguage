package ast;

public class TypeError {
    private final String msg;

    public TypeError(String msg) {
        this.msg = "[TypeError] " + msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}

