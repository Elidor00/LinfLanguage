package linf.error.behaviour;

public class IncompatibleBehaviourError extends BehaviourError {
    private static final String msg = "Incompatible behaviour: Both RW and DEL access on identifier ";
    private String id;

    public IncompatibleBehaviourError(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return msg + id + ".";
    }
}
