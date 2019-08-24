package type;

import linf.error.type.*;
import linf.error.type.TypeError;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class FunDecTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void CheckType_ShouldPass_SimpleFunDec() throws TypeError {
        checkType(
                "{" +
                        "f(int x, bool y){" +
                        "x = 7;" +
                        "y = true;" +
                        "}" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleTypesBoolInt() throws TypeError {
        exception.expect(IncompatibleTypesError.class);
        checkType(
                "{" +
                        "f(var int x, bool y){" +
                        "x = 7;" +
                        "y = x * 3;" +
                        "}" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleTypesIntBool() throws TypeError {
        exception.expect(IncompatibleTypesError.class);
        checkType(
                "{" +
                        "f(int x, bool y){" +
                        "x = 7 + false;" +
                        "y = true;" +
                        "}" +
                        "}"
        );
    }

}

