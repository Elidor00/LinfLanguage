package type;

import linf.error.type.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class AssignmentTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void CheckType_ShouldPass_SimpleIntAssignment() throws TypeError {
        checkType(
                "{" +
                        "int x = 2;" +
                        "int y = 42;" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldPass_SimpleBoolAssignment() throws TypeError {
        checkType(
                "{" +
                        "bool x = true;" +
                        "bool y = false;" +
                        "}"
        );
    }

    /*
    @Test (expected = IncompatibleTypesError.class)
    //(expected=Exception.class)
    public void CheckType_ShouldFail_WithIncompatibleTypes() throws TypeError {
        checkType(
                "{int x = true;}"
        );
    }
    */

    @Test
    public void CheckType_ShouldFail_WithIncompatibleTypesBoolInt() throws TypeError {
        exception.expect(IncompatibleTypesError.class);
        checkType(
                "{bool x = 2;}"
        );
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleTypesIntBool() throws TypeError {
        exception.expect(IncompatibleTypesError.class);
        checkType(
                "{int x = true;}"
        );
    }

}

