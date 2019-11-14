package semantic;

import linf.error.behaviour.UnbalancedDeletionBehaviourError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkSemantics;

@RunWith(JUnit4.class)
public class IfThenElseTest {

    @Test
    public void CheckSemantics_ShouldFail_UnbalanceDeletionBehaviourError() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkSemantics(
                "{" +
                        "int x = 1;" +
                        "bool y = true;" +
                        "if (x == 1) then {" +
                        "delete x;" +
                        "} else { " +
                        "delete y;" +
                        "}" +
                        "}"
        ));
    }

    @Test
    public void CheckSemantics_ShouldFail_UnbalanceDeletionBehaviourError1() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkSemantics(
                "{" +
                        "int x = 1;" +
                        "bool y = true;" +
                        "if (x == 1) then {" +
                        "delete x;" +
                        "} else { " +
                        "}" +
                        "}"
        ));
    }

    @Test
    public void CheckSemantics_ShouldFail_UnbalancedDeletionBehaviourError2() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkSemantics(
                "{\n" +
                        "f(var int x, int y){\n" +
                        "    if (y == 0) then {\n" +
                        "        delete x;\n" +
                        "    } else {\n" +
                        "        x = x+y ;\n" +
                        "    }\n" +
                        "}\n" +

                        "int x = 3;\n " +
                        "f(x,x) ;\n" +
                        "}"));
    }

    @Test
    public void CheckSemantics_ShouldFail_UnbalancedDeletionBehaviourError3() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkSemantics(
                "{" +
                        "int x = 1;" +
                        "f() {" +
                        "if (x == 0) then {" +
                        "g() {" +
                        "f();" +
                        "}" +
                        "g();" +
                        "} else {" +
                        "g() {" +
                        "f();" +
                        "}" +
                        "g();" +
                        "delete x;" +
                        "}" +
                        "}" +
                        "f();" +
                        "}"
        ));
    }

    @Test
    public void CheckSemantics_ShouldFail_UnbalancedDeletionBehaviourError4() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkSemantics(
                "{" +
                        "f() {" +
                        "int x = 5;" +
                        "if (x == 5) then {" +
                        "delete f;" +
                        "} else {" +
                        "print x;" +
                        "}" +
                        "}" +
                        "}"
        ));
    }

    @Test
    public void CheckSemantics_ShouldFail_UnbalancedDeletionBehaviourError5() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkSemantics(
                "{" +
                        "f() { }" +
                        "g() {" +
                        "int a = 5;" +
                        "if (a == 3) then {" +
                        "delete f;" +
                        "} else {" +
                        "delete a;" +
                        "}" +
                        "}" +
                        "}"
        ));
    }

    @Test
    public void CheckSemantics_ShouldFail_FunctionIdDeletedBeforeCalled1() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkSemantics(
                "{" +
                        "int x = 5;" +
                        "f(int x) {" +
                        "print x;" +
                        "}" +
                        "if (x > 2) then {" +
                        "delete f;" +
                        "} else {" +
                        "x = x + 1;" +
                        "int a = 4;" +
                        "f(a);" +
                        "}" +
                        "}"
        ));
    }
}
