package semantic;


import linf.error.behaviour.BehaviourError;
import linf.error.behaviour.IncompatibleBehaviourError;
import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkSemantics;

@RunWith(JUnit4.class)
public class BlockTest {
    @Test
    public void CheckSemantics_ShouldPass_WithSimpleBlock() throws BehaviourError {
        List<SemanticError> errors = checkSemantics("{}");
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldPass_WithNestdBlocks() throws BehaviourError {
        List<SemanticError> errors = checkSemantics("{{}}");
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithIllegalDeletion() throws BehaviourError {
        List<SemanticError> errors = checkSemantics("{int x = 0; { delete x;} {delete x; }}");

        assertEquals(1, errors.size());

        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("x", ((UnboundSymbolError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithIncompatibleBehaviour2() {
        assertThrows(IncompatibleBehaviourError.class, () -> checkSemantics("{\n" +
                "\n" +
                "f(var int x, var int y){ int z = x ; delete x ; y = y+z ;}\n" +
                "\n" +
                "int x = 3 ; f(x,x) ;\n" +
                "\n" +
                "}"));
    }

    @Test
    public void CheckSemantics_ShouldFail_IncompatibleBehaviourError() {
        assertThrows(IncompatibleBehaviourError.class, () -> checkSemantics(
                "{" +
                        "int y = 1;" +
                        "f() {" +
                        "y = y + 4;" +
                        "delete y;" +
                        "}" +
                        "f();" +
                        "}"
        ));
    }
}
