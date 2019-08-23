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
                "subi $t1 $sp 2\n" +
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
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(5, vm.getA0());
        assertEquals(5, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void expMult() {
        String result = cgen(" { int x = 2*3; } ");
        String test =
                "subi $t1 $sp 2\n" +
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
                        "mult $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(6, vm.getA0());
        assertEquals(6, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void expSub() {
        String result = cgen(" { int x = 2-3; } ");
        String test =
                "subi $t1 $sp 2\n" +
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
                        "sub $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(-1, vm.getA0());
        assertEquals(-1, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void expDiv() {
        String result = cgen(" { int x = 2/3; } ");
        String test =
                "subi $t1 $sp 2\n" +
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
                        "div $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
        assertEquals(0, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void expPrecedenceMultSum() {
        String result = cgen(" { int x = 2+3*8; print x; } ");
        String test =
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //2
                        "li $a0 2\n" +
                        "push $a0\n" +
                        //3
                        "li $a0 3\n" +
                        "push $a0\n" +
                        //8
                        "li $a0 8\n" +
                        "top $t1\n" +
                        "pop\n" +
                        //8*3
                        "mult $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        //24+2
                        "add $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "lw $a0 0($fp)\n" +
                        "print\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        List<String> out = vm.getStdOut();
        assertEquals(26, vm.getA0());
        assertEquals(26, vm.peekMemory(MEMSIZE - 3));
        assertEquals("26", out.get(0));
    }

    @Test
    public void expOperatorPrecedence() {
        String result = cgen(" { int x = 2*3+2-4/2; } ");
        String test =
                "subi $t1 $sp 2\n" +
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
                        //2*3
                        "mult $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        //2
                        "li $a0 2\n" +
                        "push $a0\n" +
                        //4
                        "li $a0 4\n" +
                        "push $a0\n" +
                        //2
                        "li $a0 2\n" +
                        "top $t1\n" +
                        "pop\n" +
                        //4/2
                        "div $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        //2-2
                        "sub $a0 $t1 $a0\n" +
                        "top $t1\n" +
                        "pop\n" +
                        //6+0
                        "add $a0 $t1 $a0\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(6, vm.getA0());
        assertEquals(6, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void expAndTrue() {
        String result = cgen(" { bool x = true && true; } ");
        String test =
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //true
                        "li $a0 1\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label0\n" +
                        "li $a0 1\n" +
                        "label0:\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0());
        assertEquals(1, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void expAndFalse() {
        String result = cgen(" { bool x = true && false; }  ");
        String test =
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //true
                        "li $a0 1\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label0\n" +
                        "li $a0 0\n" +
                        "label0:\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
        assertEquals(0, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void expORTrue() {
        String result = cgen(" { bool x = true || false; } ");
        String test =
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //true
                        "li $a0 1\n" +
                        "li $t1 1\n" +
                        "beq $a0 $t1 label0\n" +
                        "li $a0 0\n" +
                        "label0:\n" +
                        "push $a0\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0());
        assertEquals(1, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void complexExp() {
        String result = cgen("{ int x = (78 / 2 + 454) * 2 - ((3 - (9 *3) + (527*2))) ;}");
        String test = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 78\n" +
                "li $t1 2\n" +
                "div $a0 $a0 $t1\n" +
                "addi $a0 $a0 454\n" +
                "li $t1 2\n" +
                "mult $a0 $a0 $t1\n" +
                "push $a0\n" +
                "li $a0 9\n" +
                "li $t1 3\n" +
                "mult $a0 $a0 $t1\n" +
                "push $a0\n" +
                "li $a0 572\n" +
                "li $t1 2\n" +
                "mult $a0 $a0 $t1\n" +
                "top $t1\n" +
                "pop\n" +
                "add $a0 $a0 $t1\n" +
                "addi $a0 $a0 3\n" +
                "top $t1\n" +
                "pop\n" +
                "sub $a0 $t1 $a0\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        //assertEquals(test, result);
        LVM vm = runBytecode(test);
        List<String> out = vm.getStdOut();
        assertEquals(-44, vm.getA0());
        assertEquals(-44, vm.peekMemory(MEMSIZE - 3));
        assertEquals("-44", out.get(0));

    }
}
