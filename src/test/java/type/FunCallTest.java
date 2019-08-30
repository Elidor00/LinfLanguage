package type;

import linf.error.type.IncompatibleBehaviourError;
import linf.error.type.ReferenceParameterError;
import linf.error.type.TypeError;
import linf.error.type.WrongParameterNumberError;
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
    public void CheckType_ShouldFail_WrongParameterNumberErrorGreater() {
        assertThrows(WrongParameterNumberError.class, () -> checkType(
                "{" +
                        "f(int x, bool y){" +
                        "x = x - 7;" +
                        "y = y && false;" +
                        "}" +
                        "f(3, true, 4, 43);" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WrongParameterNumberErrorLess() {
        assertThrows(WrongParameterNumberError.class, () -> checkType(
                "{" +
                        "f(int x, bool y){" +
                        "}" +
                        "f(3);" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WrongParameterTypeError() {
        assertThrows(WrongParameterNumberError.class, () -> checkType(
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
    public void CheckType_ShouldFail_IncompatibleBehaviourError() {
        assertThrows(IncompatibleBehaviourError.class, () -> checkType(
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

