package semantic;

import linf.error.semantic.DoubleDeclarationError;
import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.checkSemantics;

@RunWith(JUnit4.class)
public class VarDecTest {
    @Test
    public void CheckSemantics_ShouldPass_WithSimpleAssignment() {
        List<SemanticError> errors = checkSemantics("{ int x = 1 ; }");
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldPass_WithWellFormedRecAssignment() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int y = 701;"
                        + "int x = y + 1;"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithUnboundRecAssignment() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int x = y + 1;"
                        + "}"
        );
        assertEquals(1, errors.size());
        assertEquals(UnboundSymbolError.class, errors.get(0).getClass());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithAlreadyDeclaredID() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int x = 10 + 1;"
                        + "int y = x * 2;"
                        + "int x = 70;"
                        + "}"
        );
        assertEquals(1, errors.size());
        assertEquals(DoubleDeclarationError.class, errors.get(0).getClass());
    }
}
