package linf.error.type;

public class UnbalancedDeletionBehaviourError extends TypeError {
    private static final String msg = "Deletion mismatch between \"then\" and \"else\" branches. The same identifiers must be deleted in both branches.";

    public UnbalancedDeletionBehaviourError() {
        super(msg);
    }
}
