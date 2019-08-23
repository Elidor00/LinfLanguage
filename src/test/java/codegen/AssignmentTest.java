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
public class AssignmentTest {
    @Before
    public void resetLabels() {
        LinfLib.reset();
    }

    @Test
    public void LocalAssignment_Should_JustWork() {
        String actual = cgen("{ int k = 5; k = 7; }");
        String expected = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 5\n" +
                "push $a0\n" +
                "li $a0 7\n" +
                "sw $a0 0($fp)\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        assertEquals(7, vm.getA0());
        assertEquals(MEMSIZE - 3, vm.getFp());
        assertEquals(7, vm.peekMemory(MEMSIZE - 3));

    }

    @Test
    public void GlobalAssignment_Should_JustWork() {
        String actual = cgen("{ int k = 5; { { { k = 7; } } } }");
        String expected = "subi $t1 $sp 2\n" + // Block
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // Declaration
                "li $a0 5\n" +
                "push $a0\n" +
                // Nested blocks
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                // Global assignment
                "li $a0 7\n" +
                "lw $al 2($fp)\n" +
                "lw $al 2($al)\n" +
                "lw $al 2($al)\n" +
                "sw $a0 0($al)\n" +
                "lw $fp 2($sp)\n" +
                // Pop access link and return address
                "addi $sp $sp 2\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                // Pop declaration
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                // Pop access link and return address
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        assertEquals(7, vm.getA0());
        assertEquals(7, vm.peekMemory(MEMSIZE - 3));


        String actual2 = cgen("{ int k = 5; f(){ k = 7; } f(); }");
        String expected2 = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 5\n" +
                "push $a0\n" +
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +
                "li $a0 7\n" +
                "lw $al 2($fp)\n" +
                "sw $a0 0($al)\n" +
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                "addi $sp $sp 1\n" +
                "top $fp\n" +
                "pop\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(expected2, actual2);
        vm = runBytecode(actual);
        assertEquals(7, vm.getA0());
        assertEquals(7, vm.peekMemory(MEMSIZE - 3));

    }

    @Test
    public void ParameterAssignment_Should_JustWork() {
        String actual = cgen("{ int k = 5; f(int par){  { par = 7; } print par; } f(k);}");
        String expected = "subi $t1 $sp 2\n" + // Block
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // Declaration
                "li $a0 5\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                // Global assignment
                "li $a0 7\n" +
                "lw $al 2($fp)\n" +
                "sw $a0 3($al)\n" +
                "lw $fp 2($sp)\n" +
                // Pop access link and return address
                "addi $sp $sp 2\n" +
                "lw $a0 3($fp)\n" +
                "print\n" +
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                "push $fp\n" +
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                "addi $sp $sp 2\n" +
                "top $fp\n" +
                "pop\n" +
                // Pop declaration
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                // Pop access link and return address
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("7", out.get(0));
        assertEquals(7, vm.getA0());
        assertEquals(5, vm.peekMemory(MEMSIZE - 3));
        assertEquals(7, vm.peekMemory(MEMSIZE - 5));

    }

    @Test
    public void VarParameterAssignment_Should_JustWork() {
        String actual = cgen("{ int k = 5; f(var int par){  { par = 7; } print par; print k; } f(k);}");
        String expected = "subi $t1 $sp 2\n" + // Block
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // Declaration
                "li $a0 5\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                // Global assignment
                "li $a0 7\n" +
                "lw $al 2($fp)\n" +
                "lw $al 3($al)\n" +
                "sw $a0 0($al)\n" +
                "lw $fp 2($sp)\n" +
                // Pop access link and return address
                "addi $sp $sp 2\n" +
                "lw $al 3($fp)\n" +
                "lw $a0 0($al)\n" +
                "print\n" +
                "lw $al 2($fp)\n" +
                "lw $a0 0($al)\n" +
                "print\n" +
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "move $a0 $al\n" +
                "push $a0\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                "addi $sp $sp 2\n" +
                "top $fp\n" +
                "pop\n" +
                // Pop declaration
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                // Pop access link and return address
                "addi $sp $sp 2\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(2, out.size());
        assertEquals("7", out.get(0));
        assertEquals("7", out.get(1));
        assertEquals(7, vm.getA0());
        assertEquals(7, vm.peekMemory(MEMSIZE - 3));

    }
}
