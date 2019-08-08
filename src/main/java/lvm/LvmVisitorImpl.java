package lvm;

// ExecuteVM

import lvm.parser.LVMParser;

import static lvm.utils.Registers.*;


public class LvmVisitorImpl {

    public static final int CODESIZE = 10000;
    public static final int MEMSIZE = 1000;

    private final int[] code;
    private final int[] memory = new int[MEMSIZE];

    //registers
    private int ip = 0;
    private int sp = MEMSIZE;
    private int fp = MEMSIZE;
    private int a0 = 0; //ACC
    private int al = 0;
    private int t1 = 0; //TMP
    private int ra;


    public LvmVisitorImpl(int[] code) {
        this.code = code;
    }

    public void cpu() {
        while (true) {
            int bytecode = code[ip++];
            int v1, v2, address, offset;
            String r1, r2;
            switch (bytecode){
                case LVMParser.PUSH:
                    push(GET_REGISTER_VALUE.get(INT_TO_STRING_REGISTER.get(code[ip++])).apply(this));
                    if (sp < 0)
                        System.out.println("Stack Overflow");
                        System.exit(1);
                    break;
                case LVMParser.POP:
                    pop();
                    break;
                case LVMParser.ADD:
                    r1 = INT_TO_STRING_REGISTER.get(code[ip++]);
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


            }
        }

    }

    private int pop() {
        return memory[sp++];
    }

    private void push(int v) {
        memory[--sp] = v;
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
