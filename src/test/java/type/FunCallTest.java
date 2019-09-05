package type;

import linf.error.type.*;
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
    public void CheckType_ShouldPass_SimpleFunCallWithExpParameters() {
        try {
            checkType(
                    "{" +
                            "int a = 5;" +
                            "bool b = true;" +
                            "f(int a, bool b, var bool c){" +
                                "print a;" +
                                "print c;" +
                            "}" +
                            "f((a+5), (b && false), b);" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_SimplePrototype() {
        try {
            checkType(
                    "{" +
                            "f(int x);" +
                            "int x = 42;" +
                            "f(int x){ print x; }" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldFail_SimplePrototypeError() {
        assertThrows(MismatchedPrototype.class, () -> checkType(
                    "{" +
                            "f(int x);" +
                            "bool x = true;" +
                            "f(bool x){ print a; }" +
                            "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_SimplePrototypeError2() {
        assertThrows(MismatchedPrototype.class, () -> checkType(
                "{" +
                        "f(bool x);" +
                        "int a = 3;" +
                        "f(int a){ print a; }" +
                        "}"
        ));
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

    @Test
    public void CheckType_ShouldFail_FunctionIdDeletedBeforeCalled() {
        assertThrows(FunctionNameDeletionError.class, () -> checkType(
                "{" +
                        "int x = 5;" +
                        "f(int x) {" +
                            "print x;" +
                        "}" +
                        "delete f;" +
                        "int a = 4;" +
                        "f(a);" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_FunctionIdDeletedBeforeCalled2() {
        assertThrows(FunctionNameDeletionError.class, () -> checkType(
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
        ));
    }

    @Test
    public void CheckType_ShouldFail_FunctionIdDeletedBeforeCalled3() {
        assertThrows(FunctionNameDeletionError.class, () -> checkType(
                "{" +
                        "f(int x) {" +
                            "delete f;" +
                            "x = 42;" +
                        "}" +
                        "int y = 2;" +
                        "f(y);" +
                        "f(y);" +
                        "}"
        ));
    }

}