// Generated from /home/orang3/IdeaProjects/complex-extended-static-analysis/src/main/java/lvm/parser/LVM.g4 by ANTLR 4.7.2
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
     * Visit a parse tree produced by the {@code push}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPush(LVMParser.PushContext ctx);

    /**
     * Visit a parse tree produced by the {@code pop}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPop(LVMParser.PopContext ctx);

    /**
     * Visit a parse tree produced by the {@code add}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAdd(LVMParser.AddContext ctx);

    /**
     * Visit a parse tree produced by the {@code addi}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAddi(LVMParser.AddiContext ctx);

    /**
     * Visit a parse tree produced by the {@code sub}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSub(LVMParser.SubContext ctx);

    /**
     * Visit a parse tree produced by the {@code subi}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubi(LVMParser.SubiContext ctx);

    /**
     * Visit a parse tree produced by the {@code mult}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMult(LVMParser.MultContext ctx);

    /**
     * Visit a parse tree produced by the {@code div}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDiv(LVMParser.DivContext ctx);

    /**
     * Visit a parse tree produced by the {@code move}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMove(LVMParser.MoveContext ctx);

    /**
     * Visit a parse tree produced by the {@code sw}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSw(LVMParser.SwContext ctx);

    /**
     * Visit a parse tree produced by the {@code lw}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLw(LVMParser.LwContext ctx);

    /**
     * Visit a parse tree produced by the {@code li}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLi(LVMParser.LiContext ctx);

    /**
     * Visit a parse tree produced by the {@code label}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLabel(LVMParser.LabelContext ctx);

    /**
     * Visit a parse tree produced by the {@code b}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitB(LVMParser.BContext ctx);

    /**
     * Visit a parse tree produced by the {@code beq}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBeq(LVMParser.BeqContext ctx);

    /**
     * Visit a parse tree produced by the {@code blr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlr(LVMParser.BlrContext ctx);

    /**
     * Visit a parse tree produced by the {@code blre}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlre(LVMParser.BlreContext ctx);

    /**
     * Visit a parse tree produced by the {@code bgr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBgr(LVMParser.BgrContext ctx);

    /**
     * Visit a parse tree produced by the {@code bgre}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBgre(LVMParser.BgreContext ctx);

    /**
     * Visit a parse tree produced by the {@code jal}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitJal(LVMParser.JalContext ctx);

    /**
     * Visit a parse tree produced by the {@code jr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitJr(LVMParser.JrContext ctx);

    /**
     * Visit a parse tree produced by the {@code print}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrint(LVMParser.PrintContext ctx);

    /**
     * Visit a parse tree produced by the {@code top}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTop(LVMParser.TopContext ctx);

    /**
     * Visit a parse tree produced by the {@code halt}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitHalt(LVMParser.HaltContext ctx);
}