package type;

import linf.error.type.DoubleDeletionError;
import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static utils.TestUtils.checkType;

@RunWith(JUnit4.class)
public class DeletionTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
    public void CheckType_ShouldFail_DoubleDeletionErrorBlock() throws TypeError {
        exception.expect(DoubleDeletionError.class);
        checkType(
                "{" +
                        "int x = 1;" +
                        "delete x;" +
                        "delete x;" +
                        "}"
        );
    }

    @Test
    public void CheckType_ShouldFail_DoubleDeletionErrorNestedBlock() throws TypeError {
        exception.expect(DoubleDeletionError.class);
        checkType(
                "{" +
                        "{" +
                        "int x = 1;" +
                        "delete x;" +
                        "delete x;" +
                        "}" +
                        "}"
        );
    }

}

