package lvm.error;

public abstract class LVMError extends Exception {
    private final String msg;

    LVMError(String msg) {
        this.msg = LVMError.class.getSimpleName() + " " + msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
