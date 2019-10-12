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
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.exit;

class App {
    public static void main(String[] args) {
        String fileName = "test.lnf";

        try {
            Block blk = makeAST(fileName);
            List<SemanticError> semanticErrors = blk.checkSemantics(new Environment());

            if (semanticErrors.isEmpty()) {
                blk.checkType();
                String bytecodeSource = blk.codeGen();

                int[] bytecode = LVM.assemble(bytecodeSource + "\nhalt");
                LVM vm = new LVM();
                vm.run(bytecode);
            } else {
                for (SemanticError err : semanticErrors) {
                    System.err.println(err);
                    exit(-1);
                }
            }
        } catch (IOException | TypeError | LVMError e) {
            e.printStackTrace();
        }
    }

    private static Block makeAST(String program) throws IOException {
        ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(
                CharStreams.fromFileName(program)
        );
        List<String> lexicalErrors = checkSyntax(lexer);
        if (!lexicalErrors.isEmpty()) {
            lexicalErrors.forEach(System.err::println);
            System.exit(-1);
        }
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);
        LinfVisitorImpl visitor = new LinfVisitorImpl();
        return (Block) visitor.visit(parser.block());
    }

    private static List<String> checkSyntax(ComplexStaticAnalysisLexer lexer) {
        List<String> lexicalErrors = lexer.getAllTokens()
                .stream()
                .filter(t -> t.getType() == ComplexStaticAnalysisLexer.ERR)
                .map(t -> "line " + t.getLine() + ":" + t.getCharPositionInLine() + " Illegal token: " + t.getText())
                .collect(Collectors.toList());
        lexer.reset();
        return lexicalErrors;
    }
}
