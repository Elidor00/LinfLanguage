package execution;

import lvm.ExecuteVM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.assemble;

@RunWith(JUnit4.class)
public class LVMTest {
    @Test
    public void LoadInteger() {
        int[] assembled = assemble(
                "li $a0 500"
        );
        //exe vm
        ExecuteVM vm = new ExecuteVM();
        vm.cpu(assembled);
        assertEquals(500, vm.getA0());
    }
}
