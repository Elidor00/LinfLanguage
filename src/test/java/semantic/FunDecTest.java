package semantic;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.DoubleDeclarationError;
import linf.error.semantic.FunctionNameShadowingError;
import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.checkSemantics;

@RunWith(JUnit4.class)
public class FunDecTest {
    @Test
    public void CheckSemantics_ShouldPass_WithSimpleDeclaration() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "f(var int x, bool y) {"
                        + "y = x * 2;"
                        + "x = 70;"
                        + "}"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithParameterFunctionShadowing() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "int x = 7;"
                        + "f(var int f, bool y) {"
                        + "y = x * 2;"
                        + "}"
                        + "}"
        );
        assertEquals(1, errors.size());
        assertEquals(FunctionNameShadowingError.class, errors.get(0).getClass());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithAlreadyDeclaredID() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "f(var int x, bool y) {"
                        + "y = x * 2;"
                        + "x = 70;"
                        + "}"
                        + "g(int k) {"
                        + "print k;"
                        + "}"
                        + "f(int r) {"
                        + "delete r;"
                        + "}"
                        + "}"
        );
        assertEquals(1, errors.size());
        assertEquals(DoubleDeclarationError.class, errors.get(0).getClass());
    }

    @Test
    public void CheckSemantics_ShouldPass_RecursiveFunction() throws BehaviourError {
        List<SemanticError> errors = checkSemantics("" +
                "{ g(var int x) {\n" +
                "if (x==0) then {delete x;} else {x = x-1; print x; g(x);}\n" +
                "}\n" +
                "\n" +
                "int u = 90;\n" +
                "\n" +
                "g(u); }");
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_RecursiveFunction_UnboundSymbol() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{g(var int x) {\n" +
                        "if (x==0) then {delete x;} else {x = x-1; print x; g(x);}\n" +
                        "}\n" +
                        "\n" +
                        "int u = 90;\n" +
                        "\n" +
                        "g(u);" +
                        "print u;" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(UnboundSymbolError.class, errors.get(0).getClass());
        assertEquals("u", ((UnboundSymbolError) errors.get(0)).getId());
    }
}