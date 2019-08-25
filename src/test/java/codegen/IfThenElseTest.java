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
public class IfThenElseTest {

    @Before
    public void resetLabels() {
        LinfLib.reset();
    }

    @Test
    public void IfThenElse_ShouldThen_IfCondIsTrue() {
        String result = cgen(" { int x = 2; if (x == 2) then { x = 3; } else { x = 0; } }");
        String test = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 2\n" +
                "push $a0\n" +
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "li $a0 2\n" +
                "top $t1\n" +
                "pop\n" +
                "beq $t1 $a0 label3\n" +
                "li $a0 0\n" +
                "b label2\n" +
                "label3:\n" +
                "li $a0 1\n" +
                "label2:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label1\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "li $a0 3\n" +
                "lw $al 2($fp)\n" +
                "sw $a0 0($al)\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "b label0\n" +
                "label1:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "lw $al 2($fp)\n" +
                "sw $a0 0($al)\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "label0:\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(3, vm.getA0());
        assertEquals(3, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void IfThenElse_ShouldElse_IfCondIsFalse() {
        String result = cgen(" { int x = 2; if (x != 2) then { x = 3;  } else { x = 0; }}");
        String test = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 2\n" +
                "push $a0\n" +
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "li $a0 2\n" +
                "top $t1\n" +
                "pop\n" +
                "bne $t1 $a0 label3\n" +
                "li $a0 0\n" +
                "b label2\n" +
                "label3:\n" +
                "li $a0 1\n" +
                "label2:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label1\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "li $a0 3\n" +
                "lw $al 2($fp)\n" +
                "sw $a0 0($al)\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "b label0\n" +
                "label1:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "lw $al 2($fp)\n" +
                "sw $a0 0($al)\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "label0:\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
        assertEquals(0, vm.peekMemory(MEMSIZE - 3));
    }


    @Test
    public void ifThenElseEqualThenBranch() {
        String result = cgen(" { int x = 2; if (x == 2) then { print x; } else { } }");
        String test =
                //main block
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //x = 2
                        "li $a0 2\n" +
                        "push $a0\n" +

                        "lw $a0 0($fp)\n" +
                        "push $a0\n" +
                        "li $a0 2\n" +

                        "top $t1\n" +
                        "pop\n" +
                        "beq $t1 $a0 label3\n" +
                        "li $a0 0\n" +
                        "b label2\n" +
                        "label3:\n" +
                        "li $a0 1\n" +
                        "label2:\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label1\n" +
                        //thenBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        //print
                        "lw $al 2($fp)\n" +
                        "lw $a0 0($al)\n" +
                        "print\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n" +

                        "b label0\n" +
                        "label1:\n" +

                        //elseBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n" +

                        "label0:\n" +

                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        List<String> out = vm.getStdOut();
        assertEquals(2, vm.getA0());
        assertEquals(1, out.size());
        assertEquals("2", out.get(0));
    }

    @Test
    public void ifThenElseNotEqualElseBranch() {
        String result = cgen(" { int x = 2; if (x != 2) then { } else { } }");
        String test = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 2\n" +
                "push $a0\n" +
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "li $a0 2\n" +
                "top $t1\n" +
                "pop\n" +
                "bne $t1 $a0 label3\n" +
                "li $a0 0\n" +
                "b label2\n" +
                "label3:\n" +
                "li $a0 1\n" +
                "label2:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label1\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "b label0\n" +
                "label1:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "label0:\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
    }

    @Test
    public void ifThenElseGreaterElseBranch() {
        String result = cgen(" { int x = 5; if (x > 9) then { } else { print x; } }");
        String test = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 5\n" +
                "push $a0\n" +
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "li $a0 9\n" +
                "top $t1\n" +
                "pop\n" +
                "bgr $t1 $a0 label3\n" +
                "li $a0 0\n" +
                "b label2\n" +
                "label3:\n" +
                "li $a0 1\n" +
                "label2:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label1\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "b label0\n" +
                "label1:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $al 2($fp)\n" +
                "lw $a0 0($al)\n" +
                "print\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "label0:\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("5", out.get(0));
        assertEquals(5, vm.getA0());
    }

    @Test
    public void ifThenElseGreaterEqualThenBranch() {
        String result = cgen(" { int x = 2; if (x >= 2) then { } else { } }");
        String test = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 2\n" +
                "push $a0\n" +
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "li $a0 2\n" +
                "top $t1\n" +
                "pop\n" +
                "bgre $t1 $a0 label3\n" +
                "li $a0 0\n" +
                "b label2\n" +
                "label3:\n" +
                "li $a0 1\n" +
                "label2:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label1\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "b label0\n" +
                "label1:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "label0:\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0());
    }

    @Test
    public void ifThenElseLessElseBranch() {
        String result = cgen(" { int x = 2; if (x < 2) then { } else { } }");
        String test = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 2\n" +
                "push $a0\n" +
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "li $a0 2\n" +
                "top $t1\n" +
                "pop\n" +
                "blr $t1 $a0 label3\n" +
                "li $a0 0\n" +
                "b label2\n" +
                "label3:\n" +
                "li $a0 1\n" +
                "label2:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label1\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "b label0\n" +
                "label1:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "label0:\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
    }

    @Test
    public void ifThenElseLessEqualThenBranch() {
        String result = cgen(" { int x = 2; if (x <= 2) then { } else { } }");
        String test = "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 2\n" +
                "push $a0\n" +
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "li $a0 2\n" +
                "top $t1\n" +
                "pop\n" +
                "blre $t1 $a0 label3\n" +
                "li $a0 0\n" +
                "b label2\n" +
                "label3:\n" +
                "li $a0 1\n" +
                "label2:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label1\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "b label0\n" +
                "label1:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "label0:\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0());
    }
}