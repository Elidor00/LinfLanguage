package type;

import linf.error.type.TypeError;
import linf.error.type.UnbalancedDeletionBehaviourError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class IfThenElseTest {

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElse() {
        try {
            checkType(
                    "{" +
                            "int x = 1;" +
                            "if (x == 1) then {" +
                            "x = x + 1;}" +
                            "else {" +
                            "x = x - 1;" +
                            "}" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElse2() {
        try {
            checkType(
                    "{" +
                            "int x = 1;" +
                            "if (x == 1) then {" +
                                "print x;" +
                            "} else { " +
                                "x = 2;" +
                            "}" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElseDelete() {
        try {
            checkType(
                    "{" +
                            "int x = 1;" +
                            "if (x == 1) then {" +
                                "delete x;" +
                            "} else { " +
                                "delete x;" +
                            "}" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_SimpleNestedIfThenElse() {
        try {
            checkType(
                    "{" +
                            "int x = 1;" +
                            "int y = 2;" +
                            "if (x == 1) then {" +
                                "if (y >= 1) then {" +
                                    "x = x + 1;" +
                                "} else { " +
                                    "print x;" +
                                "}" +
                            "} else {" +
                                "print y;" +
                            "}" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_SimpleNestedIfThenElse2() {
        try {
            checkType(
                    "{" +
                            "int x = 1;" +
                            "int y = 2;" +
                                "if (x == 1) then {" +
                                    "print x;" +
                                "} else {" +
                                    "if (y >= 1) then {" +
                                        "x = x + 1;" +
                                    "} else { " +
                                        "print y;" +
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
    public void CheckType_ShouldPass_NestedIfThenElseDelete() {
        try {
            checkType(
                    "{" +
                            "int x = 0;" +
                            "int y = 0;" +
                            "if (x == 0) then {" +
                                "if (x == 0) then {" +
                                    "delete y;" +
                                "} else {" +
                                    "delete y;" +
                                "}" +
                            "} else {" +
                                "delete y;" +
                            "}" +
                            "}"
            );
        } catch (TypeError e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void CheckType_ShouldPass_NestedIfThenElseDelete2() {
        try {
            checkType(
                    "{" +
                            "int x = 0;" +
                            "int y = 0;" +
                                "if (x == 0) then {" +
                                    "delete y;" +
                                "} else {" +
                                    "if (x == 0) then {" +
                                        "delete y;" +
                                    "} else {" +
                                        "delete y;" +
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
    public void CheckType_ShouldFail_UnbalanceDeletionBehaviourError() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkType(
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
    public void CheckType_ShouldFail_UnbalanceDeletionBehaviourError1() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkType(
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
    public void CheckType_ShouldFail_UnbalancedDeletionBehaviourError2() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkType(
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
    public void CheckType_ShouldFail_UnbalancedDeletionBehaviourError3() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkType(
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
    public void CheckType_ShouldFail_UnbalancedDeletionBehaviourError4() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkType(
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
    public void CheckType_ShouldFail_UnbalancedDeletionBehaviourError5() {
        assertThrows(UnbalancedDeletionBehaviourError.class, () -> checkType(
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

}
