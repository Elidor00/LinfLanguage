package codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import linf.statement.Block;
import static utils.TestUtils.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class BlockCodeGen {

    @Test
    public void block() {
        Block block = makeAST(" { }");
        String test =
                "push $fp \n" +
                "push $fp \n" +
                "move $fp $sp \n" +
                "pop \n" +
                "top $fp \n" +
                "pop \n";
        String result = block.codeGen();
        assertEquals(test, result);
    }

    @Test
    public void blockVarDec() {
        Block block = makeAST(" { int x = 2; }");
        String test =
                "push $fp \n" +
                "addi $sp $sp -1 \n" + //cresce al contrario?
                "push $fp \n" +
                "move $fp $sp \n" +
                "li $a0 2 \n" +
                "sw $a0 4($fp) \n" +
                "pop \n" +
                "addi $sp $sp 1 \n" +
                "top $fp \n" +
                "pop \n";
        String result = block.codeGen();
        assertEquals(test, result);
    }

    @Test
    public void blockVarDecS() {
        Block block = makeAST(" { int x = 2; int y = 1;}");
        String test =
                "push $fp \n" +
                "addi $sp $sp -2 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "li $a0 2 \n" +      //x
                "sw $a0 4($fp) \n" +

                "li $a0 1 \n" +      //y
                "sw $a0 8($fp) \n" +

                "pop \n" +
                "addi $sp $sp 2 \n" +
                "top $fp \n" +
                "pop \n";
        String result = block.codeGen();
        assertEquals(test, result);
    }

    @Test
    public void nestedBlockVarDecS() {
        Block block = makeAST(" {{ int x = 2; int y = 1; }}");
        String test =
                "push $fp \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "push $fp \n" +
                "addi $sp $sp -2 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "li $a0 2 \n" +      //x
                "sw $a0 4($fp) \n" +

                "li $a0 1 \n" +      //y
                "sw $a0 8($fp) \n" +

                "pop \n" +
                "addi $sp $sp 2 \n" +
                "top $fp \n" +
                "pop \n" +

                "pop \n" +
                "top $fp \n" +
                "pop \n";

        String result = block.codeGen();
        assertEquals(test, result);
    }


}
