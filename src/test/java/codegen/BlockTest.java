package codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static utils.TestUtils.cgen;

@RunWith(JUnit4.class)
public class BlockTest {

    @Test
    public void block() {
        String result = cgen(" { } ");
        String test =
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void blockVarDec() {
        String result = cgen(" { int x = 2; }");
        String test =
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //x = 2
                        "li $a0 2\n" +
                        "push $a0\n" +

                        "addi $sp $sp 1\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void blockVarDecS() {
        String result = cgen(" { int x = 2; int y = 1;}");
        String test =
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //x
                        "li $a0 2\n" +
                        "push $a0\n" +
                        //y
                        "li $a0 1\n" +
                        "push $a0\n" +

                        "addi $sp $sp 2\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void nestedBlockVarDecS() {
        String result = cgen(" {{ int x = 2; int y = 1; }}");
        String test =
                //main block
                "subi $t1 $sp 2\n" +
                        "push $t1\n" +
                        "push $t1\n" +
                        "move $fp $sp\n" +
                        //nested block
                        "push $fp\n" +
                        "push $fp\n" +
                        "move $fp $sp\n" +
                        //x
                        "li $a0 2\n" +
                        "push $a0\n" +
                        //y
                        "li $a0 1\n" +
                        "push $a0\n" +
                        //close nested block
                        "addi $sp $sp 2\n" +
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n" +
                        //close main block
                        "lw $fp 2($sp)\n" +
                        "addi $sp $sp 2\n";
        assertEquals(test, result);
    }
}