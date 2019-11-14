package type;

import linf.error.behaviour.IncompatibleBehaviourError;
import linf.error.type.ReferenceParameterError;
import linf.error.type.TypeError;
import linf.error.type.WrongParameterTypeError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class FunCallTest {

    @Test
    public void CheckType_ShouldPass_SimpleFunCall() {
        try {
            checkType(
                    "{" +
                            "f(int x){" +
                            "x = x + 2;" +
                            "}" +
                            "f(40);" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldFail_WrongParameterTypeError() {
        assertThrows(WrongParameterTypeError.class, () -> checkType(
                "{" +
                        "f(int x, bool y){" +
                        "x = x - 7;" +
                        "y = y && false;" +
                        "}" +
                        "f(true, 42);" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_ReferenceParameterErrorInt() {
        assertThrows(ReferenceParameterError.class, () -> checkType(
                "{" +
                        "int x = 6;" +
                        "f(var int x) {" +
                        "x = x - 7;" +
                        "}" +
                        "f(42);" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_ReferenceParameterErrorBool() {
        assertThrows(ReferenceParameterError.class, () -> checkType(
                "{" +
                        "bool x = true;" +
                        "f(var bool x) {" +
                        "x = x || false;" +
                        "}" +
                        "f(false);" +
                        "}"
        ));
    }


    @Test
    public void CheckType_ShouldFail_WithIncompatibleBehaviour() {
        assertThrows(IncompatibleBehaviourError.class, () -> checkType("{\n" +
                "int x = 3;\n" +

                "foo(var int a) {\n" +
                "    a = x + 1;\n" +
                "    delete a;\n" +
                "    x = 35;\n" +
                "}\n" +

                "int y = 2;\n" +

                "foo(y);\n" +
                "foo(x); // <--- QUI DEVE DARE ERRORE!\n" +

                "bar(var int a){\n" +
                "    a = a + 1;\n" +
                "    delete a;\n" +
                "}\n" +
                "}"));
    }

    @Test
    public void CheckType_ShouldFail_DeleteFunctionIdAndCall() {
        assertThrows(IncompatibleBehaviourError.class, () -> checkType(
                "{" +
                        "f(){" +
                        "delete f;" +
                        "}" +
                        "f();" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldPass_DeleteFunctionIdNotCall() {
        try {
            checkType(
                    "{" +
                            "f(){" +
                            "delete f;" +
                            "}" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

}