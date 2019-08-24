package type;

import linf.error.type.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class FunCallTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
    public void CheckType_ShouldFail_WrongParameterNumberError() throws TypeError {
        exception.expect(WrongParameterNumberError.class);
        checkType(
                "{" +
                        "f(int x, bool y){" +
                        "x = x - 7;" +
                        "y = y && false;" +
                        "}" +
                        "f(3, true, 4, 43);" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldFail_WrongParameterTypeError() throws TypeError {
        exception.expect(WrongParameterTypeError.class);
        checkType(
                "{" +
                        "f(int x, bool y){" +
                        "x = x - 7;" +
                        "y = y && false;" +
                        "}" +
                        "f(true, 42);" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldFail_ReferenceParameterError() throws TypeError {
        exception.expect(ReferenceParameterError.class);
        checkType(
                "{" +
                        "int x = 6;" +
                        "f(var int x) {" +
                        "x = x - 7;" +
                        "}" +
                        "f(42);" +
                        "}"
        );
    }

}

