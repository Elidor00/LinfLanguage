package type;

import linf.error.type.TypeError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
}

