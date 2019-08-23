package codegen;

import linf.utils.LinfLib;
import lvm.LVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // emtpy block
                "move $fp $sp\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        runBytecode(actual);
    }

    @Test
    public void Unary_FunDec_Should_JustWork() {
        String actual = cgen("{ f(int x){ print x; } }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "b label0\n" +
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
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        runBytecode(actual);
    }

    @Test
    public void NestedDec_Should_JustWork() {
        String actual = cgen("{ f(int x){ g() { print x; } g(); } }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "b label0\n" +
                "fLabel1:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +
                "b label1\n" +
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
                "label1:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel0\n" +
                "addi $sp $sp 1\n" +
                "top $fp\n" +
                "pop\n" +
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        runBytecode(actual);
    }
}
