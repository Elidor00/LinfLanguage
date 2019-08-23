// Generated from /home/filippo/Project/complex-extended-static-analysis/src/main/java/lvm/parser/LVM.g4 by ANTLR 4.7.2
package lvm.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LVMParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, JR=4, TOP=5, PRINT=6, HALT=7, LOADI=8, MOVE=9, 
		PUSH=10, POP=11, ADD=12, ADDI=13, SUB=14, SUBI=15, MULT=16, DIV=17, STOREW=18, 
		LOADW=19, BRANCH=20, BRANCHEQ=21, BRANCHNOTEQ=22, BRANCHLESS=23, BRANCHLESSEQ=24, 
		BRANCHGREATER=25, BRANCHGREATEREQ=26, REGISTER=27, LABEL=28, NUMBER=29, 
		WHITESP=30, ERR=31;
	public static final int
		RULE_program = 0, RULE_instruction = 1;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "instruction"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "':'", "'jr'", "'top'", "'print'", "'halt'", "'li'", 
			"'move'", "'push'", "'pop'", "'add'", "'addi'", "'sub'", "'subi'", "'mult'", 
			"'div'", "'sw'", "'lw'", "'b'", "'beq'", "'bne'", "'blr'", "'blre'", 
			"'bgr'", "'bgre'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "JR", "TOP", "PRINT", "HALT", "LOADI", "MOVE", 
			"PUSH", "POP", "ADD", "ADDI", "SUB", "SUBI", "MULT", "DIV", "STOREW", 
			"LOADW", "BRANCH", "BRANCHEQ", "BRANCHNOTEQ", "BRANCHLESS", "BRANCHLESSEQ", 
			"BRANCHGREATER", "BRANCHGREATEREQ", "REGISTER", "LABEL", "NUMBER", "WHITESP", 
			"ERR"
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

	@Override
	public String getGrammarFileName() { return "LVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LVMParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(7);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << JR) | (1L << TOP) | (1L << PRINT) | (1L << HALT) | (1L << LOADI) | (1L << MOVE) | (1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << ADDI) | (1L << SUB) | (1L << SUBI) | (1L << MULT) | (1L << DIV) | (1L << STOREW) | (1L << LOADW) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHNOTEQ) | (1L << BRANCHLESS) | (1L << BRANCHLESSEQ) | (1L << BRANCHGREATER) | (1L << BRANCHGREATEREQ) | (1L << LABEL))) != 0)) {
				{
				{
				setState(4);
				instruction();
				}
				}
				setState(9);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public Token r1;
		public Token r2;
		public Token r3;
		public Token n;
		public Token l;
		public TerminalNode PUSH() { return getToken(LVMParser.PUSH, 0); }
		public TerminalNode POP() { return getToken(LVMParser.POP, 0); }
		public TerminalNode ADD() { return getToken(LVMParser.ADD, 0); }
		public TerminalNode ADDI() { return getToken(LVMParser.ADDI, 0); }
		public TerminalNode SUB() { return getToken(LVMParser.SUB, 0); }
		public TerminalNode SUBI() { return getToken(LVMParser.SUBI, 0); }
		public TerminalNode MULT() { return getToken(LVMParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(LVMParser.DIV, 0); }
		public TerminalNode MOVE() { return getToken(LVMParser.MOVE, 0); }
		public TerminalNode STOREW() { return getToken(LVMParser.STOREW, 0); }
		public TerminalNode LOADW() { return getToken(LVMParser.LOADW, 0); }
		public TerminalNode LOADI() { return getToken(LVMParser.LOADI, 0); }
		public TerminalNode BRANCH() { return getToken(LVMParser.BRANCH, 0); }
		public TerminalNode BRANCHEQ() { return getToken(LVMParser.BRANCHEQ, 0); }
		public TerminalNode BRANCHNOTEQ() { return getToken(LVMParser.BRANCHNOTEQ, 0); }
		public TerminalNode BRANCHLESS() { return getToken(LVMParser.BRANCHLESS, 0); }
		public TerminalNode BRANCHLESSEQ() { return getToken(LVMParser.BRANCHLESSEQ, 0); }
		public TerminalNode BRANCHGREATER() { return getToken(LVMParser.BRANCHGREATER, 0); }
		public TerminalNode BRANCHGREATEREQ() { return getToken(LVMParser.BRANCHGREATEREQ, 0); }
		public TerminalNode JR() { return getToken(LVMParser.JR, 0); }
		public TerminalNode PRINT() { return getToken(LVMParser.PRINT, 0); }
		public TerminalNode TOP() { return getToken(LVMParser.TOP, 0); }
		public TerminalNode HALT() { return getToken(LVMParser.HALT, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode NUMBER() { return getToken(LVMParser.NUMBER, 0); }
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PUSH:
				{
				setState(10);
				match(PUSH);
				setState(11);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				}
				break;
			case POP:
				{
				setState(12);
				match(POP);
				}
				break;
			case ADD:
				{
				setState(13);
				match(ADD);
				setState(14);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(15);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(16);
				((InstructionContext)_localctx).r3 = match(REGISTER);
				}
				break;
			case ADDI:
				{
				setState(17);
				match(ADDI);
				setState(18);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(19);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(20);
				((InstructionContext)_localctx).n = match(NUMBER);
				}
				break;
			case SUB:
				{
				setState(21);
				match(SUB);
				setState(22);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(23);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(24);
				((InstructionContext)_localctx).r3 = match(REGISTER);
				}
				break;
			case SUBI:
				{
				setState(25);
				match(SUBI);
				setState(26);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(27);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(28);
				((InstructionContext)_localctx).n = match(NUMBER);
				}
				break;
			case MULT:
				{
				setState(29);
				match(MULT);
				setState(30);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(31);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(32);
				((InstructionContext)_localctx).r3 = match(REGISTER);
				}
				break;
			case DIV:
				{
				setState(33);
				match(DIV);
				setState(34);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(35);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(36);
				((InstructionContext)_localctx).r3 = match(REGISTER);
				}
				break;
			case MOVE:
				{
				setState(37);
				match(MOVE);
				setState(38);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(39);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				}
				break;
			case STOREW:
				{
				setState(40);
				match(STOREW);
				setState(41);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(42);
				((InstructionContext)_localctx).n = match(NUMBER);
				setState(43);
				match(T__0);
				setState(44);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(45);
				match(T__1);
				}
				break;
			case LOADW:
				{
				setState(46);
				match(LOADW);
				setState(47);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(48);
				((InstructionContext)_localctx).n = match(NUMBER);
				setState(49);
				match(T__0);
				setState(50);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(51);
				match(T__1);
				}
				break;
			case LOADI:
				{
				setState(52);
				match(LOADI);
				setState(53);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(54);
				((InstructionContext)_localctx).n = match(NUMBER);
				}
				break;
			case LABEL:
				{
				setState(55);
				((InstructionContext)_localctx).l = match(LABEL);
				setState(56);
				match(T__2);
				}
				break;
			case BRANCH:
				{
				setState(57);
				match(BRANCH);
				setState(58);
				((InstructionContext)_localctx).l = match(LABEL);
				}
				break;
			case BRANCHEQ:
				{
				setState(59);
				match(BRANCHEQ);
				setState(60);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(61);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(62);
				((InstructionContext)_localctx).l = match(LABEL);
				}
				break;
			case BRANCHNOTEQ:
				{
				setState(63);
				match(BRANCHNOTEQ);
				setState(64);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(65);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(66);
				((InstructionContext)_localctx).l = match(LABEL);
				}
				break;
			case BRANCHLESS:
				{
				setState(67);
				match(BRANCHLESS);
				setState(68);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(69);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(70);
				((InstructionContext)_localctx).l = match(LABEL);
				}
				break;
			case BRANCHLESSEQ:
				{
				setState(71);
				match(BRANCHLESSEQ);
				setState(72);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(73);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(74);
				((InstructionContext)_localctx).l = match(LABEL);
				}
				break;
			case BRANCHGREATER:
				{
				setState(75);
				match(BRANCHGREATER);
				setState(76);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(77);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(78);
				((InstructionContext)_localctx).l = match(LABEL);
				}
				break;
			case BRANCHGREATEREQ:
				{
				setState(79);
				match(BRANCHGREATEREQ);
				setState(80);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				setState(81);
				((InstructionContext)_localctx).r2 = match(REGISTER);
				setState(82);
				((InstructionContext)_localctx).l = match(LABEL);
				}
				break;
			case JR:
				{
				setState(83);
				match(JR);
				setState(84);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				}
				break;
			case PRINT:
				{
				setState(85);
				match(PRINT);
				}
				break;
			case TOP:
				{
				setState(86);
				match(TOP);
				setState(87);
				((InstructionContext)_localctx).r1 = match(REGISTER);
				}
				break;
			case HALT:
				{
				setState(88);
				match(HALT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3!^\4\2\t\2\4\3\t\3"+
		"\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5"+
		"\3\\\n\3\3\3\2\2\4\2\4\2\2\2s\2\t\3\2\2\2\4[\3\2\2\2\6\b\5\4\3\2\7\6\3"+
		"\2\2\2\b\13\3\2\2\2\t\7\3\2\2\2\t\n\3\2\2\2\n\3\3\2\2\2\13\t\3\2\2\2\f"+
		"\r\7\f\2\2\r\\\7\35\2\2\16\\\7\r\2\2\17\20\7\16\2\2\20\21\7\35\2\2\21"+
		"\22\7\35\2\2\22\\\7\35\2\2\23\24\7\17\2\2\24\25\7\35\2\2\25\26\7\35\2"+
		"\2\26\\\7\37\2\2\27\30\7\20\2\2\30\31\7\35\2\2\31\32\7\35\2\2\32\\\7\35"+
		"\2\2\33\34\7\21\2\2\34\35\7\35\2\2\35\36\7\35\2\2\36\\\7\37\2\2\37 \7"+
		"\22\2\2 !\7\35\2\2!\"\7\35\2\2\"\\\7\35\2\2#$\7\23\2\2$%\7\35\2\2%&\7"+
		"\35\2\2&\\\7\35\2\2\'(\7\13\2\2()\7\35\2\2)\\\7\35\2\2*+\7\24\2\2+,\7"+
		"\35\2\2,-\7\37\2\2-.\7\3\2\2./\7\35\2\2/\\\7\4\2\2\60\61\7\25\2\2\61\62"+
		"\7\35\2\2\62\63\7\37\2\2\63\64\7\3\2\2\64\65\7\35\2\2\65\\\7\4\2\2\66"+
		"\67\7\n\2\2\678\7\35\2\28\\\7\37\2\29:\7\36\2\2:\\\7\5\2\2;<\7\26\2\2"+
		"<\\\7\36\2\2=>\7\27\2\2>?\7\35\2\2?@\7\35\2\2@\\\7\36\2\2AB\7\30\2\2B"+
		"C\7\35\2\2CD\7\35\2\2D\\\7\36\2\2EF\7\31\2\2FG\7\35\2\2GH\7\35\2\2H\\"+
		"\7\36\2\2IJ\7\32\2\2JK\7\35\2\2KL\7\35\2\2L\\\7\36\2\2MN\7\33\2\2NO\7"+
		"\35\2\2OP\7\35\2\2P\\\7\36\2\2QR\7\34\2\2RS\7\35\2\2ST\7\35\2\2T\\\7\36"+
		"\2\2UV\7\6\2\2V\\\7\35\2\2W\\\7\b\2\2XY\7\7\2\2Y\\\7\35\2\2Z\\\7\t\2\2"+
		"[\f\3\2\2\2[\16\3\2\2\2[\17\3\2\2\2[\23\3\2\2\2[\27\3\2\2\2[\33\3\2\2"+
		"\2[\37\3\2\2\2[#\3\2\2\2[\'\3\2\2\2[*\3\2\2\2[\60\3\2\2\2[\66\3\2\2\2"+
		"[9\3\2\2\2[;\3\2\2\2[=\3\2\2\2[A\3\2\2\2[E\3\2\2\2[I\3\2\2\2[M\3\2\2\2"+
		"[Q\3\2\2\2[U\3\2\2\2[W\3\2\2\2[X\3\2\2\2[Z\3\2\2\2\\\5\3\2\2\2\4\t[";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}