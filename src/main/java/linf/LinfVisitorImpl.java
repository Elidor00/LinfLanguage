package linf;

import linf.expression.*;
import linf.parser.ComplexStaticAnalysisBaseVisitor;
import linf.parser.ComplexStaticAnalysisParser;
import linf.statement.*;
import linf.type.BoolType;
import linf.type.IntType;
import linf.type.LinfType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinfVisitorImpl extends ComplexStaticAnalysisBaseVisitor<Node> {

    @Override
    public Block visitBlock(ComplexStaticAnalysisParser.BlockContext ctx) {
        List<LinfStmt> stmts = new ArrayList<>();
        for (ComplexStaticAnalysisParser.StatementContext sc : ctx.statement()) {
            stmts.add((LinfStmt) visit(sc));
        }
        return new Block(stmts);
    }

    @Override
    public LinfStmt visitStatement(ComplexStaticAnalysisParser.StatementContext ctx) {
        if (ctx.assignment() != null) {
            return (LinfStmt) visit(ctx.assignment());
        } else if (ctx.declaration() != null) {
            return (LinfStmt) visit(ctx.declaration());
        } else if (ctx.print() != null) {
            return (LinfStmt) visit(ctx.print());
        } else if (ctx.deletion() != null) {
            return (LinfStmt) visit(ctx.deletion());
        } else if (ctx.functioncall() != null) {
            return (LinfStmt) visit(ctx.functioncall());
        } else if (ctx.ifthenelse() != null) {
            return (LinfStmt) visit(ctx.ifthenelse());
        } else {
            // block
            return (LinfStmt) visit(ctx.block());
        }
    }

    @Override
    public Assignment visitAssignment(ComplexStaticAnalysisParser.AssignmentContext ctx) {
        Exp exp = (Exp) visit(ctx.exp());
        return new Assignment(ctx.ID().getText(), exp);
    }

    @Override
    public Deletion visitDeletion(ComplexStaticAnalysisParser.DeletionContext ctx) {
        return new Deletion(ctx.ID().getText());
    }

    @Override
    public Print visitPrint(ComplexStaticAnalysisParser.PrintContext ctx) {
        return new Print(visitExp(ctx.exp()));
    }

    @Override
    public IfThenElse visitIfthenelse(ComplexStaticAnalysisParser.IfthenelseContext ctx) {
        Exp exp = (Exp) visit(ctx.exp());
        ArrayList<Block> blocks = new ArrayList<>();

        for (ComplexStaticAnalysisParser.BlockContext blk : ctx.block()) {
            blocks.add((Block) visit(blk));
        }

        return new IfThenElse(exp, blocks.get(0), blocks.get(1));
    }

    @Override
    public FunCall visitFunctioncall(ComplexStaticAnalysisParser.FunctioncallContext ctx) {
        FunCall res = new FunCall(ctx.ID().getText());

        for (ComplexStaticAnalysisParser.ExpContext ec : ctx.exp()) {
            Exp exp = (Exp) visit(ec);
            res.addPar(exp);
        }

        return res;
    }

    @Override
    public StmtDec visitDeclaration(ComplexStaticAnalysisParser.DeclarationContext ctx) {
        if (ctx.type() != null && ctx.exp() != null) {
            LinfType type = (LinfType) visit(ctx.type());
            Exp exp = (Exp) visit(ctx.exp());
            return new VarDec(type, ctx.ID().getText(), exp);
        } else {
            List<Parameter> parameters = ctx.parameter()
                    .stream()
                    .map((child) -> (Parameter) visit(child))
                    .collect(Collectors.toList());
            String id = ctx.ID().getText();
            if (ctx.block() != null) {
                Block blk = (Block) visit(ctx.block());
                blk.setAR();
                return new FunDec(id, parameters, blk);
            } else {
                return new FunPrototype(id, parameters);
            }
        }
    }

    @Override
    public LinfType visitType(ComplexStaticAnalysisParser.TypeContext ctx) {
        String name = ctx.getText();
        if (name.equals("int")) {
            return new IntType();
        } else {
            return new BoolType();
        }
    }

    @Override
    public Parameter visitParameter(ComplexStaticAnalysisParser.ParameterContext ctx) {
        boolean isVar = ctx.getChild(0).getText().equals("var");
        String id = ctx.ID().getText();
        LinfType type = (LinfType) visit(ctx.type());
        if (isVar) {
            type.setReference();
        }
        return new Parameter(type, id);
    }

    @Override
    public Exp visitExp(ComplexStaticAnalysisParser.ExpContext ctx) {
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
    public Term visitTerm(ComplexStaticAnalysisParser.TermContext ctx) {
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
    public Factor visitFactor(ComplexStaticAnalysisParser.FactorContext ctx) {
        Factor res = new Factor();
        LinfValue value = (LinfValue) visit(ctx.left);
        res.setValue(value);
        if (ctx.right != null) {
            LinfValue right = (LinfValue) visit(ctx.right);
            res.setOp(ctx.op.getText());
            res.setRight(right);
        }

        return res;

    }

    @Override
    public LinfValue visitValue(ComplexStaticAnalysisParser.ValueContext ctx) {
        if (ctx.ID() != null) {
            return new IDValue(ctx.ID().getText());
        } else if (ctx.INTEGER() != null) {
            return new IntValue(ctx.INTEGER().getText());
        } else if (ctx.getChild(0).getText().equals("true") || ctx.getChild(0).getText().equals("false")) {
            return new BoolValue(ctx.getChild(0).getText());
        } else {
            return visitExp(ctx.exp());
        }
    }
}
