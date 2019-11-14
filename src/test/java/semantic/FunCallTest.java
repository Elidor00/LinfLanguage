package semantic;

import linf.error.behaviour.BehaviourError;
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
    public void CheckSemantics_ShouldPass_WithSimpleFunCall() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{ "
                        + "f(int x) { print x; }"
                        + "f(5);"
                        + "}"
        );
        assertEquals(0, errors.size());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithUndeclaredFunction() throws BehaviourError {
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
    public void CheckSemantics_ShouldFail_WithVariableUsedAsFunction() throws BehaviourError {
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
    public void CheckSemantics_ShouldFail_WrongParameterNumberErrorGreater() throws BehaviourError {
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
    public void CheckSemantics_ShouldFail_WrongParameterNumberErrorLess() throws BehaviourError {
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
    public void CheckSemantics_ShouldFail_WithDoubleDeletion_OnVarParameter() throws BehaviourError {
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
    public void CheckType_ShouldFail_WithDoubleDeletion_OnVarId2() throws BehaviourError {
        List<SemanticError> errors = checkSemantics("{\n" +

                "g(var int x, var int y){ delete x ; delete y ;}\n" +

                "f(var int z){ g(z,z) ; }\n" +

                "int x = 3 ; f(x) ;\n" +

                "}");

        assertEquals(1, errors.size());
        assertEquals(VarParameterDoubleDeletionError.class, errors.get(0).getClass());
        assertEquals("z", (((VarParameterDoubleDeletionError) errors.get(0)).getId().toString()));
        assertEquals("g", ((VarParameterDoubleDeletionError) errors.get(0)).getFun().getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithDoubleDeletion_OnVarId3() throws BehaviourError {
        List<SemanticError> errors = checkSemantics("{\n" +

                "g(var bool x, var bool y){ delete x ; delete y ;}\n" +

                "f(var bool z){ g(z,z) ; }\n" +

                "bool x = true ; f(x) ;\n" +

                "}");
        assertEquals(1, errors.size());

        SemanticError err = errors.get(0);
        assertEquals(VarParameterDoubleDeletionError.class, err.getClass());
        assertEquals("z", ((VarParameterDoubleDeletionError) err).getId().toString());
        assertEquals("g", ((VarParameterDoubleDeletionError) err).getFun().getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_WithDoubleDeletion_OnVarId4() throws BehaviourError {
        List<SemanticError> errors = checkSemantics(
                "{" +
                        "int z = 5;" +
                        "f(var int a, var int b) {" +
                        "{" +
                        "delete a;" +
                        "delete b;" +
                        "delete z;" +
                        "}" +
                        "}" +
                        "{" +
                        "int x = 3;" +
                        "f(x,x);" +
                        "}" +
                        "}"
        );
        SemanticError err = errors.get(0);
        assertEquals(VarParameterDoubleDeletionError.class, err.getClass());
        assertEquals("x", ((VarParameterDoubleDeletionError) err).getId().toString());
        assertEquals("f", ((VarParameterDoubleDeletionError) err).getFun().getId());
    }

    @Test
    public void CheckSemantics_ShouldFail_FunctionIdDeletedBeforeCalled() throws BehaviourError {
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
    public void CheckSemantics_ShouldFail_FunctionIdDeletedBeforeCalled2() throws BehaviourError {
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
