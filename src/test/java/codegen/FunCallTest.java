package codegen;


import linf.utils.LinfLib;
import lvm.LVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static lvm.LVM.MEMSIZE;
import static org.junit.Assert.assertEquals;
import static utils.TestUtils.cgen;
import static utils.TestUtils.runBytecode;

@RunWith(JUnit4.class)
public class FunCallTest {
    @Before
    public void resetLabels() {
        LinfLib.reset();
    }

    @Test
    public void Simple_FunCall_Should_JustWork() {
        String actual = cgen("{ f() { print 0; } f(); }");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "print\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel0\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("0", out.get(0));

    }

    @Test
    public void Simple_NotLocal_FunCall_Should_JustWork() {
        String actual = cgen("{ f() { print 0; } { f(); } print 5; }");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "print\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Inner block
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                // Prepare stack for call
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 2\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "li $a0 5\n" +
                "print\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(2, out.size());
        assertEquals("0", out.get(0));
        assertEquals("5", out.get(1));
    }

    @Test
    public void Unary_FunCall_Should_JustWork() {
        String actual = cgen("{ f(int x){ print x; } f(5); }");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                "lw $a0 4($fp)\n" +
                "print\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                "li $a0 5\n" +
                "push $a0\n" +
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel0\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("5", out.get(0));
    }

