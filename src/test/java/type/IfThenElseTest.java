package type;

import linf.error.behaviour.UnbalancedDeletionBehaviourError;
import linf.error.type.TypeError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;
import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class IfThenElseTest {

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElse() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElse2() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElse3() throws TypeError {
        checkType(
                "{" +
                        "int x = 1;" +
                        "bool b = false;" +
                        "if (false && (((x + 5) < 10) || (true || (x >= 6)))) then {" +
                        "print x;" +
                        "} else { " +
                        "x = 2;" +
                        "}" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElse4() throws TypeError {
        checkType(
                "{" +
                        "int x = 1;" +
                        "int y = 4;" +
                        "if ( (((x + 5) > (y + 3)) && ((x - 1) <= (y - 2))) ) then {" +
                        "print x;" +
                        "} else { " +
                        "x = 2;" +
                        "}" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldPass_SimpleIfThenElseDelete() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_SimpleNestedIfThenElse() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_SimpleNestedIfThenElse2() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_NestedIfThenElseDelete() throws TypeError {
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
    }

    @Test
    public void CheckType_ShouldPass_NestedIfThenElseDelete2() throws TypeError {
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
    }



}
