package type;

import linf.error.type.DoubleDeletionError;
import linf.error.type.IncompatibleBehaviourError;
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


    @Test
    public void CheckType_ShouldFail_WithIncompatibleBehaviour2() {
        assertThrows(IncompatibleBehaviourError.class, () -> checkType("{\n" +
                "\n" +
                "f(var int x, var int y){ int z = x ; delete x ; y = y+z ;}\n" +
                "\n" +
                "int x = 3 ; f(x,x) ;\n" +
                "\n" +
                "}"));
    }

    @Test
    public void CheckType_ShouldFail_IncompatibleBehaviourError() {
        assertThrows(IncompatibleBehaviourError.class, () -> checkType(
                "{" +
                        "int y = 1;" +
                        "f() {" +
                        "y = y + 4;" +
                        "delete y;" +
                        "}" +
                        "f();" +
                        "}"
        ));
    }

    @Test
    public void CheckType_ShouldFail_WithDoubleDeletion_OnVarId3() {
        assertThrows(DoubleDeletionError.class, () -> checkType("{\n" +

                "g(var bool x, var bool y){ delete x ; delete y ;}\n" +

                "f(var bool z){ g(z,z) ; }\n" +

                "bool x = true ; f(x) ;\n" +

                "}"));
    }
}
