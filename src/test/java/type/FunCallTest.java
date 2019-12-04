package type;

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
    public void CheckType_ShouldPass_SimpleFunCall() throws TypeError {
        checkType(
                "{" +
                        "f(int x){" +
                        "x = x + 2;" +
                        "}" +
                        "f(40);" +
                        "}"
        );
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
    public void CheckType_ShouldPass_DeleteFunctionIdNotCall() throws TypeError {
        checkType(
                "{" +
                        "f(){" +
                        "delete f;" +
                        "}" +
                        "}"
        );
    }

}