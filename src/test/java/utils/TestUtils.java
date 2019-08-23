package utils;

import linf.LinfVisitorImpl;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.parser.ComplexStaticAnalysisLexer;
import linf.parser.ComplexStaticAnalysisParser;
import linf.statement.Block;
import linf.utils.Environment;
import lvm.LVM;
import lvm.error.LVMError;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public final class TestUtils {
    private static Block makeAST(String code) {
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

    public static Block checkType(String code) throws TypeError {
        Block mainBlock = makeAST(code);
        List<SemanticError> errors = mainBlock.checkSemantics(new Environment());
        assertEquals(0, errors.size());
        mainBlock.checkType();
        return mainBlock;
    }

    public static String cgen(String linfCode) {
        try {
            return checkType(linfCode).codeGen();
        } catch (TypeError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LVM runBytecode(String code) {
        int[] bytecode = LVM.assemble(code + "\n halt");
        //exe vm
        LVM vm = new LVM();
        try {
            vm.run(bytecode);
            return vm;
        } catch (LVMError err) {
            err.printStackTrace();
            return null;
        }
    }
}
