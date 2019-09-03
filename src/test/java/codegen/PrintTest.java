package codegen;

import lvm.LVM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static lvm.LVM.MEMSIZE;
import static org.junit.Assert.assertEquals;
import static utils.TestUtils.cgen;
import static utils.TestUtils.runBytecode;

@RunWith(JUnit4.class)
public class PrintTest {

    @Test
    public void printVarDec() {
        String result = cgen(" { int y = 3; print y; } ");
        String test =
                "subi $t1 $sp 3\n" +
                        "push $t1\n" +
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
                        "addi $sp $sp 3\n";
        assertEquals(test, result);
        LVM vm = runBytecode(result);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("3", out.get(0));
        assertEquals(3, vm.peekMemory(MEMSIZE - 4));
    }
}
