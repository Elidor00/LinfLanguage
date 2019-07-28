// Generated from /home/orang3/IdeaProjects/complex-extended-static-analysis/src/main/java/lvm/parser/LVM.g4 by ANTLR 4.7.2
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
     * Enter a parse tree produced by the {@code push}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterPush(LVMParser.PushContext ctx);

    /**
     * Exit a parse tree produced by the {@code push}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitPush(LVMParser.PushContext ctx);

    /**
     * Enter a parse tree produced by the {@code pop}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterPop(LVMParser.PopContext ctx);

    /**
     * Exit a parse tree produced by the {@code pop}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitPop(LVMParser.PopContext ctx);

    /**
     * Enter a parse tree produced by the {@code add}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterAdd(LVMParser.AddContext ctx);

    /**
     * Exit a parse tree produced by the {@code add}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitAdd(LVMParser.AddContext ctx);

    /**
     * Enter a parse tree produced by the {@code addi}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterAddi(LVMParser.AddiContext ctx);

    /**
     * Exit a parse tree produced by the {@code addi}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitAddi(LVMParser.AddiContext ctx);

    /**
     * Enter a parse tree produced by the {@code sub}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterSub(LVMParser.SubContext ctx);

    /**
     * Exit a parse tree produced by the {@code sub}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitSub(LVMParser.SubContext ctx);

    /**
     * Enter a parse tree produced by the {@code subi}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterSubi(LVMParser.SubiContext ctx);

    /**
     * Exit a parse tree produced by the {@code subi}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitSubi(LVMParser.SubiContext ctx);

    /**
     * Enter a parse tree produced by the {@code mult}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterMult(LVMParser.MultContext ctx);

    /**
     * Exit a parse tree produced by the {@code mult}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitMult(LVMParser.MultContext ctx);

    /**
     * Enter a parse tree produced by the {@code div}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterDiv(LVMParser.DivContext ctx);

    /**
     * Exit a parse tree produced by the {@code div}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitDiv(LVMParser.DivContext ctx);

    /**
     * Enter a parse tree produced by the {@code move}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterMove(LVMParser.MoveContext ctx);

    /**
     * Exit a parse tree produced by the {@code move}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitMove(LVMParser.MoveContext ctx);

    /**
     * Enter a parse tree produced by the {@code sw}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterSw(LVMParser.SwContext ctx);

    /**
     * Exit a parse tree produced by the {@code sw}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitSw(LVMParser.SwContext ctx);

    /**
     * Enter a parse tree produced by the {@code lw}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterLw(LVMParser.LwContext ctx);

    /**
     * Exit a parse tree produced by the {@code lw}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitLw(LVMParser.LwContext ctx);

    /**
     * Enter a parse tree produced by the {@code li}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterLi(LVMParser.LiContext ctx);

    /**
     * Exit a parse tree produced by the {@code li}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitLi(LVMParser.LiContext ctx);

    /**
     * Enter a parse tree produced by the {@code label}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterLabel(LVMParser.LabelContext ctx);

    /**
     * Exit a parse tree produced by the {@code label}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitLabel(LVMParser.LabelContext ctx);

    /**
     * Enter a parse tree produced by the {@code b}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterB(LVMParser.BContext ctx);

    /**
     * Exit a parse tree produced by the {@code b}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitB(LVMParser.BContext ctx);

    /**
     * Enter a parse tree produced by the {@code beq}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterBeq(LVMParser.BeqContext ctx);

    /**
     * Exit a parse tree produced by the {@code beq}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitBeq(LVMParser.BeqContext ctx);

    /**
     * Enter a parse tree produced by the {@code blr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterBlr(LVMParser.BlrContext ctx);

    /**
     * Exit a parse tree produced by the {@code blr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitBlr(LVMParser.BlrContext ctx);

    /**
     * Enter a parse tree produced by the {@code blre}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterBlre(LVMParser.BlreContext ctx);

    /**
     * Exit a parse tree produced by the {@code blre}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitBlre(LVMParser.BlreContext ctx);

    /**
     * Enter a parse tree produced by the {@code bgr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterBgr(LVMParser.BgrContext ctx);

    /**
     * Exit a parse tree produced by the {@code bgr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitBgr(LVMParser.BgrContext ctx);

    /**
     * Enter a parse tree produced by the {@code bgre}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterBgre(LVMParser.BgreContext ctx);

    /**
     * Exit a parse tree produced by the {@code bgre}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitBgre(LVMParser.BgreContext ctx);

    /**
     * Enter a parse tree produced by the {@code jal}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterJal(LVMParser.JalContext ctx);

    /**
     * Exit a parse tree produced by the {@code jal}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitJal(LVMParser.JalContext ctx);

    /**
     * Enter a parse tree produced by the {@code jr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterJr(LVMParser.JrContext ctx);

    /**
     * Exit a parse tree produced by the {@code jr}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitJr(LVMParser.JrContext ctx);

    /**
     * Enter a parse tree produced by the {@code print}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterPrint(LVMParser.PrintContext ctx);

    /**
     * Exit a parse tree produced by the {@code print}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitPrint(LVMParser.PrintContext ctx);

    /**
     * Enter a parse tree produced by the {@code top}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterTop(LVMParser.TopContext ctx);

    /**
     * Exit a parse tree produced by the {@code top}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitTop(LVMParser.TopContext ctx);

    /**
     * Enter a parse tree produced by the {@code halt}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void enterHalt(LVMParser.HaltContext ctx);

    /**
     * Exit a parse tree produced by the {@code halt}
     * labeled alternative in {@link LVMParser#instruction}.
     *
     * @param ctx the parse tree
     */
    void exitHalt(LVMParser.HaltContext ctx);
}