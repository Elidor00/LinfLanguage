// Generated from /home/orang3/IdeaProjects/complex-extended-static-analysis/src/main/java/lvm/parser/LVM.g4 by ANTLR 4.7.2
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
		T__0=1, T__1=2, T__2=3, T__3=4, JR=5, JAL=6, TOP=7, PRINT=8, HALT=9, LOADI=10, 
		MOVE=11, PUSH=12, POP=13, ADD=14, ADDI=15, SUB=16, SUBI=17, MULT=18, DIV=19, 
		STOREW=20, LOADW=21, BRANCH=22, BRANCHEQ=23, BRANCHLESS=24, BRANCHLESSEQ=25, 
		BRANCHGREATER=26, BRANCHGREATEREQ=27, REGISTER=28, LABEL=29, NUMBER=30, 
		WHITESP=31, ERR=32;
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
			null, "'('", "')'", "':'", "'<-'", "'jr'", "'jal'", "'top'", "'print'", 
			"'halt'", "'li'", "'move'", "'push'", "'pop'", "'add'", "'addi'", "'sub'", 
			"'subi'", "'mult'", "'div'", "'sw'", "'lw'", "'b'", "'beq'", "'blr'", 
			"'blre'", "'bgr'", "'bgre'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "JR", "JAL", "TOP", "PRINT", "HALT", "LOADI", 
			"MOVE", "PUSH", "POP", "ADD", "ADDI", "SUB", "SUBI", "MULT", "DIV", "STOREW", 
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << JR) | (1L << JAL) | (1L << PRINT) | (1L << HALT) | (1L << LOADI) | (1L << MOVE) | (1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << ADDI) | (1L << SUB) | (1L << SUBI) | (1L << MULT) | (1L << DIV) | (1L << STOREW) | (1L << LOADW) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHLESS) | (1L << BRANCHLESSEQ) | (1L << BRANCHGREATER) | (1L << BRANCHGREATEREQ) | (1L << REGISTER) | (1L << LABEL))) != 0)) {
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
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
	 
		public InstructionContext() { }
		public void copyFrom(InstructionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AddContext extends InstructionContext {
		public TerminalNode ADD() { return getToken(LVMParser.ADD, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public AddContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterAdd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitAdd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubContext extends InstructionContext {
		public TerminalNode SUB() { return getToken(LVMParser.SUB, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public SubContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitSub(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MoveContext extends InstructionContext {
		public TerminalNode MOVE() { return getToken(LVMParser.MOVE, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public MoveContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterMove(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitMove(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitMove(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BContext extends InstructionContext {
		public TerminalNode BRANCH() { return getToken(LVMParser.BRANCH, 0); }
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public BContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterB(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitB(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitB(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultContext extends InstructionContext {
		public TerminalNode MULT() { return getToken(LVMParser.MULT, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public MultContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitMult(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitMult(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SwContext extends InstructionContext {
		public TerminalNode STOREW() { return getToken(LVMParser.STOREW, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode NUMBER() { return getToken(LVMParser.NUMBER, 0); }
		public SwContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterSw(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitSw(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitSw(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddiContext extends InstructionContext {
		public TerminalNode ADDI() { return getToken(LVMParser.ADDI, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode NUMBER() { return getToken(LVMParser.NUMBER, 0); }
		public AddiContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterAddi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitAddi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitAddi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JrContext extends InstructionContext {
		public TerminalNode JR() { return getToken(LVMParser.JR, 0); }
		public TerminalNode REGISTER() { return getToken(LVMParser.REGISTER, 0); }
		public JrContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterJr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitJr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitJr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlrContext extends InstructionContext {
		public TerminalNode BRANCHLESS() { return getToken(LVMParser.BRANCHLESS, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public BlrContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterBlr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitBlr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitBlr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LwContext extends InstructionContext {
		public TerminalNode LOADW() { return getToken(LVMParser.LOADW, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode NUMBER() { return getToken(LVMParser.NUMBER, 0); }
		public LwContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterLw(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitLw(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitLw(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LabelContext extends InstructionContext {
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public LabelContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BgrContext extends InstructionContext {
		public TerminalNode BRANCHGREATER() { return getToken(LVMParser.BRANCHGREATER, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public BgrContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterBgr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitBgr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitBgr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PushContext extends InstructionContext {
		public TerminalNode PUSH() { return getToken(LVMParser.PUSH, 0); }
		public TerminalNode REGISTER() { return getToken(LVMParser.REGISTER, 0); }
		public PushContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterPush(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitPush(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitPush(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BeqContext extends InstructionContext {
		public TerminalNode BRANCHEQ() { return getToken(LVMParser.BRANCHEQ, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public BeqContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterBeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitBeq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitBeq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PopContext extends InstructionContext {
		public TerminalNode POP() { return getToken(LVMParser.POP, 0); }
		public PopContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterPop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitPop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitPop(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivContext extends InstructionContext {
		public TerminalNode DIV() { return getToken(LVMParser.DIV, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public DivContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterDiv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitDiv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class HaltContext extends InstructionContext {
		public TerminalNode HALT() { return getToken(LVMParser.HALT, 0); }
		public HaltContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterHalt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitHalt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitHalt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintContext extends InstructionContext {
		public TerminalNode PRINT() { return getToken(LVMParser.PRINT, 0); }
		public PrintContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitPrint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlreContext extends InstructionContext {
		public TerminalNode BRANCHLESSEQ() { return getToken(LVMParser.BRANCHLESSEQ, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public BlreContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterBlre(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitBlre(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitBlre(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JalContext extends InstructionContext {
		public TerminalNode JAL() { return getToken(LVMParser.JAL, 0); }
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public JalContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterJal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitJal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitJal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TopContext extends InstructionContext {
		public TerminalNode REGISTER() { return getToken(LVMParser.REGISTER, 0); }
		public TerminalNode TOP() { return getToken(LVMParser.TOP, 0); }
		public TopContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterTop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitTop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitTop(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BgreContext extends InstructionContext {
		public TerminalNode BRANCHGREATEREQ() { return getToken(LVMParser.BRANCHGREATEREQ, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode LABEL() { return getToken(LVMParser.LABEL, 0); }
		public BgreContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterBgre(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitBgre(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitBgre(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiContext extends InstructionContext {
		public TerminalNode LOADI() { return getToken(LVMParser.LOADI, 0); }
		public TerminalNode REGISTER() { return getToken(LVMParser.REGISTER, 0); }
		public TerminalNode NUMBER() { return getToken(LVMParser.NUMBER, 0); }
		public LiContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterLi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitLi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitLi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubiContext extends InstructionContext {
		public TerminalNode SUBI() { return getToken(LVMParser.SUBI, 0); }
		public List<TerminalNode> REGISTER() { return getTokens(LVMParser.REGISTER); }
		public TerminalNode REGISTER(int i) {
			return getToken(LVMParser.REGISTER, i);
		}
		public TerminalNode NUMBER() { return getToken(LVMParser.NUMBER, 0); }
		public SubiContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).enterSubi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LVMListener ) ((LVMListener)listener).exitSubi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LVMVisitor ) return ((LVMVisitor<? extends T>)visitor).visitSubi(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction);
		try {
			setState(88);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PUSH:
				_localctx = new PushContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(10);
				match(PUSH);
				setState(11);
				match(REGISTER);
				}
				break;
			case POP:
				_localctx = new PopContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(12);
				match(POP);
				}
				break;
			case ADD:
				_localctx = new AddContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(13);
				match(ADD);
				setState(14);
				match(REGISTER);
				setState(15);
				match(REGISTER);
				setState(16);
				match(REGISTER);
				}
				break;
			case ADDI:
				_localctx = new AddiContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(17);
				match(ADDI);
				setState(18);
				match(REGISTER);
				setState(19);
				match(REGISTER);
				setState(20);
				match(NUMBER);
				}
				break;
			case SUB:
				_localctx = new SubContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(21);
				match(SUB);
				setState(22);
				match(REGISTER);
				setState(23);
				match(REGISTER);
				setState(24);
				match(REGISTER);
				}
				break;
			case SUBI:
				_localctx = new SubiContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(25);
				match(SUBI);
				setState(26);
				match(REGISTER);
				setState(27);
				match(REGISTER);
				setState(28);
				match(NUMBER);
				}
				break;
			case MULT:
				_localctx = new MultContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(29);
				match(MULT);
				setState(30);
				match(REGISTER);
				setState(31);
				match(REGISTER);
				setState(32);
				match(REGISTER);
				}
				break;
			case DIV:
				_localctx = new DivContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(33);
				match(DIV);
				setState(34);
				match(REGISTER);
				setState(35);
				match(REGISTER);
				setState(36);
				match(REGISTER);
				}
				break;
			case MOVE:
				_localctx = new MoveContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(37);
				match(MOVE);
				setState(38);
				match(REGISTER);
				setState(39);
				match(REGISTER);
				}
				break;
			case STOREW:
				_localctx = new SwContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(40);
				match(STOREW);
				setState(41);
				match(REGISTER);
				setState(42);
				match(NUMBER);
				setState(43);
				match(T__0);
				setState(44);
				match(REGISTER);
				setState(45);
				match(T__1);
				}
				break;
			case LOADW:
				_localctx = new LwContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(46);
				match(LOADW);
				setState(47);
				match(REGISTER);
				setState(48);
				match(NUMBER);
				setState(49);
				match(T__0);
				setState(50);
				match(REGISTER);
				setState(51);
				match(T__1);
				}
				break;
			case LOADI:
				_localctx = new LiContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(52);
				match(LOADI);
				setState(53);
				match(REGISTER);
				setState(54);
				match(NUMBER);
				}
				break;
			case LABEL:
				_localctx = new LabelContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(55);
				match(LABEL);
				setState(56);
				match(T__2);
				}
				break;
			case BRANCH:
				_localctx = new BContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(57);
				match(BRANCH);
				setState(58);
				match(LABEL);
				}
				break;
			case BRANCHEQ:
				_localctx = new BeqContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(59);
				match(BRANCHEQ);
				setState(60);
				match(REGISTER);
				setState(61);
				match(REGISTER);
				setState(62);
				match(LABEL);
				}
				break;
			case BRANCHLESS:
				_localctx = new BlrContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(63);
				match(BRANCHLESS);
				setState(64);
				match(REGISTER);
				setState(65);
				match(REGISTER);
				setState(66);
				match(LABEL);
				}
				break;
			case BRANCHLESSEQ:
				_localctx = new BlreContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(67);
				match(BRANCHLESSEQ);
				setState(68);
				match(REGISTER);
				setState(69);
				match(REGISTER);
				setState(70);
				match(LABEL);
				}
				break;
			case BRANCHGREATER:
				_localctx = new BgrContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(71);
				match(BRANCHGREATER);
				setState(72);
				match(REGISTER);
				setState(73);
				match(REGISTER);
				setState(74);
				match(LABEL);
				}
				break;
			case BRANCHGREATEREQ:
				_localctx = new BgreContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(75);
				match(BRANCHGREATEREQ);
				setState(76);
				match(REGISTER);
				setState(77);
				match(REGISTER);
				setState(78);
				match(LABEL);
				}
				break;
			case JAL:
				_localctx = new JalContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(79);
				match(JAL);
				setState(80);
				match(LABEL);
				}
				break;
			case JR:
				_localctx = new JrContext(_localctx);
				enterOuterAlt(_localctx, 21);
				{
				setState(81);
				match(JR);
				setState(82);
				match(REGISTER);
				}
				break;
			case PRINT:
				_localctx = new PrintContext(_localctx);
				enterOuterAlt(_localctx, 22);
				{
				setState(83);
				match(PRINT);
				}
				break;
			case REGISTER:
				_localctx = new TopContext(_localctx);
				enterOuterAlt(_localctx, 23);
				{
				setState(84);
				match(REGISTER);
				setState(85);
				match(T__3);
				setState(86);
				match(TOP);
				}
				break;
			case HALT:
				_localctx = new HaltContext(_localctx);
				enterOuterAlt(_localctx, 24);
				{
				setState(87);
				match(HALT);
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\"]\4\2\t\2\4\3\t"+
		"\3\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3"+
		"[\n\3\3\3\2\2\4\2\4\2\2\2r\2\t\3\2\2\2\4Z\3\2\2\2\6\b\5\4\3\2\7\6\3\2"+
		"\2\2\b\13\3\2\2\2\t\7\3\2\2\2\t\n\3\2\2\2\n\3\3\2\2\2\13\t\3\2\2\2\f\r"+
		"\7\16\2\2\r[\7\36\2\2\16[\7\17\2\2\17\20\7\20\2\2\20\21\7\36\2\2\21\22"+
		"\7\36\2\2\22[\7\36\2\2\23\24\7\21\2\2\24\25\7\36\2\2\25\26\7\36\2\2\26"+
		"[\7 \2\2\27\30\7\22\2\2\30\31\7\36\2\2\31\32\7\36\2\2\32[\7\36\2\2\33"+
		"\34\7\23\2\2\34\35\7\36\2\2\35\36\7\36\2\2\36[\7 \2\2\37 \7\24\2\2 !\7"+
		"\36\2\2!\"\7\36\2\2\"[\7\36\2\2#$\7\25\2\2$%\7\36\2\2%&\7\36\2\2&[\7\36"+
		"\2\2\'(\7\r\2\2()\7\36\2\2)[\7\36\2\2*+\7\26\2\2+,\7\36\2\2,-\7 \2\2-"+
		".\7\3\2\2./\7\36\2\2/[\7\4\2\2\60\61\7\27\2\2\61\62\7\36\2\2\62\63\7 "+
		"\2\2\63\64\7\3\2\2\64\65\7\36\2\2\65[\7\4\2\2\66\67\7\f\2\2\678\7\36\2"+
		"\28[\7 \2\29:\7\37\2\2:[\7\5\2\2;<\7\30\2\2<[\7\37\2\2=>\7\31\2\2>?\7"+
		"\36\2\2?@\7\36\2\2@[\7\37\2\2AB\7\32\2\2BC\7\36\2\2CD\7\36\2\2D[\7\37"+
		"\2\2EF\7\33\2\2FG\7\36\2\2GH\7\36\2\2H[\7\37\2\2IJ\7\34\2\2JK\7\36\2\2"+
		"KL\7\36\2\2L[\7\37\2\2MN\7\35\2\2NO\7\36\2\2OP\7\36\2\2P[\7\37\2\2QR\7"+
		"\b\2\2R[\7\37\2\2ST\7\7\2\2T[\7\36\2\2U[\7\n\2\2VW\7\36\2\2WX\7\6\2\2"+
		"X[\7\t\2\2Y[\7\13\2\2Z\f\3\2\2\2Z\16\3\2\2\2Z\17\3\2\2\2Z\23\3\2\2\2Z"+
		"\27\3\2\2\2Z\33\3\2\2\2Z\37\3\2\2\2Z#\3\2\2\2Z\'\3\2\2\2Z*\3\2\2\2Z\60"+
		"\3\2\2\2Z\66\3\2\2\2Z9\3\2\2\2Z;\3\2\2\2Z=\3\2\2\2ZA\3\2\2\2ZE\3\2\2\2"+
		"ZI\3\2\2\2ZM\3\2\2\2ZQ\3\2\2\2ZS\3\2\2\2ZU\3\2\2\2ZV\3\2\2\2ZY\3\2\2\2"+
		"[\5\3\2\2\2\4\tZ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}