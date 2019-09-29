package type;

import linf.error.type.DoubleDeletionError;
import linf.error.type.TypeError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class DeletionTest {

    @Test
    public void CheckType_ShouldPass_SimpleDeletion() {
        try {
            checkType(
                    "{" +
                            "bool x = true;" +
                            "int y = 1;" +
                            "delete x;" +
                            "delete y;" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_MultipleDeletion() {
        try {
            checkType(
                    "{" +
                            "int x = 5;" +
                            "int y = 3;" +
                            "f(var int a, var int b){" +
                            "delete x;" +
                            "g() {" +
                            "delete a;" +
                            "delete y;" +
                            "}" +
                            "g();" +
                            "}" +
                            "{" +
                            "{" +
                            "int a = 2;" +
                            "int b = 3;" +
                            "f(a,b);" +
                            "}" +
                            "}" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_OnNestedDeletion() {
        try {
            checkType(
                    "{" +
                            "int x = 1;" +
                            "{" +
                            "int x = 2;" +
                            "delete x;" +
                            "}" +
                            "delete x;" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_OnNestedDeletion1() {
        try {
            checkType(
                    "{" +
                            "int x = 1;" +
                            "f(var int a) {" +
                            "delete a;" +
                            "}" +
                            "{" +
                            "int x = 2;" +
                            "f(x);" +
                            "}" +
                            "f(x);" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_OnNestedDeletion2() {
        try {
            checkType(
                    "{" +
                            "int x = 1;" +
                            "f(var int w){" +
                            "delete x;" +
                            "int z = 3;" +
                            "g(var int a){" +
                            "delete a;" +
                            "delete w;" +
                            "}" +
                            "g(z);" +
                            "}" +
                            "int y = 2;" +
                            "f(y);" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_AssignmentAndDeletion() {
        try {
            checkType(
                    "{" +
                            "f(var int x, var int y, var bool z){" +
                            "int w = x;" +
                            "delete x;" +
                            "y = y + w;" +
                            "}" +
                            "int x = 3;" +
                            "int y = 4;" +
                            "bool z = true;" +
                            "f(x,y,z);" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldFail_DoubleDeletionError() {
        assertThrows(DoubleDeletionError.class, () -> checkType(
                "{" +
                        "int z = 5;" +
                        "f(var int a, var int b) {" +
                        "{" +
                        "delete a;" +
                        "delete b;" +
                        "delete z;" +
                        "}" +
                        "}" +
                        "{" +
                        "int x = 3;" +
                        "f(x,x);" +
                        "}" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_DoubleDeletionError1() {
        assertThrows(DoubleDeletionError.class, () -> checkType(
                "{" +
                        "int x = 1;" + // always delete this x
                        "f(int y){" +
                        "delete x;" +
                        "}" +
                        "f(42);" +
                        "int x = 6;" +
                        "f(41);" +
                        "}"
        ));
    }

}
