package lvm.error;


public class StackUnderflowError extends LVMError {
    public StackUnderflowError() {
        super("Stack Underflow! $sp cannot be less than 0.");
    }
}
