package lvm.error;


public class StackOverflowError extends LVMError {
    public StackOverflowError() {
        super("Stack Overflow! $sp cannot be less than 0.");
    }
}
