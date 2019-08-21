package codegen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import lvm.utils.Strings;
import java.util.HashMap;

import static utils.TestUtils.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ExpTest {

    @Before
    public void resetLabels() {
        Strings.reset();
    }

    private static HashMap<String,String> labels = new HashMap<>();
    static {
        labels.put("true", "label1");
        labels.put("false", "label2");
    }

    @Test
    public void expSum() {
        String result = cgen( " { int x = 2+3; } ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //2
                    "li $a0 2\n" +
                    "push $a0\n" +
                    //3
                    "li $a0 3\n" +
                    "top $t1\n" +
                    "pop\n" +
                    "add $a0 $t1 $a0\n" +
                    "push $a0\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void expMult() {
        String result = cgen( " { int x = 2*3; } ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //2
                    "li $a0 2\n" +
                    "push $a0\n" +
                    //3
                    "li $a0 3\n" +
                    "top $t1\n" +
                    "pop\n" +
                    "mult $a0 $t1 $a0\n" +
                    "push $a0\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void expSub() {
        String result = cgen( " { int x = 2-3; } ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //2
                    "li $a0 2\n" +
                    "push $a0\n" +
                    //3
                    "li $a0 3\n" +
                    "top $t1\n" +
                    "pop\n" +
                    "sub $a0 $t1 $a0\n" +
                    "push $a0\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void expDiv() {
        String result = cgen( " { int x = 2/3; } ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //2
                    "li $a0 2\n" +
                    "push $a0\n" +
                    //3
                    "li $a0 3\n" +
                    "top $t1\n" +
                    "pop\n" +
                    "div $a0 $t1 $a0\n" +
                    "push $a0\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void expPrecedenceMultSum() {
        String result = cgen( " { int x = 2+3*8; print x; } ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //2
                    "li $a0 2\n" +
                    "push $a0\n" +
                    //3
                    "li $a0 3\n" +
                    "push $a0\n" +
                    //8
                    "li $a0 8\n" +
                    "top $t1\n" +
                    "pop\n" +
                    //8*3
                    "mult $a0 $t1 $a0\n" +
                    "top $t1\n" +
                    "pop\n" +
                    //24+2
                    "add $a0 $t1 $a0\n" +
                    "push $a0\n" +
                    "lw $a0 0($al)\n" +
                    "print\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void expOperatorPrecedence() {
        String result = cgen( " { int x = 2*3+2-4/2; } ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //2
                    "li $a0 2\n" +
                    "push $a0\n" +
                    //3
                    "li $a0 3\n" +
                    "top $t1\n" +
                    "pop\n" +
                    //2*3
                    "mult $a0 $t1 $a0\n"+
                    "push $a0\n" +
                    //2
                    "li $a0 2\n" +
                    "push $a0\n" +
                    //4
                    "li $a0 4\n" +
                    "push $a0\n" +
                    //2
                    "li $a0 2\n" +
                    "top $t1\n" +
                    "pop\n" +
                    //4/2
                    "div $a0 $t1 $a0\n" +
                    "top $t1\n" +
                    "pop\n" +
                    //2-2
                    "sub $a0 $t1 $a0\n" +
                    "top $t1\n" +
                    "pop\n" +
                    //6+0
                    "add $a0 $t1 $a0\n" +
                    "push $a0\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void expAndTrue() {
        String result = cgen( " { bool x = true && true; } ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //true
                    "li $a0 1\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 1\n" +
                    labels.get("true") + ":\n" +
                    "push $a0\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void expAndFalse() {
        String result = cgen( " { bool x = true && false; }  ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //true
                    "li $a0 1\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 0\n" +
                    labels.get("true") + ":\n" +
                    "push $a0\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }

    @Test
    public void expORTrue() {
        String result = cgen( " { bool x = true || false; } ");
        String test =
                "subi $t1 $sp 2\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                    //true
                    "li $a0 1\n" +
                    "li $t1 1\n" +
                    "beq $a0 $t1 " + labels.get("true") + "\n" +
                    "li $a0 0\n" +
                    labels.get("true") + ":\n" +
                    "push $a0\n" +
                "addi $sp $sp 1\n" +
                "addi $sp $sp 2\n";
        assertEquals(test, result);
    }
}
