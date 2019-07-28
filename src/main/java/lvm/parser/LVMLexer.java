// Generated from /home/orang3/IdeaProjects/complex-extended-static-analysis/src/main/java/lvm/parser/LVM.g4 by ANTLR 4.7.2
package lvm.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LVMLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, JR = 5, JAL = 6, TOP = 7, PRINT = 8, HALT = 9, LOADI = 10,
            MOVE = 11, PUSH = 12, POP = 13, ADD = 14, ADDI = 15, SUB = 16, SUBI = 17, MULT = 18, DIV = 19,
            STOREW = 20, LOADW = 21, BRANCH = 22, BRANCHEQ = 23, BRANCHLESS = 24, BRANCHLESSEQ = 25,
            BRANCHGREATER = 26, BRANCHGREATEREQ = 27, REGISTER = 28, LABEL = 29, NUMBER = 30,
            WHITESP = 31, ERR = 32;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\"\u00df\b\1\4\2\t" +
                    "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
                    "\t!\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3" +
                    "\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3" +
                    "\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3" +
                    "\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3" +
                    "\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3" +
                    "\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3" +
                    "\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3" +
                    "\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3" +
                    "\35\3\35\3\35\3\35\5\35\u00bf\n\35\3\36\3\36\7\36\u00c3\n\36\f\36\16\36" +
                    "\u00c6\13\36\3\37\3\37\5\37\u00ca\n\37\3\37\3\37\7\37\u00ce\n\37\f\37" +
                    "\16\37\u00d1\13\37\5\37\u00d3\n\37\3 \6 \u00d6\n \r \16 \u00d7\3 \3 \3" +
                    "!\3!\3!\3!\2\2\"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31" +
                    "\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65" +
                    "\34\67\359\36;\37= ?!A\"\3\2\5\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"" +
                    "\"\2\u00e8\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2" +
                    "\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27" +
                    "\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2" +
                    "\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2" +
                    "\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2" +
                    "\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\3C\3\2\2\2\5E\3\2\2\2" +
                    "\7G\3\2\2\2\tI\3\2\2\2\13L\3\2\2\2\rO\3\2\2\2\17S\3\2\2\2\21W\3\2\2\2" +
                    "\23]\3\2\2\2\25b\3\2\2\2\27e\3\2\2\2\31j\3\2\2\2\33o\3\2\2\2\35s\3\2\2" +
                    "\2\37w\3\2\2\2!|\3\2\2\2#\u0080\3\2\2\2%\u0085\3\2\2\2\'\u008a\3\2\2\2" +
                    ")\u008e\3\2\2\2+\u0091\3\2\2\2-\u0094\3\2\2\2/\u0096\3\2\2\2\61\u009a" +
                    "\3\2\2\2\63\u009e\3\2\2\2\65\u00a3\3\2\2\2\67\u00a7\3\2\2\29\u00be\3\2" +
                    "\2\2;\u00c0\3\2\2\2=\u00d2\3\2\2\2?\u00d5\3\2\2\2A\u00db\3\2\2\2CD\7*" +
                    "\2\2D\4\3\2\2\2EF\7+\2\2F\6\3\2\2\2GH\7<\2\2H\b\3\2\2\2IJ\7>\2\2JK\7/" +
                    "\2\2K\n\3\2\2\2LM\7l\2\2MN\7t\2\2N\f\3\2\2\2OP\7l\2\2PQ\7c\2\2QR\7n\2" +
                    "\2R\16\3\2\2\2ST\7v\2\2TU\7q\2\2UV\7r\2\2V\20\3\2\2\2WX\7r\2\2XY\7t\2" +
                    "\2YZ\7k\2\2Z[\7p\2\2[\\\7v\2\2\\\22\3\2\2\2]^\7j\2\2^_\7c\2\2_`\7n\2\2" +
                    "`a\7v\2\2a\24\3\2\2\2bc\7n\2\2cd\7k\2\2d\26\3\2\2\2ef\7o\2\2fg\7q\2\2" +
                    "gh\7x\2\2hi\7g\2\2i\30\3\2\2\2jk\7r\2\2kl\7w\2\2lm\7u\2\2mn\7j\2\2n\32" +
                    "\3\2\2\2op\7r\2\2pq\7q\2\2qr\7r\2\2r\34\3\2\2\2st\7c\2\2tu\7f\2\2uv\7" +
                    "f\2\2v\36\3\2\2\2wx\7c\2\2xy\7f\2\2yz\7f\2\2z{\7k\2\2{ \3\2\2\2|}\7u\2" +
                    "\2}~\7w\2\2~\177\7d\2\2\177\"\3\2\2\2\u0080\u0081\7u\2\2\u0081\u0082\7" +
                    "w\2\2\u0082\u0083\7d\2\2\u0083\u0084\7k\2\2\u0084$\3\2\2\2\u0085\u0086" +
                    "\7o\2\2\u0086\u0087\7w\2\2\u0087\u0088\7n\2\2\u0088\u0089\7v\2\2\u0089" +
                    "&\3\2\2\2\u008a\u008b\7f\2\2\u008b\u008c\7k\2\2\u008c\u008d\7x\2\2\u008d" +
                    "(\3\2\2\2\u008e\u008f\7u\2\2\u008f\u0090\7y\2\2\u0090*\3\2\2\2\u0091\u0092" +
                    "\7n\2\2\u0092\u0093\7y\2\2\u0093,\3\2\2\2\u0094\u0095\7d\2\2\u0095.\3" +
                    "\2\2\2\u0096\u0097\7d\2\2\u0097\u0098\7g\2\2\u0098\u0099\7s\2\2\u0099" +
                    "\60\3\2\2\2\u009a\u009b\7d\2\2\u009b\u009c\7n\2\2\u009c\u009d\7t\2\2\u009d" +
                    "\62\3\2\2\2\u009e\u009f\7d\2\2\u009f\u00a0\7n\2\2\u00a0\u00a1\7t\2\2\u00a1" +
                    "\u00a2\7g\2\2\u00a2\64\3\2\2\2\u00a3\u00a4\7d\2\2\u00a4\u00a5\7i\2\2\u00a5" +
                    "\u00a6\7t\2\2\u00a6\66\3\2\2\2\u00a7\u00a8\7d\2\2\u00a8\u00a9\7i\2\2\u00a9" +
                    "\u00aa\7t\2\2\u00aa\u00ab\7g\2\2\u00ab8\3\2\2\2\u00ac\u00ad\7&\2\2\u00ad" +
                    "\u00ae\7c\2\2\u00ae\u00bf\7\62\2\2\u00af\u00b0\7&\2\2\u00b0\u00b1\7v\2" +
                    "\2\u00b1\u00bf\7\63\2\2\u00b2\u00b3\7&\2\2\u00b3\u00b4\7u\2\2\u00b4\u00bf" +
                    "\7r\2\2\u00b5\u00b6\7&\2\2\u00b6\u00b7\7h\2\2\u00b7\u00bf\7r\2\2\u00b8" +
                    "\u00b9\7&\2\2\u00b9\u00ba\7c\2\2\u00ba\u00bf\7n\2\2\u00bb\u00bc\7&\2\2" +
                    "\u00bc\u00bd\7t\2\2\u00bd\u00bf\7c\2\2\u00be\u00ac\3\2\2\2\u00be\u00af" +
                    "\3\2\2\2\u00be\u00b2\3\2\2\2\u00be\u00b5\3\2\2\2\u00be\u00b8\3\2\2\2\u00be" +
                    "\u00bb\3\2\2\2\u00bf:\3\2\2\2\u00c0\u00c4\t\2\2\2\u00c1\u00c3\t\3\2\2" +
                    "\u00c2\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5" +
                    "\3\2\2\2\u00c5<\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7\u00d3\7\62\2\2\u00c8" +
                    "\u00ca\7/\2\2\u00c9\u00c8\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\3\2" +
                    "\2\2\u00cb\u00cf\4\63;\2\u00cc\u00ce\4\62;\2\u00cd\u00cc\3\2\2\2\u00ce" +
                    "\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d3\3\2" +
                    "\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00c7\3\2\2\2\u00d2\u00c9\3\2\2\2\u00d3" +
                    ">\3\2\2\2\u00d4\u00d6\t\4\2\2\u00d5\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2" +
                    "\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da" +
                    "\b \2\2\u00da@\3\2\2\2\u00db\u00dc\13\2\2\2\u00dc\u00dd\3\2\2\2\u00dd" +
                    "\u00de\b!\2\2\u00deB\3\2\2\2\t\2\u00be\u00c4\u00c9\u00cf\u00d2\u00d7\3" +
                    "\2\3\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    static {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public LVMLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "T__0", "T__1", "T__2", "T__3", "JR", "JAL", "TOP", "PRINT", "HALT",
                "LOADI", "MOVE", "PUSH", "POP", "ADD", "ADDI", "SUB", "SUBI", "MULT",
                "DIV", "STOREW", "LOADW", "BRANCH", "BRANCHEQ", "BRANCHLESS", "BRANCHLESSEQ",
                "BRANCHGREATER", "BRANCHGREATEREQ", "REGISTER", "LABEL", "NUMBER", "WHITESP",
                "ERR"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'('", "')'", "':'", "'<-'", "'jr'", "'jal'", "'top'", "'print'",
                "'halt'", "'li'", "'move'", "'push'", "'pop'", "'add'", "'addi'", "'sub'",
                "'subi'", "'mult'", "'div'", "'sw'", "'lw'", "'b'", "'beq'", "'blr'",
                "'blre'", "'bgr'", "'bgre'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, null, "JR", "JAL", "TOP", "PRINT", "HALT", "LOADI",
                "MOVE", "PUSH", "POP", "ADD", "ADDI", "SUB", "SUBI", "MULT", "DIV", "STOREW",
                "LOADW", "BRANCH", "BRANCHEQ", "BRANCHLESS", "BRANCHLESSEQ", "BRANCHGREATER",
                "BRANCHGREATEREQ", "REGISTER", "LABEL", "NUMBER", "WHITESP", "ERR"
        };
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "LVM.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}