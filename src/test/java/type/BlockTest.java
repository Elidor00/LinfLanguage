package type;

import linf.error.behaviour.IncompatibleBehaviourError;
import linf.error.type.TypeError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class BlockTest {
    @Test
    public void CheckType_ShouldPass_OnNestedDelete() throws TypeError {
        checkType("{\n" +

                "int x = 1;\n" +

                "foo(var int a){\n" +
                "delete a;\n" +
                "}\n" +

                "{ int x = 2; foo(x);  }\n" +

                "foo(x);\n" +

                "}");
    }




}
