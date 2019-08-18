package lvm.error;

import static lvm.LVM.MEMSIZE;

public class StackOverflowError extends LVMError {
    public StackOverflowError() {
        super("Stack Overflow! $sp cannot be greater than " + MEMSIZE + ".");
    }
}
