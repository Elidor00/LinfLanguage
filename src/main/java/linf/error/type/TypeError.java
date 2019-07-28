package linf.error.type;

public abstract class TypeError extends Exception {
    private final String msg;

    public TypeError(String msg) {
        this.msg = "[TypeError] " + msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}

