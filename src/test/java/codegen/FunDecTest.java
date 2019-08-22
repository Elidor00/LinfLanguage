package codegen;

import linf.utils.LinfLib;
import lvm.LVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static lvm.LVM.MEMSIZE;
import static org.junit.Assert.assertEquals;
import static utils.TestUtils.cgen;
import static utils.TestUtils.runBytecode;

@RunWith(JUnit4.class)
public class FunDecTest {
    @Before
    public void resetLabels() {
        LinfLib.reset();
    }

    @Test
    public void Empty_FunDec_Should_JustWork() {
        String actual = cgen("{ f(){} }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "jal label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // emtpy block
                "move $fp $sp\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        assertEquals(MEMSIZE - 1, vm.getSp());
    }

    @Test
    public void Unary_FunDec_Should_JustWork() {
        String actual = cgen("{ f(int x){ print x; } }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "jal label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "lw $a0 3($fp)\n" +
                "print\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        assertEquals(MEMSIZE - 1, vm.getSp());
    }

    @Test
    public void NestedDec_Should_JustWork() {
        assertEquals(true, false);
    }
}
