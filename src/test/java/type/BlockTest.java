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
    public void CheckType_ShouldPass_OnNestedDelete() {
        try {
            checkType("{\n" +

                    "int x = 1;\n" +

                    "foo(var int a){\n" +
                    "delete a;\n" +
                    "}\n" +

                    "{ int x = 2; foo(x);  }\n" +

                    "foo(x);\n" +

                    "}");
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldFail_WithIncompatibleBehaviour() {
        assertThrows(IncompatibleBehaviourError.class, () -> checkType("{\n" +
                "int x = 3;\n" +

                "foo(var int a) {\n" +
                "    a = x + 1;\n" +
                "    delete a;\n" +
                "    x = 35;\n" +
                "}\n" +

                "int y = 2;\n" +

                "foo(y);\n" +
                "foo(x); // <--- QUI DEVE DARE ERRORE!\n" +

                "bar(var int a){\n" +
                "    a = a + 1;\n" +
                "    delete a;\n" +
                "}\n" +
                "}"));
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
    public void CheckType_ShouldFail_WithDoubleDeletionBehaviour() {
        assertThrows(DoubleDeletionError.class, () -> checkType("{\n" +

                "int x = 1;\n" +

                "foo(){delete x;}\n" +

                "{\n" +
                "int x = 2; foo();\n" +
                "}\n" +

                "foo();\n" +
                "}"));
    }

    @Test
    public void CheckType_ShouldFail_WithDoubleDeletionBehaviour_OnVarId() {
        assertThrows(DoubleDeletionError.class, () -> checkType("{\n" +
                "\n" +
                "f(var int x, var int y){ delete x; delete y; }\n" +
                "\n" +
                "int x = 3; f(x,x) ;\n" +
                "\n" +
                "}"));
    }

    @Test
    public void CheckType_ShouldFail_WithDoubleDeletion_OnVarId2() {
        assertThrows(DoubleDeletionError.class, () -> checkType("{\n" +

                "g(var int x, var int y){ delete x ; delete y ;}\n" +

                "f(var int z){ g(z,z) ; }\n" +

                "int x = 3 ; f(x) ;\n" +

                "}"));
    }
}
