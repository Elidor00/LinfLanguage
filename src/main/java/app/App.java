package app;

import linf.LinfVisitorImpl;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.parser.ComplexStaticAnalysisLexer;
import linf.parser.ComplexStaticAnalysisParser;
import linf.statement.Block;
import linf.utils.Environment;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

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

            ArrayList<String> lexerErrors = new ArrayList<>(); //list of lexical errors
            //lexical errors
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
                System.err.println("Found lexical errors. Exiting process.");
                exit(-1);
            }

            //create linf.parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);

            parser.setBuildParseTree(true);

            LinfVisitorImpl visitor = new LinfVisitorImpl();

            Block blk = (Block) visitor.visit(parser.block());

            Environment env = new Environment();

            ArrayList<SemanticError> errors = blk.checkSemantics(env);
            for (SemanticError e : errors) {
                System.out.println(e);
            }

            if (errors.isEmpty()) {
                try {
                    blk.checkType();
                } catch (TypeError e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
