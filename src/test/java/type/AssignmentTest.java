package type;

import linf.error.type.DoubleDeletionError;
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
    public void CheckType_ShouldPass_SomeSimpleIntAssignment() {
        try {
            checkType(
                    "{" +
                            "int x = 2;" +
                            "int y = 42;" +
                            "x = x + y;" +
                            "x = x * y;" +
                            "x = x - y;" +
                            "x = x / y;" +
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
    public void CheckType_ShouldPass_SimpleBoolAssignment1() {
        try {
            checkType(
                    "{" +
                            "int a = 3;" +
                            "bool b = true;" +
                            "b = false && (((a + 5) < 10) || (1 == 1));" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_SomeSimpleBoolAssignment() {
        try {
            checkType(
                    "{" +
                            "int a = 3;" +
                            "bool b = true;" +
                            "b = false && (((a + 5) < 10) || (1 == 1));" +
                            "b = a == 2;" +
                            "b = a > 2;" +
                            "b = a < 2;" +
                            "b = a <= 2;" +
                            "b = a >= 2;" +
                            "b = a != 2;" +
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
    public void CheckType_ShouldFail_WithIncompatibleIntTypesAssignment() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "bool b = true;" +
                        "int a = (b + 3);" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleIntTypesAssignment1() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "int a = 2;" +
                        "bool b = true;" +
                        "a = 3 && 4;" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleIntTypesAssignment2() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "int a = 2;" +
                        "bool b = true;" +
                        "a = 3 || 4;" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleBoolTypesAssignment() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "int a = 2;" +
                        "bool b = true;" +
                        "b = a + 3;" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleBoolTypesAssignment1() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "int a = 2;" +
                        "bool b = true;" +
                        "b = a - 3;" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleBoolTypesAssignment2() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "int a = 2;" +
                        "bool b = true;" +
                        "b = a * 3;" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleBoolTypesAssignment3() {
        assertThrows(IncompatibleTypesError.class, () -> checkType(
                "{" +
                        "int a = 2;" +
                        "bool b = true;" +
                        "b = a / 3;" +
                        "}"
        ));
    }

}

