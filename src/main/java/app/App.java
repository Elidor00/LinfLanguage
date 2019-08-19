package app;

import linf.LinfVisitorImpl;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.parser.ComplexStaticAnalysisLexer;
import linf.parser.ComplexStaticAnalysisParser;
import linf.statement.Block;
import linf.utils.Environment;
import lvm.LVM;
import lvm.error.LVMError;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

class App {

    public static void main(String[] args) {
        String fileName = "test.lnf";

        try {
            CharStream input = CharStreams.fromFileName(fileName);

            //create lexer
            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(input);
            //create parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);
            parser.setBuildParseTree(true);

            //create custom visitor
            LinfVisitorImpl visitor = new LinfVisitorImpl();

            ArrayList<String> lexerErrors = new ArrayList<>(); //list of LEXICAL errors
            //check LEXICAL errors
            for (Token t : lexer.getAllTokens()) { //get all token
                if (t.getType() == ComplexStaticAnalysisLexer.ERR) { //check token type
                    System.out.println("Token Error: " + t +
                            " Line: " + t.getLine() +
                            " Char position: " + t.getCharPositionInLine());
                    lexerErrors.add("Token Error: " + t +
                            " Line: " + t.getLine() +
                            " Char position: " + t.getCharPositionInLine());
                }
            }
            if (!lexerErrors.isEmpty()) {
                System.err.println("Found LEXICAL errors. Exiting process.");
                exit(-1);
            }

            //start visit the root and then recursively visit the whole tree
            Block blk = (Block) visitor.visit(parser.block());

            Environment env = new Environment();

            List<SemanticError> semanticsErrors = blk.checkSemantics(env); //list of SEMANTICS errors

            //if no SEMANTICS errors
            if (semanticsErrors.isEmpty()) {
                try {
                    //typeCheck
                    blk.checkType();
                    String cgen = blk.codeGen();
                    System.out.println(cgen);
                    int[] bytecode = LVM.assemble(cgen + "\nhalt");
                    //vm
                    LVM vm = new LVM();
                    vm.run(bytecode);
                } catch (TypeError | LVMError e) {
                    e.printStackTrace();
                }
            } else {
                //noinspection LoopStatementThatDoesntLoop
                for (SemanticError err : semanticsErrors) {
                    System.out.println(err);
                    exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
