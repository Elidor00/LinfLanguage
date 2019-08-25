package type;

import linf.error.type.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class IfThenElseTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElse() throws TypeError {
        checkType(
                "{" +
                        "int x = 1;" +
                        "if (x == 1) then {" +
                        "x = x + 1;}" +
                        "else {" +
                        "x = x - 1;" +
                        "}" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldFail_UnbalanceDeletionBehaviourError() throws TypeError {
        exception.expect(UnbalancedDeletionBehaviourError.class);
        checkType(
                "{" +
                        "int x = 1;" +
                        "bool y = true;" +
                        "if (x == 1) then {" +
                        "delete x;" +
                        "} else { " +
                        "delete y;" +
                        "}" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldFail_UnbalanceDeletionBehaviourError1() throws TypeError {
        exception.expect(UnbalancedDeletionBehaviourError.class);
        checkType(
                "{" +
                        "int x = 1;" +
                        "bool y = true;" +
                        "if (x == 1) then {" +
                        "delete x;" +
                        "} else { " +
                        "}" +
                        "}"
        );
    }

}
