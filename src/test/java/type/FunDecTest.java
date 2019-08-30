package type;

import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class FunDecTest {

    @Test
    public void CheckType_ShouldPass_SimpleFunDec() {
        try {
            checkType(
                    "{" +
                            "f(int x, bool y){" +
                            "x = 7;" +
                            "y = true;" +
                            "}" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleTypesBoolInt() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "f(var int x, bool y){" +
                        "x = 7;" +
                        "y = x * 3;" +
                        "}" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleTypesIntBool() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "f(int x, bool y){" +
                        "x = 7 + false;" +
                        "y = true;" +
                        "}" +
                        "}"
        ));
    }

}

