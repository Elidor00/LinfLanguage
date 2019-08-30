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
    public void CheckType_ShouldPass_OnNestedDeletion() {
        try {
            checkType("{\n" +
                    "\n" +
                    "int x = 1;\n" +
                    "\n" +
                    "{\n" +
                    "    int x = 2;\n" +
                    "    delete x;\n" +
                    "}\n" +
                    "\n" +
                    "delete x;\n" +
                    "\n" +
                    "}");
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }
}

