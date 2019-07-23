package app;

import linf.statement.StmtBlock;
import linf.LinfVisitorImpl;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import linf.parser.ComplexStaticAnalysisLexer;
import linf.parser.ComplexStaticAnalysisParser;
import linf.utils.Environment;
import linf.utils.SemanticError;

import java.io.IOException;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        String fileName = "test.spl";

        try {
            CharStream input = CharStreams.fromFileName(fileName);

            //create lexer
            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(input);


            //create linf.parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);

            parser.setBuildParseTree(true);

            LinfVisitorImpl visitor = new LinfVisitorImpl();

            StmtBlock blk = (StmtBlock) visitor.visit(parser.block());

            Environment env = new Environment();

            ArrayList<SemanticError> errors = blk.checkSemantics(env);

            for (SemanticError e : errors) {
                System.out.println(e);
            }

            if (errors.isEmpty()) {
                blk.checkType();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
