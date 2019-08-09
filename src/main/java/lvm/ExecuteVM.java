package lvm;

import lvm.parser.LVMParser;
//import java.util.HashMap;
import static lvm.utils.Registers.*;
import static lvm.utils.Strings.IP;


public class ExecuteVM {

    public static final int CODESIZE = 10000;
    public static final int MEMSIZE = 10000;

    private final int[] code;
    private final int[] memory = new int[MEMSIZE];

    //registers
    private int ip = 0;
    private int sp = MEMSIZE;
    private int fp = MEMSIZE;
    private int a0 = 0; //ACC
    private int al = MEMSIZE;
    private int t1 = 0; //TMP
    private int ra;

    /*
    private int ip = 0;
    private HashMap<String,Integer> registers = new HashMap<String,Integer>(){{
        put("$fp", MEMSIZE);
        put("$al", MEMSIZE);
        put("$ra", 0);
        put("$a0", 0);
        put("$t1", 0);
        put("$sp", MEMSIZE);
    }};
    */

    public ExecuteVM(int[] code) {
        this.code = code;
    }

    public void cpu() {
        while (true) {
            //registers.get(INT_TO_STRING_REGISTER.get(sp))
            if (sp > MEMSIZE || sp <= 0) {
                System.out.println("Error: Out of memory");
            }
            int bytecode = code[ip++]; //fetch
            int v1, v2, address, offset;
            String r1, r2;
            switch (bytecode){
                case LVMParser.PUSH:
                    push(code[ip++]);
                    if (sp < 0)
                        System.out.println("Stack Overflow");
                        System.exit(1);
                    break;
                case LVMParser.POP:
                    pop();
                    break;
                case LVMParser.ADD:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    //v1 = registers.get(INT_TO_STRING_REGISTER.get(code[ip++]));
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    int resAdd = v2 + v1;
                    SET_REGISTER_VALUE.get(r1).apply(this, resAdd);
                    break;
                case LVMParser.SUB:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    int resSub = v2 - v1;
                    SET_REGISTER_VALUE.get(r1).apply(this, resSub);
                    break;
                case LVMParser.MULT:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    int resMult = v2 * v1;
                    SET_REGISTER_VALUE.get(r1).apply(this, resMult);
                    break;
                case LVMParser.DIV:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v1 == 0) {
                        System.out.println("Division by zero detected");
                        System.exit(1);
                    }
                    int resDiv = v2 / v1;
                    SET_REGISTER_VALUE.get(r1).apply(this, resDiv);
                    break;
                case LVMParser.BRANCH:
                    address = code[ip];
                    ip = address;
                    break;
                case LVMParser.BRANCHEQ:
                    address = code[ip++];
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 == v1)
                        ip = address;
                    else ip++;
                    break;
                case LVMParser.BRANCHGREATER:
                    address = code[ip++];
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 > v1)
                        ip = address;
                    else ip++;
                    break;
                case LVMParser.BRANCHGREATEREQ:
                    address = code[ip++];
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 >= v1)
                        ip = address;
                    else ip++;
                    break;
                case LVMParser.BRANCHLESS:
                    address = code[ip++];
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 < v1)
                        ip = address;
                    else ip++;
                    break;
                case LVMParser.BRANCHLESSEQ:
                    address = code[ip++];
                    v1 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    v2 = GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this);
                    if (v2 <= v1)
                        ip = address;
                    else ip++;
                    break;
                case LVMParser.STOREW:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    offset = code[ip++]/4;
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    memory[v1 + offset] = GET_REGISTER_VALUE.get(r1).apply(this);
                    break;
                case LVMParser.LOADW:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    offset = code[ip++]/4;
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    SET_REGISTER_VALUE.get(r1).apply(this, memory[v1 + offset]);
                    break;
                case LVMParser.PRINT:
                    System.out.println((sp < MEMSIZE) ? memory[sp] : "Empty stack!");
                    System.out.println(a0);
                    break;
                case LVMParser.MOVE:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = GET_REGISTER_VALUE.get(r2).apply(this);
                    SET_REGISTER_VALUE.get(r1).apply(this, v1);
                    break;
                case LVMParser.LOADI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v1 = code[ip++];
                    SET_REGISTER_VALUE.get(r1).apply(this, v1);
                    break;
                case LVMParser.TOP:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    SET_REGISTER_VALUE.get(r1).apply(this, memory[sp]);
                    break;
                case LVMParser.JAL:
                    ra = ip + 1;
                    SET_REGISTER_VALUE.get(IP).apply(this, code[ip]);
                    break;
                case LVMParser.JR:
                    SET_REGISTER_VALUE.get(IP).apply(this, ra);
                    break;
                case LVMParser.ADDI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v2 = GET_REGISTER_VALUE.get(r2).apply(this);
                    v1 = code[ip++]/4;
                    SET_REGISTER_VALUE.get(r1).apply(this, v2 + v1);
                    break;
                case LVMParser.SUBI:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    r2 = INT_TO_STRING_REGISTER.get(code[ip++]);
                    v2 = GET_REGISTER_VALUE.get(r2).apply(this);
                    v1 = code[ip++]/4;
                    SET_REGISTER_VALUE.get(r1).apply(this,v2 - v1);
                    break;
                case LVMParser.HALT:
                    System.out.println("Done");
                    return;
            }
        }
    }

    private int pop() {
        int tmp = sp;
        if (sp + 4  <= MEMSIZE) {
            sp = sp + 4;
        } else {
            System.err.println("Out of memory");
            System.exit(1);
        }
        return memory[tmp];
    }

    private void push(int v) {
        if (sp - 4 > 0)
            memory[sp - 4] = v;
         else
            System.out.println("Error: Out of memory");
            System.exit(1);
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
}
