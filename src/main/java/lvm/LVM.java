package lvm;

import lvm.error.StackOverflowError;
import lvm.error.*;
import lvm.parser.LVMLexer;
import lvm.parser.LVMParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import static lvm.utils.Registers.*;


public class LVM {
    public static final int MEMSIZE = 100000000;
    static final int CODESIZE = 10000;

    private final int[] memory = new int[MEMSIZE];
    private final List<String> stdOut = new ArrayList<>();
    private int[] code = new int[CODESIZE];
    //registers
    private int ip = 0;
    private int sp = MEMSIZE - 1;
    private int fp = MEMSIZE - 1;
    private int a0 = 0; //ACC
    private int al = MEMSIZE - 1;
    private int t1 = 0; //TMP
    private int ra;

    public static int[] assemble(String code) {
        LVMLexer lvmLexer = new LVMLexer(CharStreams.fromString(code));
        CommonTokenStream lvmTokens = new CommonTokenStream(lvmLexer);
        LVMParser lvmParser = new LVMParser(lvmTokens);
        LVMVisitorImpl lvmVisitor = new LVMVisitorImpl();
        lvmVisitor.visitProgram(lvmParser.program());
        return lvmVisitor.getCode();
    }

    public void run(int[] program) throws LVMError {
        code = program;
        while (true) {
            if (sp >= MEMSIZE) {
                throw new StackUnderflowError();
            }
            if (sp < 0) {
                throw new StackOverflowError();
            }
            int v1, v2, offset;
            String r1, r2;
            int bytecode = code[ip++];
            switch (bytecode) {
                case LVMParser.PUSH:
                    push(code[ip++]);
                    break;
                case LVMParser.POP:
                    pop();
                    break;
                case LVMParser.ADD:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    int resAdd = v1 + v2;
                    SET_REGISTER_VALUE.get(r1).apply(this, resAdd);
                    break;
                case LVMParser.SUB:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    int resSub = v1 - v2;
                    SET_REGISTER_VALUE.get(r1).apply(this, resSub);
                    break;
                case LVMParser.MULT:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    int index = code[ip++];
                    String name = INT_TO_STRING_REGISTER.get(index);
                    v2 = GET_REGISTER_VALUE.get(name).apply(this);
                    int resMult = v1 * v2;
                    SET_REGISTER_VALUE.get(r1).apply(this, resMult);
                    break;
                case LVMParser.DIV:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 == 0) {
                        throw new DivisionByZeroError();
                    }
                    int resDiv = v1 / v2;
                    SET_REGISTER_VALUE.get(r1).apply(this, resDiv);
                    break;
                case LVMParser.BRANCH:
                    ra = ip + 1;
                    ip = code[ip];
                    break;
                case LVMParser.BRANCHEQ:
                    branchCond((Integer first, Integer second) -> first.intValue() == second.intValue());
                    break;
                case LVMParser.BRANCHGREATER:
                    branchCond((Integer first, Integer second) -> first > second);
                    break;
                case LVMParser.BRANCHGREATEREQ:
                    branchCond((Integer first, Integer second) -> first >= second);
                    break;
                case LVMParser.BRANCHLESS:
                    branchCond((Integer first, Integer second) -> first < second);
                    break;
                case LVMParser.BRANCHLESSEQ:
                    branchCond((Integer first, Integer second) -> first <= second);
                    break;
                case LVMParser.STOREW:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    offset = code[ip++];
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    memory[v1 + offset] = GET_REGISTER_VALUE.get(r1).apply(this);
                    break;
                case LVMParser.LOADW:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    offset = code[ip++];
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    SET_REGISTER_VALUE.get(r1).apply(this, memory[v1 + offset]);
                    break;
                case LVMParser.PRINT:
                    System.out.println(a0);
                    stdOut.add(Integer.toString(a0));
                    break;
                case LVMParser.MOVE:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v2 = GET_REGISTER_VALUE.get(r2).apply(this);
                    SET_REGISTER_VALUE.get(r1).apply(this, v2);
                    break;
                case LVMParser.LOADI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = code[ip++];
                    SET_REGISTER_VALUE.get(r1).apply(this, v1);
                    break;
                case LVMParser.TOP:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    SET_REGISTER_VALUE.get(r1).apply(this, memory[sp + 1]);
                    break;
                case LVMParser.JR:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip]);
                    ip = GET_REGISTER_VALUE.get(r1).apply(this);
                    break;
                case LVMParser.ADDI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v2 = GET_REGISTER_VALUE.get(r2).apply(this);
                    v1 = code[ip++];
                    SET_REGISTER_VALUE.get(r1).apply(this, v2 + v1);
                    break;
                case LVMParser.SUBI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v2 = GET_REGISTER_VALUE.get(r2).apply(this);
                    v1 = code[ip++];
                    SET_REGISTER_VALUE.get(r1).apply(this, v2 - v1);
                    break;
                case LVMParser.HALT:
                    System.out.println("Halting...");
                    return;
                default:
                    throw new IllegalOperator(bytecode);
            }
        }
    }

    private void pop() throws StackUnderflowError {
        if (sp + 1 < MEMSIZE) {
            sp = sp + 1;
        } else {
            throw new StackUnderflowError();
        }
    }

    private void push(int register) throws StackOverflowError {
        if (sp - 1 >= 0) {
            String name = INT_TO_STRING_REGISTER.get(register);
            memory[sp] = GET_REGISTER_VALUE.get(name).apply(this);
            sp = sp - 1;
        } else {
            throw new StackOverflowError();
        }
    }

    private void branchCond(BiPredicate<Integer, Integer> predicate) {
        int v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
        int v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
        int address = code[ip++];
        if (predicate.test(v1, v2)) {
            ip = address;
        }
    }

    public int peekMemory(int location) {
        if (location >= MEMSIZE) {
            throw new IllegalArgumentException();
        }
        if (location < 0) {
            throw new IllegalArgumentException();
        }
        return memory[location];
    }

    public int getIp() {
        return ip;
    }

    public int setIp(int ip) {
        this.ip = ip;
        return ip;
    }

    public int getSp() {
        return sp;
    }

    public int setSp(int sp) {
        this.sp = sp;
        return sp;
    }

    public int getFp() {
        return fp;
    }

    public int setFp(int fp) {
        this.fp = fp;
        return fp;
    }

    public int getA0() {
        return a0;
    }

    public int setA0(int a0) {
        this.a0 = a0;
        return a0;
    }

    public int getAl() {
        return al;
    }

    public int setAl(int al) {
        this.al = al;
        return al;
    }

    public int getT1() {
        return t1;
    }

    public int setT1(int t1) {
        this.t1 = t1;
        return t1;
    }

    public int getRa() {
        return ra;
    }

    public int setRa(int ra) {
        this.ra = ra;
        return ra;
    }

    public List<String> getStdOut() {
        return stdOut;
    }
}
