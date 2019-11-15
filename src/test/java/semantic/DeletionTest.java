package semantic;

import linf.error.behaviour.BehaviourError;
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
    public void CheckSemantics_ShouldPass_WithSimpleDeletion() throws BehaviourError {
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
    public void CheckSemantics_ShouldPass_WithSimpleFunctionDeletion() throws BehaviourError {
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
    public void CheckSemantics_ShouldPass_WithNestedDeletion() throws BehaviourError {
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
    public void CheckSemantics_ShouldFail_WithConsecutiveDeletions() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int y = 0;"
                        + "delete y;"
                        + "delete y;"
                        + "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("y", ((UnboundSymbolError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithUnboundDeletionSymbolError() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "delete y;"
                        + "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("y", ((UnboundSymbolError) err).getId());
    }


    @Test
    public void CheckSemantics_ShouldFail_WithUnboundSymbol() throws BehaviourError {
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

    @Test
    public void Delete_Should_Delete_VarIds() throws BehaviourError {
        List<SemanticError> errors = checkSemantics("{\n" +
                "f(var int x, var int y){ delete x; delete y; }\n" +
                "int x = 3;\n" +
                "x = 4;\n" +
                "int y = 3 ; f(x,y) ;\n" +
                "x = 3;\n" +
                "}");
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("x", ((UnboundSymbolError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_IllegalDeletionError() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{" +
                        "int x = 1;" + // always delete this x
                        "f(int y){" +
                        "delete x;" +
                        "}" +
                        "f(42);" +
                        "int x = 6;" +
                        "f(41);" +
                        "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(IllegalDeletionError.class, err.getClass());
        assertEquals("x", ((IllegalDeletionError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithIllegalDeletionBehaviour() throws BehaviourError {
        List<SemanticError> errors = checkSemantics("{\n" +

                "int x = 1;\n" +

                "foo(){delete x;}\n" +

                "{\n" +
                "int x = 2; foo();\n" +
                "}\n" +

                "foo();\n" +
                "}");
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(IllegalDeletionError.class, err.getClass());
        assertEquals("x", ((IllegalDeletionError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithUnboundSymbolBehaviour() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{int x = 0;\n" +
                        "\n" +
                        "g(var int k) { delete k; }\n" +
                        "\n" +
                        "if (x) then {\n" +
                        "    g(x);\n" +
                        "} else {\n" +
                        "    delete x;\n" +
                        "}\n" +
                        "\n" +
                        "print x;}");
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("x", ((UnboundSymbolError) err).getId());
    }
}
