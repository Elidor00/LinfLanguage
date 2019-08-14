package app;

import linf.LinfVisitorImpl;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.parser.ComplexStaticAnalysisLexer;
import linf.parser.ComplexStaticAnalysisParser;
import linf.statement.Block;
import linf.utils.Environment;
import lvm.ExecuteVM;
import lvm.LvmVisitorImpl;
import lvm.parser.LVMLexer;
import lvm.parser.LVMParser;
import org.antlr.v4.runtime.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;

public class App {

    public static void main(String[] args) {
        String fileName = "test.lnf";

        try {
            CharStream input = CharStreams.fromFileName(fileName);

            //create lexer
            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(input);

            ArrayList<String> lexerErrors = new ArrayList<>(); //list of LEXICAL errors
            //check LEXICAL errors
            for (Token t: lexer.getAllTokens()){ //get all token
                //System.out.println("\nToken: " + "t");
                if (t.getType() == lexer.ERR){ //check token type
                    System.out.println("Token Error: " + t +
                            " Line: " + t.getLine() +
                            " Char position: " + t.getCharPositionInLine());
                    lexerErrors.add("Token Error: " + t +
                            " Line: " + t.getLine() +
                            " Char position: " + t.getCharPositionInLine());
                }
            }
            if(!lexerErrors.isEmpty()){
                System.err.println("Found LEXICAL errors. Exiting process.");
                exit(-1);
            }

            lexer.reset();

            //create parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);
            parser.setBuildParseTree(true);

            //create custom visitor
            LinfVisitorImpl visitor = new LinfVisitorImpl();
            //start visit the root and then recursively visit the whole tree
            Block blk = (Block) visitor.visit(parser.block());
            //check SYNTAX errors
            if(parser.getNumberOfSyntaxErrors()!= 0){
                System.err.println("Found SYNTAX errors. Exiting process.");
                exit(-1);
            }

            Environment env = new Environment();

            ArrayList<SemanticError> semanticsErrors = blk.checkSemantics(env); //list of SEMANTICS errors
            //check semantics errors
            for (SemanticError err : semanticsErrors) {
                System.out.println(err);
            exit(-1);
            }
            //if no SEMANTICS errors
            if (semanticsErrors.isEmpty()) {
                try {
                    //typeCheck
                    //parser.reset();
                    blk.checkType();
                    System.out.println("Type checking done");
                    //parser.reset();
                    //Block blk2 = (Block) visitor.visit(parser.block());
                    String cgen = blk.codeGen();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("bytecode.txt"));
                    writer.write(cgen);
                    writer.close();
                    System.out.println("File written Successfully");
                    //interpreter
                    CharStream stream = new ANTLRInputStream(cgen); //for LVMLexer
                    LVMLexer interpreterLexerCgen = new LVMLexer(stream);
                    CommonTokenStream interpreterTokens = new CommonTokenStream(interpreterLexerCgen);
                    LVMParser interpreterParser = new LVMParser(interpreterTokens);
                    interpreterParser.setBuildParseTree(true);
                    LvmVisitorImpl interpreterVisitorCgen = new LvmVisitorImpl();
                    interpreterVisitorCgen.visitProgram(interpreterParser.program());
                    //exe vm
                    ExecuteVM vm = new ExecuteVM();
                    vm.cpu(interpreterVisitorCgen.code);
                } catch (TypeError e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
