package linf.error.behaviour;

public class UnbalancedDeletionBehaviourError extends BehaviourError {
    private static final String msg = "Deletion mismatch between \"then\" and \"else\" branches. The same identifiers must be deleted in both branches.";

    @Override
    public String toString() {
        return msg;
    }
}
