package linf.error.type;

public class DoubleDeletionError extends TypeError {

    public DoubleDeletionError(String id) {
        super("Double Deletion. Identifier \"" + id + "\" will be deleted two or more times.");
    }
}
