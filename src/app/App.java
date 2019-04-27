package app;

import ast.ComplexExtdStmtBlock;
import ast.ComplexExtdVisitorImpl;
import utils.Environment;
import utils.SemanticError;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        String fileName = "test.spl";

        try {
            CharStream input = CharStreams.fromFileName(fileName);

            //create lexer
            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(input);


            //create parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);

            parser.setBuildParseTree(true);

            ComplexExtdVisitorImpl visitor = new ComplexExtdVisitorImpl();

            ComplexExtdStmtBlock blk = (ComplexExtdStmtBlock) visitor.visit(parser.block());

            Environment env = new Environment();

            for (SemanticError e : blk.checkSemantics(env)){
                System.out.println(e);
            }

            blk.checkType(env);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
