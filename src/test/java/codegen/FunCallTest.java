package codegen;


import lvm.utils.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.cgen;

@RunWith(JUnit4.class)
public class FunCallTest {
    @Before
    public void resetLabels() {
        Strings.reset();
    }

    @Test
    public void Simple_FunCall() {
        String actual = cgen("{ f() { print 0; } f(); }");
        String expected = "move $fp $sp\n" +
                "jal label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "print $a0\n" +
                // return control
                "top $ra\n" +
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
                "pop\n";
        assertEquals(expected, actual);
    }

    @Test
    public void Simple_NotLocal_FunCall() {
        String actual = cgen("{ f() { print 0; } { f(); } }");
        String expected = "move $fp $sp\n" +
                "jal label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "print $a0\n" +
                // return control
                "top $ra\n" +
                "addi $sp $sp 0\n" +
                "top $fp\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Inner block
                "move $fp $sp\n" +
                // Prepare stack for call
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "lw $al 0($al)\n" +
                "push $al\n" +
                "addi $ra $ip 2\n" +
                "jal fLabel0\n" +
                "pop\n";
        assertEquals(expected, actual);
    }

    @Test
    public void Unary_FunCall() {
        String actual = cgen("{ f(int x){ print x; } f(5); }");
        String expected = "move $fp $sp\n" +
                "jal label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "lw $a0 3($fp)\n" +
                "print $a0\n" +
                // return control
                "top $ra\n" +
                "addi $sp $sp 1\n" +
                "top $fp\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "push $fp\n" +
                "li $a0 0\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "addi $ra $ip 2\n" +
                "jal fLabel0\n" +
                "pop\n";
        assertEquals(expected, actual);
    }

    @Test
    public void Unary_Var_FunCall() {
        String actual = cgen("{ int k = 50; f(var int x){ print x; } f(k); }");
        String expected = "move $fp $sp\n" +
                "li $a0 50\n" +
                "push $a0\n" +
                "jal label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "lw $t1 3($fp)\n" +
                "lw $a0 0($t1)\n" +
                "print $a0\n" +
                // return control
                "top $ra\n" +
                "addi $sp $sp 1\n" +
                "top $fp\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "lw $al 0($al)\n"+
                "lw $a0 0($al)\n"+
                "push $a0\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "addi $ra $ip 2\n" +
                "jal fLabel0\n" +
                "pop\n";
        assertEquals(expected, actual);
    }
}
