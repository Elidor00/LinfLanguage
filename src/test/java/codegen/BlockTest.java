package codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static utils.TestUtils.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class BlockTest {

    @Test
    public void block() {
        String result = cgen(" { }");
        String test =
                "push $fp \n" +
                "push $fp \n" +
                "move $fp $sp \n" +
                "pop \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }

    @Test
    public void blockVarDec() {
        String result = cgen(" { int x = 2; }");
        String test =
                "push $fp \n" +
                "addi $sp $sp -1 \n" + //cresce al contrario?
                "push $fp \n" +
                "move $fp $sp \n" +

                "li $a0 2 \n" +  //x
                "push $a0 \n" +

                "pop \n" +
                "addi $sp $sp 1 \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }

    @Test
    public void blockVarDecS() {
        String result = cgen(" { int x = 2; int y = 1;}");
        String test =
                "push $fp \n" +
                "addi $sp $sp -2 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "li $a0 2 \n" +      //x
                "push $a0 \n" +

                "li $a0 1 \n" +      //y
                "push $a0 \n" +

                "pop \n" +
                "addi $sp $sp 2 \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }

    @Test
    public void nestedBlockVarDecS() {
        String result = cgen(" {{ int x = 2; int y = 1; }}");
        String test =
                "push $fp \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "push $fp \n" +
                "addi $sp $sp -2 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "li $a0 2 \n" +      //x
                "push $a0 \n" +

                "li $a0 1 \n" +      //y
                "push $a0 \n" +

                "pop \n" +
                "addi $sp $sp 2 \n" +
                "top $fp \n" +
                "pop \n" +

                "pop \n" +
                "top $fp \n" +
                "pop \n";
        assertEquals(test, result);
    }
}