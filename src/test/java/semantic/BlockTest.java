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
public class BlockTest {
    @Test
    public void CheckSemantics_ShouldPass_WithSimpleBlock() {
        List<SemanticError> errors = checkSemantics("{}");
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldPass_WithNestdBlocks() {
        List<SemanticError> errors = checkSemantics("{{}}");
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithIllegalDeletion() {
        List<SemanticError> errors = checkSemantics("{int x = 0; { delete x; delete x; }}");

        assertEquals(2, errors.size());

        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("x", ((UnboundSymbolError) err).getId());

        err = errors.get(1);
        assertEquals(IllegalDeletionError.class, err.getClass());
        assertEquals("x", ((IllegalDeletionError) err).getId());
    }


}
