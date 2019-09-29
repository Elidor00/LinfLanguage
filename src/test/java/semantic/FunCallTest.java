package semantic;

import linf.error.semantic.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.checkSemantics;

@RunWith(JUnit4.class)
public class FunCallTest {
    @Test
    public void CheckSemantics_ShouldPass_WithSimpleFunCall() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "f(int x) { print x; }"
                        + "f(5);"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithUndeclaredFunction() {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "g(int x) { print x; }"
                        + "{ f(var int x) { delete x;} g(6); }"
                        + "f(5);"
                        + "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(UnboundSymbolError.class, err.getClass());
        assertEquals("f", ((UnboundSymbolError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithVariableUsedAsFunction() {
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

    @Test
    public void CheckSemantics_ShouldFail_WrongParameterNumberErrorGreater() {
        List<SemanticError> errors = checkSemantics(
                "{" +
                        "f(int x, bool y){" +
                        "x = x - 7;" +
                        "y = y && false;" +
                        "}" +
                        "f(3, true, 4, 43);" +
                        "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(WrongParameterNumberError.class, err.getClass());
        assertEquals("f", ((WrongParameterNumberError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WrongParameterNumberErrorLess() {
        List<SemanticError> errors = checkSemantics(
                "{" +
                        "f(int x, bool y){" +
                        "}" +
                        "f(3);" +
                        "}"
        );
        assertEquals(1, errors.size());
        SemanticError err = errors.get(0);
        assertEquals(WrongParameterNumberError.class, err.getClass());
        assertEquals("f", ((WrongParameterNumberError) err).getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithDoubleDeletion_OnVarParameter() {
        List<SemanticError> errors = checkSemantics("{\n" +
                "\n" +
                "f(var int x, var int y){ delete x; delete y; }\n" +
                "\n" +
                "int x = 3; f(x,x) ;\n" +
                "\n" +
                "}");

        assertEquals(1, errors.size());
        assertEquals(VarParameterDoubleDeletionError.class, errors.get(0).getClass());
        assertEquals("x", (((VarParameterDoubleDeletionError) errors.get(0)).getId().toString()));
    }

    @Test
    public void CheckType_ShouldFail_WithDoubleDeletion_OnVarId2() {
        List<SemanticError> errors = checkSemantics("{\n" +

                "g(var int x, var int y){ delete x ; delete y ;}\n" +

                "f(var int z){ g(z,z) ; }\n" +

                "int x = 3 ; f(x) ;\n" +

                "}");

        assertEquals(1, errors.size());
        assertEquals(VarParameterDoubleDeletionError.class, errors.get(0).getClass());
        assertEquals("z", (((VarParameterDoubleDeletionError) errors.get(0)).getId().toString()));
    }

    @Test
    public void CheckSemantics_ShouldFail_FunctionIdDeletedBeforeCalled() {
        List<SemanticError> errors = checkSemantics(
                "{" +
                        "int x = 5;" +
                        "f(int x) {" +
                        "print x;" +
                        "}" +
                        "delete f;" +
                        "int a = 4;" +
                        "f(a);" +
                        "}"
        );
        assertEquals(1, errors.size());
        assertEquals(UnboundSymbolError.class, errors.get(0).getClass());
        assertEquals("f", (((UnboundSymbolError) errors.get(0)).getId()));
    }

    @Test
    public void CheckSemantics_ShouldFail_FunctionIdDeletedBeforeCalled1() {
        List<SemanticError> errors = checkSemantics(
                "{" +
                        "int x = 5;" +
                        "f(int x) {" +
                        "print x;" +
                        "}" +
                        "if (x > 2) then {" +
                        "delete f;" +
                        "} else {" +
                        "x = x + 1;" +
                        "int a = 4;" +
                        "f(a);" +
                        "}" +
                        "}"
        );
        assertEquals(1, errors.size());
        assertEquals(UnboundSymbolError.class, errors.get(0).getClass());
        assertEquals("f", (((UnboundSymbolError) errors.get(0)).getId()));
    }

    @Test
    public void CheckSemantics_ShouldFail_FunctionIdDeletedBeforeCalled2() {
        List<SemanticError> errors = checkSemantics(
                "{" +
                        "f(int x) {" +
                        "delete f;" +
                        "x = 42;" +
                        "}" +
                        "int y = 2;" +
                        "f(y);" +
                        "f(y);" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(UnboundSymbolError.class, errors.get(0).getClass());
        assertEquals("f", (((UnboundSymbolError) errors.get(0)).getId()));
    }
}
