package codegen;


import linf.utils.LinfLib;
import lvm.LVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static lvm.LVM.MEMSIZE;
import static org.junit.Assert.assertEquals;
import static utils.TestUtils.cgen;
import static utils.TestUtils.runBytecode;

@RunWith(JUnit4.class)
public class FunCallTest {
    @Before
    public void resetLabels() {
        LinfLib.reset();
    }

    @Test
    public void Simple_FunCall_Should_JustWork() {
        String actual = cgen("{ f() { print 0; } f(); }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "print\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                "addi $sp $sp 1\n" +
                "top $fp\n" +
                "pop\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("0", out.get(0));
        assertEquals(MEMSIZE - 1, vm.getSp());
    }

    @Test
    public void Simple_NotLocal_FunCall_Should_JustWork() {
        String actual = cgen("{ f() { print 0; } { f(); } print 5; }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "print\n" +
                // return control
                "top $ra\n" +
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
                "push $al\n" +
                "b fLabel0\n" +
                "addi $sp $sp 1\n" +
                "top $fp\n" +
                "pop\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "li $a0 5\n" +
                "print\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(2, out.size());
        assertEquals("0", out.get(0));
        assertEquals("5", out.get(1));
        assertEquals(MEMSIZE - 1, vm.getSp());
    }

    @Test
    public void Unary_FunCall_Should_JustWork() {
        String actual = cgen("{ f(int x){ print x; } f(5); }");
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
                // Prepare stack for call
                "push $fp\n" +
                "li $a0 5\n" +
                "push $a0\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                "addi $sp $sp 2\n" +
                "top $fp\n" +
                "pop\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("5", out.get(0));
        assertEquals(MEMSIZE - 1, vm.getSp());
    }

    @Test
    public void Binary_FunCall_Should_JustWork() {
        String actual = cgen("{ int k = 0; int r = 1;\n" +
                "f(int x, int y){\n" +
                "int t = x; x = y; y = t;\n" +
                "print x; print y; }\n" +
                "f(k,r);\n" +
                "print k; print r; }");

        String expected = "subi $t1 $sp 2\n" + // Root block
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // k declaration
                "li $a0 0\n" +
                "push $a0\n" +
                // r declaration
                "li $a0 1\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                //t declaration
                "lw $a0 3($fp)\n" + // <----------------------
                "push $a0\n" +
                // x assignment
                "lw $a0 4($fp)\n" + // <----------------------
                "sw $a0 3($fp)\n" +
                // y assignment
                "lw $a0 0($fp)\n" + // <----------------------
                "sw $a0 4($fp)\n" +
                // print x
                "lw $a0 3($fp)\n" + // <----------------------
                "print\n" +
                // print y
                "lw $a0 4($fp)\n" + // <----------------------
                "print\n" +
                // pop t
                "addi $sp $sp 1\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Fun call
                // Push control link
                "push $fp\n" +
                // Push arguments
                // Push r
                "lw $a0 -1($fp)\n" + // <----------------------
                "push $a0\n" +
                // push k
                "lw $a0 0($fp)\n" + // <----------------------
                "push $a0\n" +
                // push access link
                "lw $al 2($fp)\n" + // <----------------------
                "push $al\n" +
                "b fLabel0\n" +
                // pop access link and parameters
                "addi $sp $sp 3\n" +
                "top $fp\n" +
                "pop\n" +
                // print k
                "lw $a0 0($fp)\n" + // <----------------------
                "print\n" +
                // print r
                "lw $a0 -1($fp)\n" + // <----------------------
                "print\n" +
                "addi $sp $sp 2\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(4, out.size());
        assertEquals("1", out.get(0));
        assertEquals("0", out.get(1));
        assertEquals("0", out.get(2));
        assertEquals("1", out.get(3));
        assertEquals(0, vm.peekMemory(MEMSIZE - 3));
        assertEquals(1, vm.peekMemory(MEMSIZE - 4));
        assertEquals(MEMSIZE - 1, vm.getSp());
    }

    @Test
    public void Unary_Var_FunCall_Should_JustWork() {
        String actual = cgen("{ int k = 50; f(var int x){ print x; x = 60; } f(k); }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 50\n" +
                "push $a0\n" +
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // AR
                "move $fp $sp\n" +
                // print
                "lw $al 3($fp)\n" +
                "lw $a0 0($al)\n" +
                "print\n" +
                // x assignment
                "li $a0 60\n" +
                "lw $al 3($fp)\n" +
                "sw $a0 0($al)\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "push $fp\n" +
                // Args
                "lw $al 2($fp)\n" +
                "move $a0 $al\n" +
                "push $a0\n" +
                // Access link
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                "addi $sp $sp 2\n" +
                "top $fp\n" +
                "pop\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("50", out.get(0));
        assertEquals(60, vm.peekMemory(MEMSIZE - 3));
        assertEquals(MEMSIZE - 1, vm.getSp());
    }

    @Test
    public void NestedCall_Should_JustWork() {
        assertEquals(true, false);
    }

    @Test
    public void RecursiveCall_Should_JustWork() {
        assertEquals(true, false);
    }

    @Test
    public void MutuallyRecursiveCall_Should_JustWork() {
        assertEquals(true, false);
    }
}
