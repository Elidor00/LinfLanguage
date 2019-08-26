// Generated from /home/filippo/Project/complex-extended-static-analysis/src/main/java/lvm/parser/LVM.g4 by ANTLR 4.7.2
package lvm.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LVMParser}.
 */
public interface LVMListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link LVMParser#program}.
     *
     * @param ctx the parse tree
     */
    void enterProgram(LVMParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link LVMParser#program}.
     *
     * @param ctx the parse tree
     */
    void exitProgram(LVMParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterInstruction(LVMParser.InstructionContext ctx);

    /**
     * Exit a parse tree produced by {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitInstruction(LVMParser.InstructionContext ctx);
}