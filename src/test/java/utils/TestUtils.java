package utils;

import linf.LinfVisitorImpl;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.parser.ComplexStaticAnalysisLexer;
import linf.parser.ComplexStaticAnalysisParser;
import linf.statement.Block;
import linf.type.LinfType;
import linf.utils.Environment;
import lvm.LvmVisitorImpl;
import lvm.parser.LVMLexer;
import lvm.parser.LVMParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public final class TestUtils {
    public static Block makeAST(String code) {
        ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(
                CharStreams.fromString(code)
        );
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);
        LinfVisitorImpl visitor = new LinfVisitorImpl();
        Block mainBlock = visitor.visitBlock(parser.block());
        assertNotNull(mainBlock);

        return mainBlock;
    }

    public static List<SemanticError> checkSemantics(String code) {
        return makeAST(code).checkSemantics(new Environment());
    }

    public static LinfType checkType(String code) throws TypeError {
        Block mainBlock = makeAST(code);
        List<SemanticError> errors = mainBlock.checkSemantics(new Environment());
        assertEquals(0, errors.size());
        return mainBlock.checkType();
    }

    public static int[] assemble(String code) {
        LVMLexer interpreterLexerCgen = new LVMLexer(
                CharStreams.fromString(code)
        );
        CommonTokenStream interpreterTokens = new CommonTokenStream(interpreterLexerCgen);
        LVMParser interpreterParser = new LVMParser(interpreterTokens);
        interpreterParser.setBuildParseTree(true);
        LvmVisitorImpl interpreterVisitorCgen = new LvmVisitorImpl();
        interpreterVisitorCgen.visitProgram(interpreterParser.program());
        return interpreterVisitorCgen.code;
    }
}
