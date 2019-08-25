package lvm.error;

import static lvm.LVM.MEMSIZE;

public class StackUnderflowError extends LVMError {
    public StackUnderflowError() {
        super("Stack Underflow! $sp cannot be greater than " + MEMSIZE + ".");
    }
}
