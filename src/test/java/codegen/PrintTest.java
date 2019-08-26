package codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.cgen;

@RunWith(JUnit4.class)
public class PrintTest {

    @Test
    public void printVarDec() {
        String result = cgen(" { int y = 3; print y; } ");
        String test =
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //y = 3
                        "li $a0 3\n" +
                        "push $a0\n" +
                        //print
                        "lw $a0 0($fp)\n" +
                        "print\n" +
                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
    }
}
