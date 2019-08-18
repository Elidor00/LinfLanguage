package linf.error.type;

public abstract class TypeError extends Exception {
    private final String msg;

    TypeError(String msg) {
        this.msg = TypeError.class.getSimpleName() + " " + msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}

