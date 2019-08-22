package codegen;

import lvm.LVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import lvm.utils.Strings;
import java.util.HashMap;

import static lvm.LVM.MEMSIZE;
import static utils.TestUtils.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class IfThenElseTest {

    @Before
    public void resetLabels() {
        Strings.reset();
    }

    private static HashMap<String,String> labels = new HashMap<>();
    static {
        labels.put("ifThenElse_end", "label0");
        labels.put("elseBranch", "label1");
        labels.put("true", "label2");
        labels.put("condition_end", "label3");
    }

    @Test
    public void ifThenElseEqualThenBranch() {
        String result = cgen(" { int x = 2; if (x == 2) then { } else { } }");
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
                    "beq $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 0\n" +
                    "b " + labels.get("condition_end") + "\n" +
                    labels.get("true") + ":\n" +
                    "li $a0 1\n" +
                    labels.get("condition_end") + ":\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 " + labels.get("elseBranch") + "\n" +
                    //thenBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    //print

                    "addi $sp $sp 2\n" +

                    "b " + labels.get("ifThenElse_end") + "\n" +
                    labels.get("elseBranch") + ":\n" +

                    //elseBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    labels.get("ifThenElse_end") + ":\n" +

                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(1, vm.getA0()); //trueBranch
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
                    "bne $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 0\n" +
                    "b " + labels.get("condition_end") + "\n" +
                    labels.get("true") + ":\n" +
                    "li $a0 1\n" +
                    labels.get("condition_end") + ":\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 " + labels.get("elseBranch") + "\n" +
                    //thenBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    "b " + labels.get("ifThenElse_end") + "\n" +
                    labels.get("elseBranch") + ":\n" +

                    //elseBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    labels.get("ifThenElse_end") + ":\n" +

                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(0, vm.getA0()); //elseBranch
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
                    "bg $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 0\n" +
                    "b " + labels.get("condition_end") + "\n" +
                    labels.get("true") + ":\n" +
                    "li $a0 1\n" +
                    labels.get("condition_end") + ":\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 " + labels.get("elseBranch") + "\n" +
                    //thenBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    "b " + labels.get("ifThenElse_end") + "\n" +
                    labels.get("elseBranch") + ":\n" +

                    //elseBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    //print
                    "lw $al 2($fp)\n" +
                    "lw $a0 0($al)\n" +
                    "print\n" +
                    "addi $sp $sp 2\n" +

                    labels.get("ifThenElse_end") + ":\n" +

                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        assertEquals(5, vm.getA0()); //elseBranch
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
                    "bge $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 0\n" +
                    "b " + labels.get("condition_end") + "\n" +
                    labels.get("true") + ":\n" +
                    "li $a0 1\n" +
                    labels.get("condition_end") + ":\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 " + labels.get("elseBranch") + "\n" +
                    //thenBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    "b " + labels.get("ifThenElse_end") + "\n" +
                    labels.get("elseBranch") + ":\n" +

                    //elseBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    labels.get("ifThenElse_end") + ":\n" +

                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
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
                    "bl $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 0\n" +
                    "b " + labels.get("condition_end") + "\n" +
                    labels.get("true") + ":\n" +
                    "li $a0 1\n" +
                    labels.get("condition_end") + ":\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 " + labels.get("elseBranch") + "\n" +
                    //thenBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    "b " + labels.get("ifThenElse_end") + "\n" +
                    labels.get("elseBranch") + ":\n" +

                    //elseBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    labels.get("ifThenElse_end") + ":\n" +

                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
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
                    "ble $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 0\n" +
                    "b " + labels.get("condition_end") + "\n" +
                    labels.get("true") + ":\n" +
                    "li $a0 1\n" +
                    labels.get("condition_end") + ":\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 " + labels.get("elseBranch") + "\n" +
                    //thenBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    "b " + labels.get("ifThenElse_end") + "\n" +
                    labels.get("elseBranch") + ":\n" +

                    //elseBranch block scope
                    "push $fp\n" +
                    "push $fp\n" +
                    "move $fp $sp\n" +
                    "addi $sp $sp 2\n" +

                    labels.get("ifThenElse_end") + ":\n" +

                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }
}