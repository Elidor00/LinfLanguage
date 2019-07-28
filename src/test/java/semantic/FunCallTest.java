package semantic;

import linf.error.semantic.FunctionDeclarationOutOfScopeError;
import linf.error.semantic.SemanticError;
import linf.error.semantic.SymbolUsedAsFunctionError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.checkSemantics;

@RunWith(JUnit4.class)
public class FunCallTest {
    @Test
    public void  CheckSemantics_ShouldPass_WithSimpleFunCall() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "f(int x) { print x; }"
                        + "f(5);"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void  CheckSemantics_ShouldFail_WithUndeclaredFunction() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "g(int x) { print x; }"
                        + "{ f(var int x) { delete x;} g(6); }"
                        + "f(5);"
                        + "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(FunctionDeclarationOutOfScopeError.class, err.getClass());
        assertEquals("f", ((FunctionDeclarationOutOfScopeError) err).getId());
    }

    @Test
    public void  CheckSemantics_ShouldFail_WithVariableUsedAsFunction() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "bool f = true;"
                        + "g(int x) { print x; }"
                        + "f(5);"
                        + "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(SymbolUsedAsFunctionError.class, err.getClass());
        assertEquals("f", ((SymbolUsedAsFunctionError) err).getId());
    }
}
