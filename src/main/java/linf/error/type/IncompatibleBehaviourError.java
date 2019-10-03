package linf.error.type;

public class IncompatibleBehaviourError extends TypeError {
    private static final String msg = "Deletion Error: Both RW and DEL access on identifier ";

    public IncompatibleBehaviourError(String id) {
        super(msg + id + ".");
    }
}
