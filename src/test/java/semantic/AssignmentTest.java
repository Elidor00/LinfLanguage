package semantic;

import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.checkSemantics;

@RunWith(JUnit4.class)
public class AssignmentTest {
    @Test
    public void CheckSemantics_ShouldPass_WithSimpleAssignment() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int x = 7;"
                        + "x = x - 900 * (x*2);"
                        + "int y = x * 2;"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithUnboundLHSSymbol() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int x = 7;"
                        + "x = 90;"
                        + "y = x * 2;"
                        + "}"
        );
        assertEquals(1, errors.size());
        assertEquals(UnboundSymbolError.class, errors.get(0).getClass());
    }
}
