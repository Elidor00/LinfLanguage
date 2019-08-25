package execution;

import lvm.LVM;
import lvm.error.DivisionByZeroError;
import lvm.error.StackUnderflowError;
import lvm.error.StackOverflowError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static lvm.LVM.MEMSIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static utils.TestUtils.runBytecode;

@RunWith(JUnit4.class)
public class LVMTest {
    @Test
    public void LoadInteger_ShouldWork() {
        LVM vm = runBytecode(
                "li $a0 500"
        );
        assertEquals(500, vm.getA0());
    }

    @Test
    public void Push_ShouldPass_WithEmptyStack() {
        LVM vm = runBytecode(
                "li $a0 500 \n push $a0"
        );
        assertEquals(500, vm.getA0());
        assertEquals(500, vm.peekMemory(vm.getSp() + 1));
    }

    @Test
    public void Push_ShouldFail_WithFullStack() {
        int[] bytecode = LVM.assemble("push $t1");
        LVM vm = new LVM();
        vm.setT1(2000);
        vm.setSp(0);
        assertThrows(StackOverflowError.class, () -> vm.run(bytecode));
    }

    @Test
    public void Pop_ShouldPass_WithNotEmptyStack() {
        LVM vm = runBytecode(
                "li $a0 40 \n push $a0 \n pop"
        );
        assertEquals(40, vm.getA0());

    }

    @Test
    public void Pop_ShouldFail_WithEmptyStack() {
        int[] bytecode = LVM.assemble("pop");
        LVM vm = new LVM();
        assertThrows(StackUnderflowError.class, () -> vm.run(bytecode));
    }

    @Test
    public void Add_ShouldWork() {
        LVM vm = runBytecode(
                "li $a0 56 \n li $t1 467 \n add $ra $a0 $t1"
        );
        assertEquals(56, vm.getA0());
        assertEquals(467, vm.getT1());
        assertEquals(523, vm.getRa());
    }

    @Test
    public void Sub_ShouldWork() {
        LVM vm = runBytecode(
                "li $a0 4600 \n li $t1 47 \n sub $ra $a0 $t1"
        );
        assertEquals(4600, vm.getA0());
        assertEquals(47, vm.getT1());
        assertEquals(4553, vm.getRa());
    }

    @Test
    public void Mult_ShouldWork() {
        LVM vm = runBytecode(
                "li $a0 4600 \n li $t1 47 \n mult $ra $a0 $t1"
        );
        assertEquals(4600, vm.getA0());
        assertEquals(47, vm.getT1());
        assertEquals(216200, vm.getRa());
    }

    @Test
    public void Div_ShouldWork() {
        LVM vm = runBytecode(
                "li $a0 4600 \n li $t1 40 \n div $ra $a0 $t1"
        );
        assertEquals(4600, vm.getA0());
        assertEquals(40, vm.getT1());
        assertEquals(115, vm.getRa());
    }

    @Test
    public void Div_ShouldFail_IfByZero() {
        int[] bytecode = LVM.assemble(
                "li $a0 4600 \n li $t1 0 \n div $ra $a0 $t1"
        );
        LVM vm = new LVM();
        assertThrows(DivisionByZeroError.class, () -> vm.run(bytecode));
    }

    @Test
    public void Branch_ShouldWork() {
        LVM vm = runBytecode(
                "li $a0 50 \n b label \n li $a0 200 \n halt \n label: \n li $a0 42"
        );
        assertEquals(42, vm.getA0());
        assertEquals(5, vm.getRa());
    }

