// Generated from /home/filippo/Project/complex-extended-static-analysis/src/main/java/lvm/parser/LVM.g4 by ANTLR 4.7.2
package lvm.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LVMParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface LVMVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link LVMParser#program}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProgram(LVMParser.ProgramContext ctx);

    /**
     * Visit a parse tree produced by {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstruction(LVMParser.InstructionContext ctx);
}