package linf;

import linf.expression.*;
import linf.statement.*;
import linf.type.BoolType;
import linf.type.IntType;
import linf.type.LinfType;
import linf.parser.ComplexStaticAnalysisBaseVisitor;
import linf.parser.ComplexStaticAnalysisParser;

import java.util.ArrayList;

public class LinfVisitorImpl extends ComplexStaticAnalysisBaseVisitor<BaseNode> {

    @Override
    public BaseNode visitBlock(ComplexStaticAnalysisParser.BlockContext ctx) {
        StmtBlock res = new StmtBlock();
        for (ComplexStaticAnalysisParser.StatementContext sc : ctx.statement()) {
            res.addStmt((Stmt) visit(sc));
        }
        return res;
    }

    @Override
    public BaseNode visitStatement(ComplexStaticAnalysisParser.StatementContext ctx) {
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        } else if (ctx.declaration() != null) {
            return visit(ctx.declaration());
        } else if (ctx.print() != null) {
            return visit(ctx.print());
        } else if (ctx.deletion() != null) {
            return visit(ctx.deletion());
        } else if (ctx.functioncall() != null) {
            return visit(ctx.functioncall());
        } else if (ctx.ifthenelse() != null) {
            return visit(ctx.ifthenelse());
        } else {
            // block
            return visit(ctx.block());
        }
    }

    @Override
    public BaseNode visitAssignment(ComplexStaticAnalysisParser.AssignmentContext ctx) {
        Exp exp = (Exp) visit(ctx.exp());
        return new StmtAssignment(ctx.ID().getText(), exp);
    }

    @Override
    public BaseNode visitDeletion(ComplexStaticAnalysisParser.DeletionContext ctx) {
        return new StmtDeletion(ctx.ID().getText());
    }

    @Override
    public BaseNode visitPrint(ComplexStaticAnalysisParser.PrintContext ctx) {
        return new StmtPrint();
    }

    @Override
    public BaseNode visitIfthenelse(ComplexStaticAnalysisParser.IfthenelseContext ctx) {
        Exp exp = (Exp) visit(ctx.exp());
        ArrayList<StmtBlock> blocks = new ArrayList<>();

        for (ComplexStaticAnalysisParser.BlockContext blk : ctx.block()) {
            blocks.add((StmtBlock) visit(blk));
        }

        return new StmtIfThenElse(exp, blocks.get(0), blocks.get(1));
    }

    @Override
    public BaseNode visitFunctioncall(ComplexStaticAnalysisParser.FunctioncallContext ctx) {
        StmtFunCall res = new StmtFunCall(ctx.ID().getText());

        for (ComplexStaticAnalysisParser.ExpContext ec : ctx.exp()) {
            Exp exp = (Exp) visit(ec);
            res.addPar(exp);
        }

        return res;
    }

    @Override
    public BaseNode visitDeclaration(ComplexStaticAnalysisParser.DeclarationContext ctx) {
        if (ctx.type() != null && ctx.exp() != null) {
            LinfType type = (LinfType) visit(ctx.type());
            Exp exp = (Exp) visit(ctx.exp());

            return new StmtVarDec(type, ctx.ID().getText(), exp);


        } else {
            StmtBlock blk = (StmtBlock) visit(ctx.block());
            StmtFunDec res = new StmtFunDec(ctx.ID().getText(), blk);

            for (ComplexStaticAnalysisParser.ParameterContext pc : ctx.parameter()) {
                res.addPar((Parameter) visit(pc));
            }

            return res;
        }
    }

    @Override
    public BaseNode visitType(ComplexStaticAnalysisParser.TypeContext ctx) {
        String name = ctx.getText();
        if (name.equals("int")) {
            return new IntType();
        } else {
            return new BoolType();
        }
    }

    @Override
    public BaseNode visitParameter(ComplexStaticAnalysisParser.ParameterContext ctx) {
        boolean isVar = ctx.getChild(0).getText().equals("var");
        String id = ctx.ID().getText();
        LinfType type = (LinfType) visit(ctx.type());
        if (isVar) {
            type.setReference();
        }
        return new Parameter(type, id);
    }

    @Override
    public BaseNode visitExp(ComplexStaticAnalysisParser.ExpContext ctx) {
        boolean isNegative = ctx.getChild(0).getText().equals("-");

        Term term = (Term) visit(ctx.left);
        Exp res = new Exp();

        res.setTerm(term);
        res.setNegative(isNegative);

        if (ctx.right != null) {
            Exp right = (Exp) visit(ctx.right);
            String op = ctx.getChild(1).getText();
            res.setOp(op);
            res.setRight(right);
        }

        return res;
    }

    @Override
    public BaseNode visitTerm(ComplexStaticAnalysisParser.TermContext ctx) {
        Factor factor = (Factor) visit(ctx.left);
        Term res = new Term();
        res.setFactor(factor);
        if (ctx.right != null) {
            Term right = (Term) visit(ctx.right);
            String op = ctx.getChild(1).getText();
            res.setOp(op);
            res.setRight(right);
        }

        return res;
    }

    @Override
    public BaseNode visitFactor(ComplexStaticAnalysisParser.FactorContext ctx) {
        Factor res = new Factor();
        Value value = (Value) visit(ctx.left);
        res.setValue(value);
        if (ctx.right != null) {
            Value right = (Value) visit(ctx.right);
            res.setOp(ctx.op.getText());
            res.setRight(right);
        }

        return res;

    }

    @Override
    public BaseNode visitValue(ComplexStaticAnalysisParser.ValueContext ctx) {
        if (ctx.ID() != null) {
            return new IDValue(ctx.ID().getText());
        } else if (ctx.INTEGER() != null) {
            return new IntValue(ctx.INTEGER().getText());
        } else if (ctx.getChild(0).getText().equals("true") || ctx.getChild(0).getText().equals("false")) {
            return new BoolValue(ctx.getChild(0).getText());
        } else {
            return visit(ctx.exp());
        }
    }
}