    @Test
    public void BranchEq_ShouldJump_IfEquals() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 50\n" +
                        "beq $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(50, vm.getT1());
        assertEquals(42, vm.getRa());
    }

    @Test
    public void BranchEq_ShouldNotJump_IfNotEquals() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 49\n" +
                        "beq $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(49, vm.getT1());
        assertEquals(200, vm.getRa());
    }

    @Test
    public void BranchGT_ShouldJump_IfGT() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 49\n" +
                        "bgr $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(49, vm.getT1());
        assertEquals(42, vm.getRa());
    }

    @Test
    public void BranchGT_ShouldNotJump_IfNotGT() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 50\n" +
                        "bgr $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(50, vm.getT1());
        assertEquals(200, vm.getRa());
    }

    @Test
    public void BranchGTE_ShouldJump_IfGTE() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 50\n" +
                        "bgre $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(50, vm.getT1());
        assertEquals(42, vm.getRa());
    }

    @Test
    public void BranchGTE_ShouldNotJump_IfNotGTE() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 51\n" +
                        "bgre $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(51, vm.getT1());
        assertEquals(200, vm.getRa());
    }

    @Test
    public void BranchLT_ShouldJump_IfLT() {
        LVM vm = runBytecode(
                "li $a0 49 li $t1 50\n" +
                        "blr $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(49, vm.getA0());
        assertEquals(50, vm.getT1());
        assertEquals(42, vm.getRa());
    }

    @Test
    public void BranchLT_ShouldNotJump_IfNotLT() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 49\n" +
                        "blr $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(49, vm.getT1());
        assertEquals(200, vm.getRa());
    }

    @Test
    public void BranchLTE_ShouldJump_IfLTE() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 50\n" +
                        "blre $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(50, vm.getT1());
        assertEquals(42, vm.getRa());
    }

    @Test
    public void BranchLTE_ShouldNotJump_IfNotLTE() {
        LVM vm = runBytecode(
                "li $a0 50 li $t1 49\n" +
                        "blre $a0 $t1 label \n" +
                        "li $ra 200 halt \n" +
                        "label: \n li $ra 42"
        );
        assertEquals(50, vm.getA0());
        assertEquals(49, vm.getT1());
        assertEquals(200, vm.getRa());
    }

    @Test
    public void StoreW_Should_JustWork() {
        LVM vm = runBytecode(
                "li $a0 576 li $t1 " + (MEMSIZE - 1) + "\n" +
                        "sw $a0 0($t1)\n" +
                        "li $t1 " + 0 + "\n" +
                        "sw $a0 576($t1)\n"
        );
        assertEquals(576, vm.peekMemory(MEMSIZE - 1));
        assertEquals(576, vm.peekMemory(576));
    }

    @Test
    public void LoadW_Should_JustWork() {
        LVM vm = runBytecode(
                "li $a0 576 li $t1 " + (MEMSIZE - 1) + "\n" +
                        "sw $a0 0($t1)\n" +
                        "sw $a0 -576($t1)\n" +
                        "lw $ra 0($t1)\n" +
                        "lw $al -576($t1)\n"
        );
        assertEquals(576, vm.peekMemory(MEMSIZE - 1));
        assertEquals(576, vm.peekMemory(MEMSIZE - 577));
        assertEquals(576, vm.getRa());
        assertEquals(576, vm.getAl());
    }

    @Test
    public void Print_Should_JustWork() {
        LVM vm = runBytecode(
                "li $a0 576\n" +
                        "print\n" +
                        "li $a0 200\n" +
                        "print"
        );
        List<String> out = vm.getStdOut();
        assertEquals("576", out.get(0));
        assertEquals("200", out.get(1));
    }

    @Test
    public void Move_Should_JustWork() {
        LVM vm = runBytecode(
                "li $a0 576\n" +
                        "move $t1 $a0"
        );
        assertEquals(576, vm.getA0());
        assertEquals(576, vm.getT1());
    }

    @Test
    public void Top_Should_JustWork() {
        LVM vm = runBytecode(
                "li $a0 576\n" +
                        "push $a0\n" +
                        "top $t1"
        );
        assertEquals(576, vm.getA0());
        assertEquals(576, vm.peekMemory(MEMSIZE - 1));
        assertEquals(576, vm.getT1());
    }

    @Test
    public void JumpRegister_Should_JustJump() {
        LVM vm = runBytecode(
                "li $ra 14\n" +
                        "jr $ra\n" +
                        "li $ra 34\n" +
                        "li $ra 34\n" +
                        "li $ra 34\n"
        );
        assertEquals(14, vm.getRa());
    }

    @Test
    public void AddI_Should_JustWork() {
        LVM vm = runBytecode("li $a0 200 addi $t1 $a0 200");
        assertEquals(400, vm.getT1());
    }

    @Test
    public void SubI_Should_JustWork() {
        LVM vm = runBytecode("li $a0 200 subi $t1 $a0 200");
        assertEquals(0, vm.getT1());
    }

    @Test
    public void Halt() {
        LVM vm = runBytecode("halt li $sp 200");

    }
}
