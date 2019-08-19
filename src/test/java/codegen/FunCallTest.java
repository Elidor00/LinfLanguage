package codegen;


import lvm.utils.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.cgen;
import static utils.TestUtils.runBytecode;

@RunWith(JUnit4.class)
public class FunCallTest {
    @Before
    public void resetLabels() {
        Strings.reset();
    }

    @Test
    public void Simple_FunCall() {
        String actual = cgen("{ f() { print 0; } f(); }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "jal label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "print\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "addi $sp $sp 0\n" +
                "top $fp\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "addi $ra $ip 2\n" +
                "jal fLabel0\n" +
                "pop\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
    }

    @Test
    public void Simple_NotLocal_FunCall() {
        String actual = cgen("{ f() { print 0; } { f(); } }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "jal label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "print\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "addi $sp $sp 0\n" +
                "top $fp\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Inner block
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                // Prepare stack for call
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "lw $al 0($al)\n" +
                "push $al\n" +
                "addi $ra $ip 2\n" +
                "jal fLabel0\n" +
                "pop\n" +
                "addi $sp $sp 2\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
    }

    @Test
    public void Unary_FunCall() {
        String actual = cgen("{ f(int x){ print x; } f(5); }");
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
                "addi $sp $sp 1\n" +
                "top $fp\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "push $fp\n" +
                "li $a0 5\n" +
                "push $a0\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "addi $ra $ip 2\n" +
                "jal fLabel0\n" +
                "pop\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
    }

    @Test
    public void Unary_Var_FunCall() {
        String actual = cgen("{ int k = 50; f(var int x){ print x; } f(k); }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 50\n" +
                "push $a0\n" +
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
                "addi $sp $sp 1\n" +
                "top $fp\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "addi $ra $ip 2\n" +
                "jal fLabel0\n" +
                "pop\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        assertEquals("50", runBytecode(actual).getStdOut().get(0));
    }
}
