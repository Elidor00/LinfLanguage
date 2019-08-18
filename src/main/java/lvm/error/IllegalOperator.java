package lvm.error;

public class IllegalOperator extends LVMError {
    public IllegalOperator(int bytecode) {
        super("Unexpected operator: " + bytecode);
    }
}
