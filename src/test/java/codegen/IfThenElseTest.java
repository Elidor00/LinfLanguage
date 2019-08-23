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
public class IfThenElseTest {

    @Before
    public void resetLabels() {
        LinfLib.reset();
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
                        "beq $t1 $a0 label2\n" +
                        "li $a0 0\n" +
                        "b label3\n" +
                        "label2:\n" +
                        "li $a0 1\n" +
                        "label3:\n" +
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
                        "addi $sp $sp 2\n" +

                        "b label0\n" +
                        "label1:\n" +

                        //elseBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "label0:\n" +

                        "addi $sp $sp 1\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(2, vm.getA0());
    }

    @Test
    public void ifThenElseNotEqualElseBranch() {
        String result = cgen(" { int x = 2; if (x != 2) then { } else { } }");
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
                        "bne $t1 $a0 label2\n" +
                        "li $a0 0\n" +
                        "b label3\n" +
                        "label2:\n" +
                        "li $a0 1\n" +
                        "label3:\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label1\n" +
                        //thenBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "b label0\n" +
                        "label1:\n" +

                        //elseBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "label0:\n" +

                        "addi $sp $sp 1\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
    }

    @Test
    public void ifThenElseGreaterElseBranch() {
        String result = cgen(" { int x = 5; if (x > 9) then { } else { print x; } }");
        String test =
                //main block
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //x = 5
                        "li $a0 5\n" +
                        "push $a0\n" +

                        "lw $a0 0($fp)\n" +
                        "push $a0\n" +
                        "li $a0 9\n" +

                        "top $t1\n" +
                        "pop\n" +
                        "bgr $t1 $a0 label2\n" +
                        "li $a0 0\n" +
                        "b label3\n" +
                        "label2:\n" +
                        "li $a0 1\n" +
                        "label3:\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label1\n" +
                        //thenBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "b label0\n" +
                        "label1:\n" +

                        //elseBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        //print
                        "lw $al 2($fp)\n" +
                        "lw $a0 0($al)\n" +
                        "print\n" +
                        "addi $sp $sp 2\n" +

                        "label0:\n" +

                        "addi $sp $sp 1\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(5, vm.getA0());
    }

    @Test
    public void ifThenElseGreaterEqualThenBranch() {
        String result = cgen(" { int x = 2; if (x >= 2) then { } else { } }");
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
                        "bgre $t1 $a0 label2\n" +
                        "li $a0 0\n" +
                        "b label3\n" +
                        "label2:\n" +
                        "li $a0 1\n" +
                        "label3:\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label1\n" +
                        //thenBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "b label0\n" +
                        "label1:\n" +

                        //elseBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "label0:\n" +

                        "addi $sp $sp 1\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0());
    }

    @Test
    public void ifThenElseLessElseBranch() {
        String result = cgen(" { int x = 2; if (x < 2) then { } else { } }");
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
                        "blr $t1 $a0 label2\n" +
                        "li $a0 0\n" +
                        "b label3\n" +
                        "label2:\n" +
                        "li $a0 1\n" +
                        "label3:\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label1\n" +
                        //thenBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "b label0\n" +
                        "label1:\n" +

                        //elseBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "label0:\n" +

                        "addi $sp $sp 1\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0());
    }

    @Test
    public void ifThenElseLessEqualThenBranch() {
        String result = cgen(" { int x = 2; if (x <= 2) then { } else { } }");
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
                        "blre $t1 $a0 label2\n" +
                        "li $a0 0\n" +
                        "b label3\n" +
                        "label2:\n" +
                        "li $a0 1\n" +
                        "label3:\n" +
                        "li $t1 0\n" +
                        "beq $a0 $t1 label1\n" +
                        //thenBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "b label0\n" +
                        "label1:\n" +

                        //elseBranch block scope
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        "addi $sp $sp 2\n" +

                        "label0:\n" +

                        "addi $sp $sp 1\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0());
    }
}