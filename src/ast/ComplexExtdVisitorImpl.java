package ast;

import parser.ComplexStaticAnalysisBaseVisitor;
import parser.ComplexStaticAnalysisParser;

public class ComplexExtdVisitorImpl extends ComplexStaticAnalysisBaseVisitor<Node> {

    private boolean ifContext = false;

    @Override
    public Node visitBlock(ComplexStaticAnalysisParser.BlockContext ctx) {
        ComplexExtdStmtBlock res = new ComplexExtdStmtBlock();
        for (ComplexStaticAnalysisParser.StatementContext sc : ctx.statement()) {
            res.addStmt((ComplexExtdStmt) visit(sc));
        }
        return res;
    }

    @Override
    public Node visitStatement(ComplexStaticAnalysisParser.StatementContext ctx) {
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
    public Node visitAssignment(ComplexStaticAnalysisParser.AssignmentContext ctx) {
        ComplexExtdExp exp = (ComplexExtdExp) visit(ctx.exp());
        return new ComplexExtdStmtAssignment(ctx.ID().getText(), exp);
    }

    @Override
    public Node visitDeletion(ComplexStaticAnalysisParser.DeletionContext ctx) {
        return new ComplexExtdStmtDeletion(ctx.ID().getText(), ifContext);
    }

    @Override
    public Node visitPrint(ComplexStaticAnalysisParser.PrintContext ctx) {
        return new ComplexExtdStmtPrint();
    }

    @Override
    public Node visitIfthenelse(ComplexStaticAnalysisParser.IfthenelseContext ctx) {
        ifContext = true;

        ComplexExtdStmtIfThenElse res = new ComplexExtdStmtIfThenElse();

        ifContext = false;

        return res;
    }

    @Override
    public Node visitFunctioncall(ComplexStaticAnalysisParser.FunctioncallContext ctx) {
        ComplexExtdStmtFunCall res = new ComplexExtdStmtFunCall(ctx.ID().getText());

        for (ComplexStaticAnalysisParser.ExpContext ec : ctx.exp()) {
            ComplexExtdExp exp = (ComplexExtdExp) visit(ec);
            res.addPar(exp);
        }

        return res;
    }

    @Override
    public Node visitDeclaration(ComplexStaticAnalysisParser.DeclarationContext ctx) {
        if (ctx.type() != null && ctx.exp() != null) {
            ComplexExtdType type = (ComplexExtdType) visit(ctx.type());
            ComplexExtdExp exp = (ComplexExtdExp) visit(ctx.exp());

            return new ComplexExtdStmtVarDec(type, ctx.ID().getText(), exp);


        } else {
            ComplexExtdStmtBlock blk = (ComplexExtdStmtBlock) visit(ctx.block());
            ComplexExtdStmtFunDec res = new ComplexExtdStmtFunDec(ctx.ID().getText(), blk);

            for (ComplexStaticAnalysisParser.ParameterContext pc : ctx.parameter()) {
                res.addPar((ComplexExtdParameter) visit(pc));
            }

            return res;
        }
    }

    @Override
    public Node visitType(ComplexStaticAnalysisParser.TypeContext ctx) {
        String name = ctx.getText();
        if (name.equals("int")) {
            return new ComplexExtdIntType();
        } else {
            return new ComplexExtdBoolType();
        }
    }

    @Override
    public Node visitParameter(ComplexStaticAnalysisParser.ParameterContext ctx) {
        boolean isVar = ctx.getChild(0).getText().equals("var");
        ComplexExtdType type = (ComplexExtdType) visit(ctx.type());
        type.setRef(isVar);
        return new ComplexExtdParameter(type, ctx.ID().getText());
    }

    @Override
    public Node visitExp(ComplexStaticAnalysisParser.ExpContext ctx) {
        boolean isNegative = ctx.getChild(0).getText().equals("-");

        ComplexExtdTerm term = (ComplexExtdTerm) visit(ctx.left);
        ComplexExtdExp res = new ComplexExtdExp();

        res.setTerm(term);
        res.setNegative(isNegative);

        res.setValueType();

        if (ctx.right != null) {
            ComplexExtdExp right = (ComplexExtdExp) visit(ctx.right);
            String op = ctx.getChild(2).getChild(0).getText();
            res.setOp(op);
            res.setRight(right);
        }

        return res;
    }

    @Override
    public Node visitTerm(ComplexStaticAnalysisParser.TermContext ctx) {
        ComplexExtdFactor factor = (ComplexExtdFactor) visit(ctx.left);
        ComplexExtdTerm res = new ComplexExtdTerm();
        res.setFactor(factor);
        if (ctx.right != null) {
            ComplexExtdTerm right = (ComplexExtdTerm) visit(ctx.right);
            String op = ctx.getChild(2).getChild(0).getText();
            res.setOp(op);
            res.setRight(right);
        }

        return res;
    }

    @Override
    public Node visitFactor(ComplexStaticAnalysisParser.FactorContext ctx) {
        ComplexExtdValue value = (ComplexExtdValue) visit(ctx.left);
        ComplexExtdFactor res = new ComplexExtdFactor();
        res.setValue(value);
        if (ctx.right != null) {
            ComplexExtdValue right = (ComplexExtdValue) visit(ctx.right);
            res.setOp(ctx.op.getText());
            res.setRight(right);
        }

        return res;
    }

    @Override
    public Node visitValue(ComplexStaticAnalysisParser.ValueContext ctx) {
        if (ctx.ID() != null) {
            ComplexExtdValue res = new ComplexExtdValue(ctx.ID().getText());
            res.setID(true);
            return res;
        } else if (ctx.INTEGER() != null) {
            return new ComplexExtdValue(ctx.INTEGER().getText());
        } else if (ctx.getChild(0).getText().equals("true") || ctx.getChild(0).getText().equals("false")) {
            return new ComplexExtdValue(ctx.getChild(0).getText());
        } else {
            return visit(ctx.exp());
        }
    }
}
