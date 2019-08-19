package codegen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

import static utils.TestUtils.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class IfThenElseTest {

    private static int count_label = 0;

    @Before
    public void setCount_label() { count_label = 0; }

    @Test
    public void ifThenElseEqual() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "ifThenElse_end");
        labels.put(2, "elseBranch");
        labels.put(3, "true");
        labels.put(4, "condition_end");

        /*
        public String freshLabel() {
            count_label ++;
            return labels.get(count_label);
        }
        */

        //TODO: IDValue cgen
        String result = cgen(" { int x = 2; if (x == 2) then { } else { } }");
        String test =
                "push $fp \n" + //main block scope
                "addi $sp $sp -1 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                    "li $a0 2 \n" + //x
                    "push $a0 \n" +

                    "li $a0 2 \n" + //2

                    "top $t1 \n" +
                    "pop \n" +
                    "beq $a0 $t1 true \n" +
                    "li $a0 0 \n" +
                    "b condition_end \n" +
                    "true: \n" +
                    "li $a0 1 \n" +
                    "condition_end: \n" +
                    "li $t1 0 \n" +
                    "beq $a0 $t1 elseBranch \n" +

                    "push $fp \n" + //thenBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "b ifThenElse_end \n" +
                    "elseBranch: \n" +

                    "push $fp \n" + //elseBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "ifThenElse_end: \n" +

                "pop \n" +
                "addi $sp $sp 1 \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }

    @Test
    public void ifThenElseNotEqual() {

        String result = cgen(" { int x = 2; if (x != 2) then { } else { } }");
        String test =
                "push $fp \n" + //main block scope
                "addi $sp $sp -1 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                    "li $a0 2 \n" + //x
                    "push $a0 \n" +

                    "li $a0 2 \n" + //2

                    "top $t1 \n" +
                    "pop \n" +
                    "beq $a0 $t1 true \n" +
                    "li $a0 0 \n" +
                    "b condition_end \n" +
                    "true: \n" +
                    "li $a0 0 \n" +
                    "condition_end: \n" +
                    "li $t1 0 \n" +
                    "beq $a0 $t1 elseBranch \n" +

                    "push $fp \n" + //thenBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "b ifThenElse_end \n" +
                    "elseBranch: \n" +

                    "push $fp \n" + //elseBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "ifThenElse_end: \n" +

                "pop \n" +
                "addi $sp $sp 1 \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }

    @Test
    public void ifThenElseGreater() {

        String result = cgen(" { int x = 2; if (x > 2) then { } else { } }");
        String test =
                "push $fp \n" + //main block scope
                "addi $sp $sp -1 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                    "li $a0 2 \n" + //x
                    "push $a0 \n" +

                    "li $a0 2 \n" + //2

                    "top $t1 \n" +
                    "pop \n" +
                    "bgr $a0 $t1 true \n" +
                    "li $a0 0 \n" +
                    "b condition_end \n" +
                    "true: \n" +
                    "li $a0 1 \n" +
                    "condition_end: \n" +
                    "li $t1 0 \n" +
                    "beq $a0 $t1 elseBranch \n" +

                    "push $fp \n" + //thenBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "b ifThenElse_end \n" +
                    "elseBranch: \n" +

                    "push $fp \n" + //elseBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "ifThenElse_end: \n" +

                "pop \n" +
                "addi $sp $sp 1 \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }

    @Test
    public void ifThenElseGreaterEqual() {

        String result = cgen(" { int x = 2; if (x >= 2) then { } else { } }");
        String test =
                "push $fp \n" + //main block scope
                "addi $sp $sp -1 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                    "li $a0 2 \n" + //x
                    "push $a0 \n" +

                    "li $a0 2 \n" + //2

                    "top $t1 \n" +
                    "pop \n" +
                    "bgre $a0 $t1 true \n" +
                    "li $a0 0 \n" +
                    "b condition_end \n" +
                    "true: \n" +
                    "li $a0 1 \n" +
                    "condition_end: \n" +
                    "li $t1 0 \n" +
                    "beq $a0 $t1 elseBranch \n" +

                    "push $fp \n" + //thenBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "b ifThenElse_end \n" +
                    "elseBranch: \n" +

                    "push $fp \n" + //elseBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "ifThenElse_end: \n" +

                "pop \n" +
                "addi $sp $sp 1 \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }

    @Test
    public void ifThenElseLess() {

        String result = cgen(" { int x = 2; if (x < 2) then { } else { } }");
        String test =
                "push $fp \n" + //main block scope
                "addi $sp $sp -1 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                    "li $a0 2 \n" + //x
                    "push $a0 \n" +

                    "li $a0 2 \n" + //2

                    "top $t1 \n" +
                    "pop \n" +
                    "blr $a0 $t1 true \n" +
                    "li $a0 0 \n" +
                    "b condition_end \n" +
                    "true: \n" +
                    "li $a0 1 \n" +
                    "condition_end: \n" +
                    "li $t1 0 \n" +
                    "beq $a0 $t1 elseBranch \n" +

                    "push $fp \n" + //thenBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "b ifThenElse_end \n" +
                    "elseBranch: \n" +

                    "push $fp \n" + //elseBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "ifThenElse_end: \n" +

                "pop \n" +
                "addi $sp $sp 1 \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }

    @Test
    public void ifThenElseLessEqual() {

        String result = cgen(" { int x = 2; if (x <= 2) then { } else { } }");
        String test =
                "push $fp \n" + //main block scope
                "addi $sp $sp -1 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                    "li $a0 2 \n" + //x
                    "push $a0 \n" +

                    "li $a0 2 \n" + //2

                    "top $t1 \n" +
                    "pop \n" +
                    "blre $a0 $t1 true \n" +
                    "li $a0 0 \n" +
                    "b condition_end \n" +
                    "true: \n" +
                    "li $a0 1 \n" +
                    "condition_end: \n" +
                    "li $t1 0 \n" +
                    "beq $a0 $t1 elseBranch \n" +

                    "push $fp \n" + //thenBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "b ifThenElse_end \n" +
                    "elseBranch: \n" +

                    "push $fp \n" + //elseBranch block scope
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "ifThenElse_end: \n" +

                "pop \n" +
                "addi $sp $sp 1 \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }
}