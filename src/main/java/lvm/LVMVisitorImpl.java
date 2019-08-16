package lvm;

import lvm.parser.LVMBaseVisitor;
import lvm.parser.LVMParser;
import org.antlr.v4.runtime.Token;

import java.util.HashMap;

import static lvm.utils.Registers.REGISTER_TO_INT;

public class LVMVisitorImpl extends LVMBaseVisitor {

    public final int[] code = new int[LVM.CODESIZE];
    private final HashMap<String, Integer> labelAdd = new HashMap<>();
    private final HashMap<Integer, String> labelRef = new HashMap<>();
    private int i = 0;

    @Override
    public Void visitProgram(LVMParser.ProgramContext ctx) {
        visitChildren(ctx);

        for (Integer refAdd : labelRef.keySet()) {
            code[refAdd] = labelAdd.get(labelRef.get(refAdd));
        }
        return null;
    }

    @Override
    public Void visitInstruction(LVMParser.InstructionContext ctx) {
        switch (ctx.getStart().getType()) {
            case LVMParser.PUSH:
                if (ctx.r1 != null) {
                    code[i++] = LVMParser.PUSH;
                    code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                }
                break;
            case LVMParser.POP:
                code[i++] = LVMParser.POP;
                break;
            case LVMParser.ADD:
                code[i++] = LVMParser.ADD;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r3.getText());
                break;
            case LVMParser.SUB:
                code[i++] = LVMParser.SUB;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r3.getText());
                break;
            case LVMParser.MULT:
                code[i++] = LVMParser.MULT;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r3.getText());
                break;
            case LVMParser.DIV:
                code[i++] = LVMParser.DIV;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r3.getText());
                break;
            case LVMParser.BRANCH:
                code[i++] = LVMParser.BRANCH;
                labelRef.put(i++, checkLabel(ctx.l));
                break;
            case LVMParser.BRANCHEQ:
                code[i++] = LVMParser.BRANCHEQ;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                labelRef.put(i++, checkLabel(ctx.l));
                break;
            case LVMParser.BRANCHGREATER:
                code[i++] = LVMParser.BRANCHGREATER;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                labelRef.put(i++, checkLabel(ctx.l));
                break;
            case LVMParser.BRANCHGREATEREQ:
                code[i++] = LVMParser.BRANCHGREATEREQ;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                labelRef.put(i++, checkLabel(ctx.l));
                break;
            case LVMParser.BRANCHLESS:
                code[i++] = LVMParser.BRANCHLESS;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                labelRef.put(i++, checkLabel(ctx.l));
                break;
            case LVMParser.BRANCHLESSEQ:
                code[i++] = LVMParser.BRANCHLESSEQ;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                labelRef.put(i++, checkLabel(ctx.l));
                break;
            case LVMParser.STOREW:
                code[i++] = LVMParser.STOREW;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = Integer.parseInt(ctx.n.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                break;
            case LVMParser.LOADW:
                code[i++] = LVMParser.LOADW;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = Integer.parseInt(ctx.n.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                break;
            case LVMParser.PRINT:
                code[i++] = LVMParser.PRINT;
                break;
            case LVMParser.LABEL:
                labelAdd.put(ctx.l.getText(), i);
                break;
            case LVMParser.MOVE:
                code[i++] = LVMParser.MOVE;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                break;
            case LVMParser.LOADI:
                code[i++] = LVMParser.LOADI;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = Integer.parseInt(ctx.n.getText());
                break;
            case LVMParser.TOP:
                code[i++] = LVMParser.TOP;
                int reg = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = reg;
                break;
            case LVMParser.JAL:
                code[i++] = LVMParser.JAL;
                labelRef.put(i++, checkLabel(ctx.l));
                break;
            case LVMParser.JR:
                code[i++] = LVMParser.JR;
                break;
            case LVMParser.ADDI:
                code[i++] = LVMParser.ADDI;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                code[i++] = Integer.parseInt(ctx.n.getText());
                break;
            case LVMParser.SUBI:
                code[i++] = LVMParser.SUBI;
                code[i++] = REGISTER_TO_INT.get(ctx.r1.getText());
                code[i++] = REGISTER_TO_INT.get(ctx.r2.getText());
                code[i++] = Integer.parseInt(ctx.n.getText());
                break;
            case LVMParser.HALT:
                code[i++] = LVMParser.HALT;
                break;
        }
        return null;
    }

    private String checkLabel(Token label) {
        String res;
        if (label != null)
            res = label.getText();
        else
            res = null;
        return res;
    }
}
