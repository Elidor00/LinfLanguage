package semantic;

import linf.error.semantic.IllegalDeletionError;
import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.checkSemantics;

@RunWith(JUnit4.class)
public class DeletionTest {
    @Test
    public void CheckSemantics_ShouldPass_WithSimpleDeletion() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int y = 0;"
                        + "delete y;"
                        + "int y = 90;"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldPass_WithSimpleFunctionDeletion() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "f(bool z) { print z; }"
                        + "delete f;"
                        + "f(var bool z) { if (z) then { z = true; } else { z = false; } }"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldPass_WithNestedDeletion() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "f(bool z) { print z; }"
                        + " { int x = 20; delete f; }"
                        + "f(var bool z) { if (z) then { z = true; } else { z = false; } }"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithConsecutiveDeletions() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int y = 0;"
                        + "delete y;"
                        + "delete y;"
                        + "}"
        );
        assertEquals(2, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("y", ((UnboundSymbolError) err).getId());
        err = errors.get(1);
        assertEquals(IllegalDeletionError.class, err.getClass());
        assertEquals("y", ((IllegalDeletionError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithUnboundSymbol() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int y = 0;"
                        + "delete y;"
                        + "delete x;"
                        + "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("x", ((UnboundSymbolError) err).getId());
    }
}
