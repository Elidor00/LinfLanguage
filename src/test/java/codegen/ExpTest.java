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
public class ExpTest {

    @Before
    public void resetLabels() {
        LinfLib.reset();
    }

    @Test
    public void expSum() {
        String result = cgen(" { int x = 2+3; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //2
                        "li $a0 2\n" +
                        "push $a0\n" +
                        //3
                        "li $a0 3\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "add $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(5, vm.getA0());
        assertEquals(5, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void expMult() {
        String result = cgen(" { int x = 2*3; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 2\n" +
                        "push $a0\n" +
                        "li $a0 3\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "mult $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(6, vm.getA0());
        assertEquals(6, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void expSub() {
        String result = cgen(" { int x = 2-3; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 2\n" +
                        "push $a0\n" +
                        "li $a0 3\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "sub $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(-1, vm.getA0());
        assertEquals(-1, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void expDiv() {
        String result = cgen(" { int x = 2/3; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 2\n" +
                        "push $a0\n" +
                        "li $a0 3\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "div $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
        assertEquals(0, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void expPrecedenceMultSum() {
        String result = cgen(" { int x = 2+3*8; print x; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 2\n" +
                        "push $a0\n" +
                        "li $a0 3\n" +
                        "push $a0\n" +
                        "li $a0 8\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "mult $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "add $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "lw $a0 0($fp)\n" +
                        "print\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        List<String> out = vm.getStdOut();
        assertEquals(26, vm.getA0());
        assertEquals(26, vm.peekMemory(MEMSIZE - 4));
        assertEquals("26", out.get(0));
    }

    @Test
    public void expOperatorPrecedence() {
        String result = cgen(" { int x = 2*3+2-4/2; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 2\n" +
                        "push $a0\n" +
                        "li $a0 3\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "mult $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "li $a0 2\n" +
                        "push $a0\n" +
                        "li $a0 4\n" +
                        "push $a0\n" +
                        "li $a0 2\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "div $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "sub $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "add $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(6, vm.getA0());
        assertEquals(6, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void expAndTrue() {
        String result = cgen(" { bool x = true && true; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 1\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label0\n" +
                        "li $a0 1\n" +
                        "label0:\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0());
        assertEquals(1, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void expAndFalse() {
        String result = cgen(" { bool x = true && false; }  ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 1\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label0\n" +
                        "li $a0 0\n" +
                        "label0:\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
        assertEquals(0, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void expORTrue() {
        String result = cgen(" { bool x = true || false; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 1\n" +
                        "li $t1 1\n" +
                        "beq $a0 $t1 label0\n" +
                        "li $a0 0\n" +
                        "label0:\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0());
        assertEquals(1, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void complexNumericExp() {
        String result = cgen("{ int x = (78 / 2 + 454) * 2 - ((3 - (9 * 4) + (527*2))) ;}"); //-35
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "li $a0 78\n" +
                        "push $a0\n" +
                        "li $a0 2\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "div $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "li $a0 454\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "add $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "li $a0 2\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "mult $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "li $a0 3\n" +
                        "push $a0\n" +
                        "li $a0 9\n" +
                        "push $a0\n" +
                        "li $a0 4\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "mult $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "li $a0 527\n" +
                        "push $a0\n" +
                        "li $a0 2\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "mult $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "add $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "sub $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        "sub $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(test);
        assertEquals(2073, vm.getA0());
        assertEquals(2073, vm.peekMemory(MEMSIZE - 4));

    }

    @Test
    public void expWithUselessParentheses() {
        String result = cgen("{ int x =  (3 - 9 + 2) ;}"); //-4
        LVM vm = runBytecode(result);
        assertEquals(-8, vm.getA0());
        assertEquals(-8, vm.peekMemory(MEMSIZE - 4));
    }
}
