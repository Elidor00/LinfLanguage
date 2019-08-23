// Generated from /home/orang3/IdeaProjects/complex-extended-static-analysis/src/main/java/lvm/parser/LVM.g4 by ANTLR 4.7.2
package lvm.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LVMLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, JR=4, TOP=5, PRINT=6, HALT=7, LOADI=8, MOVE=9, 
		PUSH=10, POP=11, ADD=12, ADDI=13, SUB=14, SUBI=15, MULT=16, DIV=17, STOREW=18, 
		LOADW=19, BRANCH=20, BRANCHEQ=21, BRANCHLESS=22, BRANCHLESSEQ=23, BRANCHGREATER=24, 
		BRANCHGREATEREQ=25, REGISTER=26, LABEL=27, NUMBER=28, WHITESP=29, ERR=30;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "JR", "TOP", "PRINT", "HALT", "LOADI", "MOVE", 
			"PUSH", "POP", "ADD", "ADDI", "SUB", "SUBI", "MULT", "DIV", "STOREW", 
			"LOADW", "BRANCH", "BRANCHEQ", "BRANCHLESS", "BRANCHLESSEQ", "BRANCHGREATER", 
			"BRANCHGREATEREQ", "REGISTER", "LABEL", "NUMBER", "WHITESP", "ERR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "':'", "'jr'", "'top'", "'print'", "'halt'", "'li'", 
			"'move'", "'push'", "'pop'", "'add'", "'addi'", "'sub'", "'subi'", "'mult'", 
			"'div'", "'sw'", "'lw'", "'b'", "'beq'", "'blr'", "'blre'", "'bgr'", 
			"'bgre'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "JR", "TOP", "PRINT", "HALT", "LOADI", "MOVE", 
			"PUSH", "POP", "ADD", "ADDI", "SUB", "SUBI", "MULT", "DIV", "STOREW", 
			"LOADW", "BRANCH", "BRANCHEQ", "BRANCHLESS", "BRANCHLESSEQ", "BRANCHGREATER", 
			"BRANCHGREATEREQ", "REGISTER", "LABEL", "NUMBER", "WHITESP", "ERR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
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

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LVMLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2 \u00d7\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32"+
		"\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u00b7\n\33"+
		"\3\34\3\34\7\34\u00bb\n\34\f\34\16\34\u00be\13\34\3\35\3\35\5\35\u00c2"+
		"\n\35\3\35\3\35\7\35\u00c6\n\35\f\35\16\35\u00c9\13\35\5\35\u00cb\n\35"+
		"\3\36\6\36\u00ce\n\36\r\36\16\36\u00cf\3\36\3\36\3\37\3\37\3\37\3\37\2"+
		"\2 \3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= \3\2\5\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"\"\2\u00e1\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\3?\3\2\2\2\5A\3\2\2\2\7C\3\2\2\2\tE\3\2\2\2\13H\3\2\2\2\rL\3"+
		"\2\2\2\17R\3\2\2\2\21W\3\2\2\2\23Z\3\2\2\2\25_\3\2\2\2\27d\3\2\2\2\31"+
		"h\3\2\2\2\33l\3\2\2\2\35q\3\2\2\2\37u\3\2\2\2!z\3\2\2\2#\177\3\2\2\2%"+
		"\u0083\3\2\2\2\'\u0086\3\2\2\2)\u0089\3\2\2\2+\u008b\3\2\2\2-\u008f\3"+
		"\2\2\2/\u0093\3\2\2\2\61\u0098\3\2\2\2\63\u009c\3\2\2\2\65\u00b6\3\2\2"+
		"\2\67\u00b8\3\2\2\29\u00ca\3\2\2\2;\u00cd\3\2\2\2=\u00d3\3\2\2\2?@\7*"+
		"\2\2@\4\3\2\2\2AB\7+\2\2B\6\3\2\2\2CD\7<\2\2D\b\3\2\2\2EF\7l\2\2FG\7t"+
		"\2\2G\n\3\2\2\2HI\7v\2\2IJ\7q\2\2JK\7r\2\2K\f\3\2\2\2LM\7r\2\2MN\7t\2"+
		"\2NO\7k\2\2OP\7p\2\2PQ\7v\2\2Q\16\3\2\2\2RS\7j\2\2ST\7c\2\2TU\7n\2\2U"+
		"V\7v\2\2V\20\3\2\2\2WX\7n\2\2XY\7k\2\2Y\22\3\2\2\2Z[\7o\2\2[\\\7q\2\2"+
		"\\]\7x\2\2]^\7g\2\2^\24\3\2\2\2_`\7r\2\2`a\7w\2\2ab\7u\2\2bc\7j\2\2c\26"+
		"\3\2\2\2de\7r\2\2ef\7q\2\2fg\7r\2\2g\30\3\2\2\2hi\7c\2\2ij\7f\2\2jk\7"+
		"f\2\2k\32\3\2\2\2lm\7c\2\2mn\7f\2\2no\7f\2\2op\7k\2\2p\34\3\2\2\2qr\7"+
		"u\2\2rs\7w\2\2st\7d\2\2t\36\3\2\2\2uv\7u\2\2vw\7w\2\2wx\7d\2\2xy\7k\2"+
		"\2y \3\2\2\2z{\7o\2\2{|\7w\2\2|}\7n\2\2}~\7v\2\2~\"\3\2\2\2\177\u0080"+
		"\7f\2\2\u0080\u0081\7k\2\2\u0081\u0082\7x\2\2\u0082$\3\2\2\2\u0083\u0084"+
		"\7u\2\2\u0084\u0085\7y\2\2\u0085&\3\2\2\2\u0086\u0087\7n\2\2\u0087\u0088"+
		"\7y\2\2\u0088(\3\2\2\2\u0089\u008a\7d\2\2\u008a*\3\2\2\2\u008b\u008c\7"+
		"d\2\2\u008c\u008d\7g\2\2\u008d\u008e\7s\2\2\u008e,\3\2\2\2\u008f\u0090"+
		"\7d\2\2\u0090\u0091\7n\2\2\u0091\u0092\7t\2\2\u0092.\3\2\2\2\u0093\u0094"+
		"\7d\2\2\u0094\u0095\7n\2\2\u0095\u0096\7t\2\2\u0096\u0097\7g\2\2\u0097"+
		"\60\3\2\2\2\u0098\u0099\7d\2\2\u0099\u009a\7i\2\2\u009a\u009b\7t\2\2\u009b"+
		"\62\3\2\2\2\u009c\u009d\7d\2\2\u009d\u009e\7i\2\2\u009e\u009f\7t\2\2\u009f"+
		"\u00a0\7g\2\2\u00a0\64\3\2\2\2\u00a1\u00a2\7&\2\2\u00a2\u00a3\7c\2\2\u00a3"+
		"\u00b7\7\62\2\2\u00a4\u00a5\7&\2\2\u00a5\u00a6\7v\2\2\u00a6\u00b7\7\63"+
		"\2\2\u00a7\u00a8\7&\2\2\u00a8\u00a9\7u\2\2\u00a9\u00b7\7r\2\2\u00aa\u00ab"+
		"\7&\2\2\u00ab\u00ac\7h\2\2\u00ac\u00b7\7r\2\2\u00ad\u00ae\7&\2\2\u00ae"+
		"\u00af\7c\2\2\u00af\u00b7\7n\2\2\u00b0\u00b1\7&\2\2\u00b1\u00b2\7t\2\2"+
		"\u00b2\u00b7\7c\2\2\u00b3\u00b4\7&\2\2\u00b4\u00b5\7k\2\2\u00b5\u00b7"+
		"\7r\2\2\u00b6\u00a1\3\2\2\2\u00b6\u00a4\3\2\2\2\u00b6\u00a7\3\2\2\2\u00b6"+
		"\u00aa\3\2\2\2\u00b6\u00ad\3\2\2\2\u00b6\u00b0\3\2\2\2\u00b6\u00b3\3\2"+
		"\2\2\u00b7\66\3\2\2\2\u00b8\u00bc\t\2\2\2\u00b9\u00bb\t\3\2\2\u00ba\u00b9"+
		"\3\2\2\2\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd"+
		"8\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00cb\7\62\2\2\u00c0\u00c2\7/\2\2"+
		"\u00c1\u00c0\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c7"+
		"\4\63;\2\u00c4\u00c6\4\62;\2\u00c5\u00c4\3\2\2\2\u00c6\u00c9\3\2\2\2\u00c7"+
		"\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2"+
		"\2\2\u00ca\u00bf\3\2\2\2\u00ca\u00c1\3\2\2\2\u00cb:\3\2\2\2\u00cc\u00ce"+
		"\t\4\2\2\u00cd\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf"+
		"\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\b\36\2\2\u00d2<\3\2\2\2"+
		"\u00d3\u00d4\13\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d6\b\37\2\2\u00d6>"+
		"\3\2\2\2\t\2\u00b6\u00bc\u00c1\u00c7\u00ca\u00cf\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}