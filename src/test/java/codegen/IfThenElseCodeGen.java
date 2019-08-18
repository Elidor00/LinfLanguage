package codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import linf.statement.Block;
import java.util.HashMap;

import static utils.TestUtils.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class IfThenElseCodeGen {

    private int label = 0;

    @Test
    public void ifThenElseEqual() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "ifThenElse_end");
        labels.put(2, "elseBranch");
        labels.put(3, "true");
        labels.put(4, "condition_end");

        Block block = makeAST(" { int x = 2; if (x == 2) then { } else { } }");
        String test =
                "push $fp \n" +
                "addi $sp $sp -1 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                    "li $a0 2 \n" + //x
                    "push $a0 \n" +

                    "li $a0 2 \n" + //2

                    "top $t1 \n" +
                    "pop \n" +
                    "beq $a0 $t1 true \n" +
                    "li $a0 0" +
                    "b condition_end" +
                    "true: \n" +
                    "li $a0 1" +
                    "condition_end: \n" +
                    "li $t1 0" +
                    "beq $a0 $t1 elseBranch \n" +

                    "push $fp \n" + //scope thenBranch
                    "push $fp \n" +
                    "move $fp $sp \n" +
                    "pop \n" +
                    "top $fp \n" +
                    "pop \n" +

                    "b ifThenElse_end \n" +
                    "elseBranch: \n" +

                    "push $fp \n" + //scope elseBranch
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

        String result = block.codeGen();
        assertEquals(test, result);
    }

}
