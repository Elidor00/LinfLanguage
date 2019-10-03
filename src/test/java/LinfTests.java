import linf.error.type.IncompatibleBehaviourError;
import lvm.LVM;
import lvm.error.StackOverflowError;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import static lvm.LVM.MEMSIZE;
import static org.junit.Assert.*;
import static utils.TestUtils.*;

@RunWith(JUnit4.class)
public class LinfTests {

    private static final String path = "src/test/res/";

    private static String getCode(String fileName) {
        try {
            return CharStreams.fromFileName(path + fileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void ackermann() {
        String ack = getCode("Ackermann.lnf");
        LVM vm = runScript(ack);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("4", out.get(0));
        assertEquals(4, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void factorial() {
        String fact = getCode("factorial.lnf");
        LVM vm = runScript(fact);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("3628800", out.get(0));
        assertEquals(1, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void fibonacci() {
        String fib = getCode("fibonacci.lnf");
        LVM vm = runScript(fib);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(3, out.size());
        assertEquals("0", out.get(0));
        assertEquals("1", out.get(1));
        assertEquals("13", out.get(2));
        assertEquals(13, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void pingPong() {
        /*String ping = getCode("pingPong.lnf");
        System.out.println(cgen(ping));
        LVM vm = runScript(ping);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(2, out.size());
        assertEquals("8", out.get(0));
        assertEquals("7", out.get(1));
        assertEquals(7, vm.peekMemory(MEMSIZE - 4));*/
    }

    @Test
    public void isEven() {
        String evenOdd = getCode("isEven_isOdd.lnf");
        LVM vm = runScript(evenOdd);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(2, out.size());
        assertEquals("1", out.get(0));
        assertEquals("0", out.get(1));
        assertEquals(0, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void test1() {
        String t1 = getCode("test1.lnf");
        LVM vm = runScript(t1);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("1", out.get(0));
        assertEquals(1, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void test2() {
        String t2 = getCode("test2.lnf");
        assertThrows(IncompatibleBehaviourError.class, () -> checkType(t2));
    }

    @Test
    public void test3() {
        String t3 = getCode("test3.lnf");
        LVM vm = runScript(t3);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("9", out.get(0));
    }

    @Test
    public void test4() {
        String t4 = getCode("test4.lnf");
        int[] bytecode = LVM.assemble(cgen(t4));
        LVM vm = new LVM();
        assertThrows(StackOverflowError.class, () -> vm.run(bytecode));
    }
}
