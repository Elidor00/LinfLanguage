package linf.error.type;

import linf.type.LinfType;

public class IncompatibleTypesError extends TypeError {
    public IncompatibleTypesError(LinfType lhs, LinfType rhs) {
        super("Incompatible types. " + lhs + " is required, but " + rhs + " was found.");
    }
}
