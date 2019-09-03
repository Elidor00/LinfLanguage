package type;

import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class AssignmentTest {

    @Test
    public void CheckType_ShouldPass_SimpleIntAssignment() {
        try {
            checkType(
                    "{" +
                            "int x = 2;" +
                            "int y = 42;" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_SimpleIntAssignment2() {
        try {
            checkType(
                    "{" +
                            "int x = 2;" +
                            "int y = 42;" +
                            "x = x + y;" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_SimpleBoolAssignment() {
        try {
            checkType(
                    "{" +
                            "bool x = true;" +
                            "bool y = false;" +
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
                "{bool x = 2;}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleTypesIntBool() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{int x = true;}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleTypesAssignment() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "bool b = true;" +
                        "int a = (b + 3);" +
                        "}"
        ));
    }

}

