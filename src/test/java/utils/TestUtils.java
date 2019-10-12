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
import java.util.stream.Collectors;

import static lvm.LVM.MEMSIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public final class TestUtils {
    private static Block makeAST(String code) {
        ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(
                CharStreams.fromString(code)
        );
        List<String> lexicalErrors = checkSyntax(lexer);
        if (!lexicalErrors.isEmpty()) {
            lexicalErrors.forEach(System.err::println);
              System.exit(-1);
        }
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);
        LinfVisitorImpl visitor = new LinfVisitorImpl();
        Block mainBlock = visitor.visitBlock(parser.block());
        assertNotNull(mainBlock);

        return mainBlock;
    }

    private static List<String> checkSyntax(ComplexStaticAnalysisLexer lexer) {
        List<String> errors = lexer.getAllTokens()
                .stream()
                .filter(t -> t.getType() == ComplexStaticAnalysisLexer.ERR)
                .map(t -> "line " + t.getLine() + ":" + t.getCharPositionInLine() + " Illegal token: " + t.getText())
                .collect(Collectors.toList());
        lexer.reset();
        return errors;
    }

    public static List<SemanticError> checkSemantics(String code) {
        return makeAST(code).checkSemantics(new Environment());
    }

    public static Block checkType(String code) throws TypeError {
        Block mainBlock = makeAST(code);
        List<SemanticError> errors = mainBlock.checkSemantics(new Environment());
        if (errors.size() > 0) {
            for (SemanticError e : errors) {
                System.out.println(e);
            }
        }
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

    public static LVM runScript(String linfCode) {
        String bytecode = cgen(linfCode);
        LVM vm = runBytecode(bytecode);
        assert MEMSIZE - 1 == vm.getSp();
        return vm;
    }

    public static LVM runBytecode(String code) {
        int[] bytecode = LVM.assemble(code + "\n halt");
        //exe vm
        LVM vm = new LVM();
        try {
            vm.run(bytecode);
            assertEquals(MEMSIZE - 1, vm.getSp());
            return vm;
        } catch (LVMError err) {
            err.printStackTrace();
            System.exit(-1);
            return null;
        }
    }
}
