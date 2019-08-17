package codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import linf.statement.Block;
import static utils.TestUtils.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class BlockCodeGen {

    @Test
    public void Block() {
        Block block = makeAST(" { }");
        String test =
                "push $fp \n" +
                "push $fp \n" +
                "move $fp $sp \n" +
                "pop \n" +
                "$fp <- top \n" +
                "pop \n";
        String result = block.codeGen();
        assertEquals(test, result);
    }

    @Test
    public void BlockVarDec() {
        Block block = makeAST(" { int x = 2; }");
        String test =
                "push $fp \n" +
                "addi $sp $sp -4 \n" + //cresce al contrario?
                "push $fp \n" +
                "move $fp $sp \n" +
                "li $a0 2 \n" +
                "sw $a0 4($fp) \n" +
                "pop \n" +
                "addi $sp $sp 4 \n" +
                "$fp <- top \n" +
                "pop \n";
        String result = block.codeGen();
        assertEquals(test, result);
    }

    @Test
    public void BlockVarDecS() {
        Block block = makeAST(" { int x = 2; int y = 1;}");
        String test =
                "push $fp \n" +
                "addi $sp $sp -8 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "li $a0 2 \n" +      //x
                "sw $a0 4($fp) \n" +

                "li $a0 1 \n" +      //y
                "sw $a0 8($fp) \n" +

                "pop \n" +
                "addi $sp $sp 8 \n" +
                "$fp <- top \n" +
                "pop \n";
        String result = block.codeGen();
        assertEquals(test, result);
    }

    @Test
    public void NestedBlockVarDecS() {
        Block block = makeAST(" {{ int x = 2; int y = 1; }}");
        String test =
                "push $fp \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "push $fp \n" +
                "addi $sp $sp -8 \n" +
                "push $fp \n" +
                "move $fp $sp \n" +

                "li $a0 2 \n" +      //x
                "sw $a0 4($fp) \n" +

                "li $a0 1 \n" +      //y
                "sw $a0 8($fp) \n" +

                "pop \n" +
                "addi $sp $sp 8 \n" +
                "$fp <- top \n" +
                "pop \n" +

                "pop \n" +
                "$fp <- top \n" +
                "pop \n";

        String result = block.codeGen();
        assertEquals(test, result);
    }


}