    @Test
    public void FunCall_WithComplexExp_ShouldJustWork(){
        String actual = cgen("{\n" +
                "fun(int y, int x, var int out) {\n" +
                "if(y == 0) then {\n" +
                "out = x;\n" +
                "}else{\n" +
                "fun(y - 1, x, out);\n" +
                "}\n"+
                "}\n" +
                "int out = 0;\n" +
                "int x = 7;\n" +
                "fun(10," +
                "x * x * (23984 / (27 - 54 * 8)) + x * (x - 1) ," +
                "out);\n" +
                "print out;\n"+
                "}");

        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("-2849", out.get(0));
        assertEquals(-2849, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void Binary_FunCall_Should_JustWork() {
        String actual = cgen("{ int k = 0; int r = 1;\n" +
                "f(int x, int y){\n" +
                "int t = x; x = y; y = t;\n" +
                "print x; print y; }\n" +
                "f(k,r);\n" +
                "print k; print r; }");

        String expected = "subi $t1 $sp 3\n" + // Root block
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // k declaration
                "li $a0 0\n" +
                "push $a0\n" +
                // r declaration
                "li $a0 1\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +
                // x assignment
                "lw $a0 4($fp)\n" + // <----------------------
                "push $a0\n" +
                "lw $a0 5($fp)\n" +
                "sw $a0 4($fp)\n" +
                "lw $a0 0($fp)\n" +
                "sw $a0 5($fp)\n" +
                "lw $a0 4($fp)\n" +
                "print\n" +
                // print y
                "lw $a0 5($fp)\n" + // <----------------------
                "print\n" +
                // pop t
                "addi $sp $sp 1\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Fun call
                // Push arguments
                // Push r
                "lw $a0 -1($fp)\n" + // <----------------------
                "push $a0\n" +
                // push k
                "lw $a0 0($fp)\n" + // <----------------------
                "push $a0\n" +
                // Push control link
                "push $fp\n" +
                // push access link
                "push $fp\n" +
                "b fLabel0\n" +
                // pop access link and parameters
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 4\n" +
                // print k
                "lw $a0 0($fp)\n" + // <----------------------
                "print\n" +
                // print r
                "lw $a0 -1($fp)\n" + // <----------------------
                "print\n" +
                "addi $sp $sp 2\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(4, out.size());
        assertEquals("1", out.get(0));
        assertEquals("0", out.get(1));
        assertEquals("0", out.get(2));
        assertEquals("1", out.get(3));
        assertEquals(0, vm.peekMemory(MEMSIZE - 4));
        assertEquals(1, vm.peekMemory(MEMSIZE - 5));
    }

    @Test
    public void Unary_Var_FunCall_Should_JustWork() {
        String actual = cgen("{ int k = 50; f(var int x){ print x; x = 60; } f(k); }");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                "li $a0 50\n" +
                "push $a0\n" +
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // AR
                "move $fp $sp\n" +
                // print
                "lw $al 4($fp)\n" +
                "lw $a0 0($al)\n" +
                "print\n" +
                // x assignment
                "li $a0 60\n" +
                "lw $al 4($fp)\n" +
                "sw $a0 0($al)\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Prepare stack for call
                // Args
                "move $a0 $fp\n" +
                "push $a0\n" +
                // Control link
                "push $fp\n" +
                // Access link
                "push $fp\n" +
                "b fLabel0\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("50", out.get(0));
        assertEquals(60, vm.peekMemory(MEMSIZE - 4));
        assertEquals(MEMSIZE - 4, vm.peekMemory(MEMSIZE - 5));
    }

    @Test
    public void Binary_VarFunCall_Should_JustWork() {
        String actual = cgen("{ int k = 0; int r = 1;\n" +
                "f(var int x, var int y){\n" +
                "int t = x; x = y; y = t;}\n" +
                "f(k,r);\n" +
                "print k; print r; }");

        String expected = "subi $t1 $sp 3\n" + // Root block
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // k declaration
                "li $a0 0\n" +
                "push $a0\n" +
                // r declaration
                "li $a0 1\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                // block
                "move $fp $sp\n" +

                // t declaration
                "lw $al 4($fp)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +

                // x assignment
                "lw $al 5($fp)\n" +
                "lw $a0 0($al)\n" +
                "lw $al 4($fp)\n" +
                "sw $a0 0($al)\n" +

                // y assignment
                "lw $a0 0($fp)\n" +
                "lw $al 5($fp)\n" +
                "sw $a0 0($al)\n" +

                // pop t
                "addi $sp $sp 1\n" +
                // return control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // Fun call
                // Push arguments
                // Push r
                "move $a0 $fp\n" +
                "addi $a0 $a0 -1\n" +
                "push $a0\n" +
                // push k
                "move $a0 $fp\n" +
                "push $a0\n" +
                // Push control link
                "push $fp\n" +
                // push access link
                "push $fp\n" +
                "b fLabel0\n" +
                // pop access link and parameters
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 4\n" +
                // print k
                "lw $a0 0($fp)\n" + // <----------------------
                "print\n" +
                // print r
                "lw $a0 -1($fp)\n" + // <----------------------
                "print\n" +
                "addi $sp $sp 2\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(2, out.size());
        assertEquals("1", out.get(0));
        assertEquals("0", out.get(1));
        assertEquals(1, vm.peekMemory(MEMSIZE - 4));
        assertEquals(0, vm.peekMemory(MEMSIZE - 5));
    }

    @Test
    public void NestedCall_Should_JustWork() {
        String actual = cgen("{ int k = 900; f(int x){ g(){ print x; } g(); } f(k); }");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // k declaration
                "li $a0 900\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel1:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +
                // g declaration
                "b label1\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +

                // print x
                "lw $al 2($fp)\n" +
                "lw $a0 4($al)\n" +
                "print\n" +

                // g returns control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label1:\n" +
                // g call
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel0\n" +
                // restore f's control link
                "lw $fp 2($sp)\n" +
                // pops control link and access link
                "addi $sp $sp 2\n" +
                // pop f's return address
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // f call
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("900", out.get(0));
        // k variable
        assertEquals(900, vm.peekMemory(MEMSIZE - 4));
        // x parameter
        assertEquals(900, vm.peekMemory(MEMSIZE - 5));
    }

    @Test
    public void NestedCall_WithVarParameter_Should_JustWork() {
        String actual = cgen("{ int k = 900;\n" +
                "f(var int x){\n" +
                "g(var int x){ x = 3; print k; x = 0; }\n" +
                "g(x); }\n" +
                "f(k); }");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // k declaration
                "li $a0 900\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel1:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +
                // g declaration
                "b label1\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +

                // var parameter assignment
                "li $a0 3\n" +
                "lw $al 4($fp)\n" +
                "sw $a0 0($al)\n" +

                // print k
                "lw $al 2($fp)\n" +
                "lw $al 2($al)\n" +
                "lw $a0 0($al)\n" +
                "print\n" +

                // var parameter assignment
                "li $a0 0\n" +
                "lw $al 4($fp)\n" +
                "sw $a0 0($al)\n" +

                // g returns control
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label1:\n" +

                // g parameters
                "lw $al 4($fp)\n" +
                "move $a0 $al\n" +
                "push $a0\n" +

                // g call
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel0\n" +
                // restore caller $fp
                "lw $fp 2($sp)\n" +
                // pop f's access link, control link and parameters
                "addi $sp $sp 3\n" +
                // pop f's return address
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // f call
                "move $a0 $fp\n" +
                "push $a0\n" +
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("3", out.get(0));
        assertEquals(0, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void RecursiveCall_Should_JustWork() {
        String actual = cgen("{ int k = 9;\n" +
                "f(int x){\n" +
                "if (x == 0) then {\n" +
                "print x;\n" +
                "} else {\n" +
                "print x; f(x-1);\n" +
                "}}\n" +
                "f(k);}");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // k declaration
                "li $a0 9\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +

                // branch condition
                "lw $a0 4($fp)\n" +
                "push $a0\n" +
                "li $a0 0\n" +
                "top $t1\n" +
                "pop\n" +
                "beq $t1 $a0 label4\n" +
                "li $a0 0\n" +
                "b label3\n" +
                // true
                "label4:\n" +
                "li $a0 1\n" +

                // false
                "label3:\n" +


                "li $t1 0\n" +
                "beq $a0 $t1 label2\n" +

                // then branch
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $al 2($fp)\n" +
                "lw $a0 4($al)\n" +
                "print\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "b label1\n" +

                // else branch
                "label2:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +

                // print x
                "lw $al 2($fp)\n" +
                "lw $a0 4($al)\n" +
                "print\n" +

                // compute x - 1
                "lw $al 2($fp)\n" +
                "lw $a0 4($al)\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "top $t1\n" +
                "pop\n" +
                "sub $a0 $t1 $a0\n" +
                "push $a0\n" +

                // push control link
                "push $fp\n" +

                // push access link
                "lw $al 2($fp)\n" +
                "lw $al 2($al)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                // restore caller's $fp
                "lw $fp 2($sp)\n" +
                // pop f's access link, control link and parameters
                "addi $sp $sp 3\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "label1:\n" +
                // pop f's return address
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // f call
                "lw $a0 0($fp)\n" +
                "push $a0\n" +
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel0\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                // end block
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(10, out.size());
        assertEquals("9", out.get(0));
        assertEquals("8", out.get(1));
        assertEquals("7", out.get(2));
        assertEquals("6", out.get(3));
        assertEquals("5", out.get(4));
        assertEquals("4", out.get(5));
        assertEquals("3", out.get(6));
        assertEquals("2", out.get(7));
        assertEquals("1", out.get(8));
        assertEquals("0", out.get(9));
        assertEquals(9, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void RecursiveCall_VarParameter_Should_JustWork() {
        String actual = cgen("{ int k = 9;\n" +
                "f(var int x){\n" +
                "if (x == 0) then {\n" +
                "print x;\n" +
                "} else {\n" +
                "print x; x = x-1; f(x);\n" +
                "}}\n" +
                "f(k); print k;}");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +
                // k declaration
                "li $a0 9\n" +
                "push $a0\n" +
                // f declaration
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +

                // branch condition
                "lw $al 4($fp)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +
                "li $a0 0\n" +
                "top $t1\n" +
                "pop\n" +
                "beq $t1 $a0 label4\n" +
                "li $a0 0\n" +
                "b label3\n" +
                // true
                "label4:\n" +
                "li $a0 1\n" +

                // false
                "label3:\n" +


                "li $t1 0\n" +
                "beq $a0 $t1 label2\n" +

                // then branch
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $al 2($fp)\n" +
                "lw $al 4($al)\n" +
                "lw $a0 0($al)\n" +
                "print\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "b label1\n" +

                // else branch
                "label2:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +

                // print x
                "lw $al 2($fp)\n" +
                "lw $al 4($al)\n" +
                "lw $a0 0($al)\n" +
                "print\n" +

                // x = x - 1
                "lw $al 2($fp)\n" +
                "lw $al 4($al)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "top $t1\n" +
                "pop\n" +
                "sub $a0 $t1 $a0\n" +
                "lw $al 2($fp)\n" +
                "lw $al 4($al)\n" +
                "sw $a0 0($al)\n" +
                "lw $al 2($fp)\n" +
                "lw $al 4($al)\n" +
                "move $a0 $al\n" +

                "push $a0\n" +

                // push control link
                "push $fp\n" +

                // push access link
                "lw $al 2($fp)\n" +
                "lw $al 2($al)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                // restore caller's $fp
                "lw $fp 2($sp)\n" +
                // pop f's access link, control link and parameters
                "addi $sp $sp 3\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "label1:\n" +
                // pop f's return address
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +
                // f call
                "move $a0 $fp\n" +
                "push $a0\n" +
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel0\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +

                // print k
                "lw $a0 0($fp)\n" +
                "print\n"+

                // end block
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(11, out.size());
        assertEquals("9", out.get(0));
        assertEquals("8", out.get(1));
        assertEquals("7", out.get(2));
        assertEquals("6", out.get(3));
        assertEquals("5", out.get(4));
        assertEquals("4", out.get(5));
        assertEquals("3", out.get(6));
        assertEquals("2", out.get(7));
        assertEquals("1", out.get(8));
        assertEquals("0", out.get(9));
        assertEquals("0", out.get(10));
        assertEquals(0, vm.peekMemory(MEMSIZE - 4));
    }

    @Test
    public void MutuallyRecursiveCall_Should_JustWork() {
        String actual = cgen("{ bool out = false;\n" +
                "isEven(int n, var bool out);\n" +
                "isOdd(int n, var bool out);\n" +
                "isEven(int n, var bool out) {\n" +
                "    if (n == 0) then {\n" +
                "        out = true;\n" +
                "    } else {\n" +
                "        isOdd(n - 1, out);}\n" +
                "}\n" +
                "isOdd(int n, var bool out) {\n" +
                "    if (n == 0) then {\n" +
                "        out = false;\n" +
                "    } else {\n" +
                "        isEven(n - 1, out);}}\n" +
                "isEven(4, out);\n" +
                "print out;\n" +
                "}");
        String expected = "subi $t1 $sp 3\n" +
                "push $t1\n" +
                "push $t1\n" +
                "push $t1\n" +
                "move $fp $sp\n" +

                // out Declaration
                "li $a0 0\n" +
                "push $a0\n" +

                // isEven Declaration
                "b label0\n" +
                "fLabel0:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +

                // n == 0
                "lw $a0 4($fp)\n" +
                "push $a0\n" +
                "li $a0 0\n" +
                "top $t1\n" +
                "pop\n" +
                "beq $t1 $a0 label4\n" +
                "li $a0 0\n" +
                "b label3\n" +
                "label4:\n" +
                "li $a0 1\n" +
                "label3:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label2\n" +

                // then branch
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +

                // out = true
                "li $a0 1\n" +
                "lw $al 2($fp)\n" +
                "lw $al 5($al)\n" +
                "sw $a0 0($al)\n" + // <----------

                // then branch - end
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "b label1\n" +

                // else branch
                "label2:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +

                // isOdd call
                // out
                "lw $al 2($fp)\n" +
                "lw $al 5($al)\n" +
                "move $a0 $al\n" +
                "push $a0\n" +

                // n - 1
                "lw $al 2($fp)\n" +
                "lw $a0 4($al)\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "top $t1\n" +
                "pop\n" +
                "sub $a0 $t1 $a0\n" +
                "push $a0\n" +

                // control link
                "push $fp\n" +

                // access link
                "lw $al 2($fp)\n" +
                "lw $al 2($al)\n" +
                "push $al\n" +
                "b fLabel1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 4\n" +

                // else branch - end
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "label1:\n" +

                // isEven dec - end
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label0:\n" +

                // isOdd Declaration
                "b label5\n" +
                "fLabel1:\n" +
                "push $ra\n" +
                "move $fp $sp\n" +

                // n == 0
                "lw $a0 4($fp)\n" +
                "push $a0\n" +
                "li $a0 0\n" +
                "top $t1\n" +
                "pop\n" +
                "beq $t1 $a0 label9\n" +
                "li $a0 0\n" +
                "b label8\n" +
                "label9:\n" +
                "li $a0 1\n" +
                "label8:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 label7\n" +

                // then branch
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +

                // out = false
                "li $a0 0\n" +
                "lw $al 2($fp)\n" +
                "lw $al 5($al)\n" +
                "sw $a0 5($al)\n" +  // <----------

                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "b label6\n" +
                "label7:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "lw $al 2($fp)\n" +
                "lw $al 5($al)\n" +
                "move $a0 $al\n" +
                "push $a0\n" +
                "lw $al 2($fp)\n" +
                "lw $a0 4($al)\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "top $t1\n" +
                "pop\n" +
                "sub $a0 $t1 $a0\n" +
                "push $a0\n" +
                "push $fp\n" +
                "lw $al 2($fp)\n" +
                "lw $al 2($al)\n" +
                "push $al\n" +
                "b fLabel0\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 4\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n" +
                "label6:\n" +
                "top $ra\n" +
                "pop\n" +
                "jr $ra\n" +
                "label5:\n" +
                "move $a0 $fp\n" +
                "push $a0\n" +
                "li $a0 4\n" +
                "push $a0\n" +
                "push $fp\n" +
                "push $fp\n" +
                "b fLabel0\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 4\n" +
                "lw $a0 0($fp)\n" +
                "print\n" +
                "addi $sp $sp 1\n" +
                "lw $fp 2($sp)\n" +
                "addi $sp $sp 3\n";
        assertEquals(expected, actual);
        LVM vm = runBytecode(actual);
        List<String> out = vm.getStdOut();
        assertEquals(1, out.size());
        assertEquals("1", out.get(0));
        assertEquals(1, vm.peekMemory(MEMSIZE - 4));
        assertEquals(1, vm.getA0());
    }
}
