import lvm.LVM;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import static lvm.LVM.MEMSIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static utils.TestUtils.runScript;

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
        assertEquals("65533", out.get(0));
        assertEquals(65533, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void factorial() {
        String fact = getCode("factorial.lnf");
        LVM vm = runScript(fact);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("3628800", out.get(0));
        assertEquals(3628800, vm.peekMemory(MEMSIZE - 3));
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
        assertEquals(13, vm.peekMemory(MEMSIZE - 3));
    }

    @Test
    public void hofstadter() {
        String hof = getCode("Hofstadter_FM.lnf");
        LVM vm = runScript(hof);
        assertNotNull(vm);

        List<String> out = vm.getStdOut();
        assertEquals(2, out.size());
        assertEquals("8", out.get(0));
        assertEquals("7", out.get(1));
        assertEquals(7, vm.peekMemory(MEMSIZE - 3));
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
        assertEquals(0, vm.peekMemory(MEMSIZE - 3));
    }
}
