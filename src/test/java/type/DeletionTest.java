package type;

import linf.error.type.TypeError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class DeletionTest {

    @Test
    public void CheckType_ShouldPass_SimpleDeletion() throws TypeError {
        checkType(
                "{" +
                        "bool x = true;" +
                        "int y = 1;" +
                        "delete x;" +
                        "delete y;" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldPass_MultipleDeletion() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_OnNestedDeletion() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_OnNestedDeletion1() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_OnNestedDeletion2() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_AssignmentAndDeletion() throws TypeError {
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

    }

}
