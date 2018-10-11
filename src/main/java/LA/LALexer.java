// Generated from LA.g4 by ANTLR 4.7.1
package LA;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LALexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		WS=60, ENDL=61, NUM_INT=62, NUM_REAL=63, CADEIA=64, IDENT=65, COMENTARIO=66, 
		COMENTARIO_NAO_FECHADO=67, ERRO=68, ERROR=69;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32", 
		"T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "T__39", "T__40", 
		"T__41", "T__42", "T__43", "T__44", "T__45", "T__46", "T__47", "T__48", 
		"T__49", "T__50", "T__51", "T__52", "T__53", "T__54", "T__55", "T__56", 
		"T__57", "T__58", "LETRA", "ALGARISMO", "WS", "ENDL", "NUM_INT", "NUM_REAL", 
		"CADEIA", "IDENT", "COMENTARIO", "COMENTARIO_NAO_FECHADO", "ERRO", "ERROR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'algoritmo'", "'fim_algoritmo'", "'declare'", "'constante'", "':'", 
		"'='", "'tipo'", "','", "'.'", "'['", "']'", "'literal'", "'inteiro'", 
		"'real'", "'logico'", "'^'", "'verdadeiro'", "'falso'", "'registro'", 
		"'fim_registro'", "'procedimento'", "'('", "')'", "'fim_procedimento'", 
		"'funcao'", "'fim_funcao'", "'var'", "'leia'", "'escreva'", "'se'", "'entao'", 
		"'senao'", "'fim_se'", "'caso'", "'seja'", "'fim_caso'", "'para'", "'<-'", 
		"'ate'", "'faca'", "'fim_para'", "'enquanto'", "'fim_enquanto'", "'retorne'", 
		"'..'", "'-'", "'+'", "'*'", "'/'", "'%'", "'&'", "'<>'", "'>='", "'<='", 
		"'>'", "'<'", "'nao'", "'ou'", "'e'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"WS", "ENDL", "NUM_INT", "NUM_REAL", "CADEIA", "IDENT", "COMENTARIO", 
		"COMENTARIO_NAO_FECHADO", "ERRO", "ERROR"
	};
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


	   void erroLexico(String mensagem) {
	      throw new ParseCancellationException(mensagem);
	   }


	public LALexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LA.g4"; }

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

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 68:
			COMENTARIO_NAO_FECHADO_action((RuleContext)_localctx, actionIndex);
			break;
		case 69:
			ERRO_action((RuleContext)_localctx, actionIndex);
			break;
		case 70:
			ERROR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void COMENTARIO_NAO_FECHADO_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 { erroLexico("Linha "+getLine()+": comentario nao fechado"); }; 
			break;
		}
	}
	private void ERRO_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 erroLexico("Linha "+getLine()+": "+getText()+" - simbolo nao identificado"); 
			break;
		}
	}
	private void ERROR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 throw new ParseCancellationException("Linha "+getLine()+": "+getText()+" - simbolo nao identificado");
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2G\u0232\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3"+
		"\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34"+
		"\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3"+
		"\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3&\3&\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3(\3)\3)\3)\3)\3)\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3"+
		",\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3/\3/\3\60\3\60\3\61\3\61"+
		"\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67"+
		"\3\67\38\38\39\39\3:\3:\3:\3:\3;\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3?\3?\3"+
		"@\5@\u01e6\n@\3@\3@\3A\6A\u01eb\nA\rA\16A\u01ec\3B\6B\u01f0\nB\rB\16B"+
		"\u01f1\3B\3B\6B\u01f6\nB\rB\16B\u01f7\3C\3C\7C\u01fc\nC\fC\16C\u01ff\13"+
		"C\3C\3C\3C\7C\u0204\nC\fC\16C\u0207\13C\3C\5C\u020a\nC\3D\3D\5D\u020e"+
		"\nD\3D\3D\3D\7D\u0213\nD\fD\16D\u0216\13D\3E\3E\7E\u021a\nE\fE\16E\u021d"+
		"\13E\3E\3E\3E\3E\3F\3F\7F\u0225\nF\fF\16F\u0228\13F\3F\3F\3F\3G\3G\3G"+
		"\3H\3H\3H\2\2I\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g"+
		"\65i\66k\67m8o9q:s;u<w=y\2{\2}>\177?\u0081@\u0083A\u0085B\u0087C\u0089"+
		"D\u008bE\u008dF\u008fG\3\2\b\4\2C\\c|\3\2\62;\4\2\13\f\17\17\4\2))^^\3"+
		"\2$$\5\2\f\f\17\17\177\177\2\u023b\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2"+
		"\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2"+
		"O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3"+
		"\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2"+
		"\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2"+
		"u\3\2\2\2\2w\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083"+
		"\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2"+
		"\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\3\u0091\3\2\2\2\5\u009b\3\2\2\2\7\u00a9"+
		"\3\2\2\2\t\u00b1\3\2\2\2\13\u00bb\3\2\2\2\r\u00bd\3\2\2\2\17\u00bf\3\2"+
		"\2\2\21\u00c4\3\2\2\2\23\u00c6\3\2\2\2\25\u00c8\3\2\2\2\27\u00ca\3\2\2"+
		"\2\31\u00cc\3\2\2\2\33\u00d4\3\2\2\2\35\u00dc\3\2\2\2\37\u00e1\3\2\2\2"+
		"!\u00e8\3\2\2\2#\u00ea\3\2\2\2%\u00f5\3\2\2\2\'\u00fb\3\2\2\2)\u0104\3"+
		"\2\2\2+\u0111\3\2\2\2-\u011e\3\2\2\2/\u0120\3\2\2\2\61\u0122\3\2\2\2\63"+
		"\u0133\3\2\2\2\65\u013a\3\2\2\2\67\u0145\3\2\2\29\u0149\3\2\2\2;\u014e"+
		"\3\2\2\2=\u0156\3\2\2\2?\u0159\3\2\2\2A\u015f\3\2\2\2C\u0165\3\2\2\2E"+
		"\u016c\3\2\2\2G\u0171\3\2\2\2I\u0176\3\2\2\2K\u017f\3\2\2\2M\u0184\3\2"+
		"\2\2O\u0187\3\2\2\2Q\u018b\3\2\2\2S\u0190\3\2\2\2U\u0199\3\2\2\2W\u01a2"+
		"\3\2\2\2Y\u01af\3\2\2\2[\u01b7\3\2\2\2]\u01ba\3\2\2\2_\u01bc\3\2\2\2a"+
		"\u01be\3\2\2\2c\u01c0\3\2\2\2e\u01c2\3\2\2\2g\u01c4\3\2\2\2i\u01c6\3\2"+
		"\2\2k\u01c9\3\2\2\2m\u01cc\3\2\2\2o\u01cf\3\2\2\2q\u01d1\3\2\2\2s\u01d3"+
		"\3\2\2\2u\u01d7\3\2\2\2w\u01da\3\2\2\2y\u01dc\3\2\2\2{\u01de\3\2\2\2}"+
		"\u01e0\3\2\2\2\177\u01e5\3\2\2\2\u0081\u01ea\3\2\2\2\u0083\u01ef\3\2\2"+
		"\2\u0085\u0209\3\2\2\2\u0087\u020d\3\2\2\2\u0089\u0217\3\2\2\2\u008b\u0222"+
		"\3\2\2\2\u008d\u022c\3\2\2\2\u008f\u022f\3\2\2\2\u0091\u0092\7c\2\2\u0092"+
		"\u0093\7n\2\2\u0093\u0094\7i\2\2\u0094\u0095\7q\2\2\u0095\u0096\7t\2\2"+
		"\u0096\u0097\7k\2\2\u0097\u0098\7v\2\2\u0098\u0099\7o\2\2\u0099\u009a"+
		"\7q\2\2\u009a\4\3\2\2\2\u009b\u009c\7h\2\2\u009c\u009d\7k\2\2\u009d\u009e"+
		"\7o\2\2\u009e\u009f\7a\2\2\u009f\u00a0\7c\2\2\u00a0\u00a1\7n\2\2\u00a1"+
		"\u00a2\7i\2\2\u00a2\u00a3\7q\2\2\u00a3\u00a4\7t\2\2\u00a4\u00a5\7k\2\2"+
		"\u00a5\u00a6\7v\2\2\u00a6\u00a7\7o\2\2\u00a7\u00a8\7q\2\2\u00a8\6\3\2"+
		"\2\2\u00a9\u00aa\7f\2\2\u00aa\u00ab\7g\2\2\u00ab\u00ac\7e\2\2\u00ac\u00ad"+
		"\7n\2\2\u00ad\u00ae\7c\2\2\u00ae\u00af\7t\2\2\u00af\u00b0\7g\2\2\u00b0"+
		"\b\3\2\2\2\u00b1\u00b2\7e\2\2\u00b2\u00b3\7q\2\2\u00b3\u00b4\7p\2\2\u00b4"+
		"\u00b5\7u\2\2\u00b5\u00b6\7v\2\2\u00b6\u00b7\7c\2\2\u00b7\u00b8\7p\2\2"+
		"\u00b8\u00b9\7v\2\2\u00b9\u00ba\7g\2\2\u00ba\n\3\2\2\2\u00bb\u00bc\7<"+
		"\2\2\u00bc\f\3\2\2\2\u00bd\u00be\7?\2\2\u00be\16\3\2\2\2\u00bf\u00c0\7"+
		"v\2\2\u00c0\u00c1\7k\2\2\u00c1\u00c2\7r\2\2\u00c2\u00c3\7q\2\2\u00c3\20"+
		"\3\2\2\2\u00c4\u00c5\7.\2\2\u00c5\22\3\2\2\2\u00c6\u00c7\7\60\2\2\u00c7"+
		"\24\3\2\2\2\u00c8\u00c9\7]\2\2\u00c9\26\3\2\2\2\u00ca\u00cb\7_\2\2\u00cb"+
		"\30\3\2\2\2\u00cc\u00cd\7n\2\2\u00cd\u00ce\7k\2\2\u00ce\u00cf\7v\2\2\u00cf"+
		"\u00d0\7g\2\2\u00d0\u00d1\7t\2\2\u00d1\u00d2\7c\2\2\u00d2\u00d3\7n\2\2"+
		"\u00d3\32\3\2\2\2\u00d4\u00d5\7k\2\2\u00d5\u00d6\7p\2\2\u00d6\u00d7\7"+
		"v\2\2\u00d7\u00d8\7g\2\2\u00d8\u00d9\7k\2\2\u00d9\u00da\7t\2\2\u00da\u00db"+
		"\7q\2\2\u00db\34\3\2\2\2\u00dc\u00dd\7t\2\2\u00dd\u00de\7g\2\2\u00de\u00df"+
		"\7c\2\2\u00df\u00e0\7n\2\2\u00e0\36\3\2\2\2\u00e1\u00e2\7n\2\2\u00e2\u00e3"+
		"\7q\2\2\u00e3\u00e4\7i\2\2\u00e4\u00e5\7k\2\2\u00e5\u00e6\7e\2\2\u00e6"+
		"\u00e7\7q\2\2\u00e7 \3\2\2\2\u00e8\u00e9\7`\2\2\u00e9\"\3\2\2\2\u00ea"+
		"\u00eb\7x\2\2\u00eb\u00ec\7g\2\2\u00ec\u00ed\7t\2\2\u00ed\u00ee\7f\2\2"+
		"\u00ee\u00ef\7c\2\2\u00ef\u00f0\7f\2\2\u00f0\u00f1\7g\2\2\u00f1\u00f2"+
		"\7k\2\2\u00f2\u00f3\7t\2\2\u00f3\u00f4\7q\2\2\u00f4$\3\2\2\2\u00f5\u00f6"+
		"\7h\2\2\u00f6\u00f7\7c\2\2\u00f7\u00f8\7n\2\2\u00f8\u00f9\7u\2\2\u00f9"+
		"\u00fa\7q\2\2\u00fa&\3\2\2\2\u00fb\u00fc\7t\2\2\u00fc\u00fd\7g\2\2\u00fd"+
		"\u00fe\7i\2\2\u00fe\u00ff\7k\2\2\u00ff\u0100\7u\2\2\u0100\u0101\7v\2\2"+
		"\u0101\u0102\7t\2\2\u0102\u0103\7q\2\2\u0103(\3\2\2\2\u0104\u0105\7h\2"+
		"\2\u0105\u0106\7k\2\2\u0106\u0107\7o\2\2\u0107\u0108\7a\2\2\u0108\u0109"+
		"\7t\2\2\u0109\u010a\7g\2\2\u010a\u010b\7i\2\2\u010b\u010c\7k\2\2\u010c"+
		"\u010d\7u\2\2\u010d\u010e\7v\2\2\u010e\u010f\7t\2\2\u010f\u0110\7q\2\2"+
		"\u0110*\3\2\2\2\u0111\u0112\7r\2\2\u0112\u0113\7t\2\2\u0113\u0114\7q\2"+
		"\2\u0114\u0115\7e\2\2\u0115\u0116\7g\2\2\u0116\u0117\7f\2\2\u0117\u0118"+
		"\7k\2\2\u0118\u0119\7o\2\2\u0119\u011a\7g\2\2\u011a\u011b\7p\2\2\u011b"+
		"\u011c\7v\2\2\u011c\u011d\7q\2\2\u011d,\3\2\2\2\u011e\u011f\7*\2\2\u011f"+
		".\3\2\2\2\u0120\u0121\7+\2\2\u0121\60\3\2\2\2\u0122\u0123\7h\2\2\u0123"+
		"\u0124\7k\2\2\u0124\u0125\7o\2\2\u0125\u0126\7a\2\2\u0126\u0127\7r\2\2"+
		"\u0127\u0128\7t\2\2\u0128\u0129\7q\2\2\u0129\u012a\7e\2\2\u012a\u012b"+
		"\7g\2\2\u012b\u012c\7f\2\2\u012c\u012d\7k\2\2\u012d\u012e\7o\2\2\u012e"+
		"\u012f\7g\2\2\u012f\u0130\7p\2\2\u0130\u0131\7v\2\2\u0131\u0132\7q\2\2"+
		"\u0132\62\3\2\2\2\u0133\u0134\7h\2\2\u0134\u0135\7w\2\2\u0135\u0136\7"+
		"p\2\2\u0136\u0137\7e\2\2\u0137\u0138\7c\2\2\u0138\u0139\7q\2\2\u0139\64"+
		"\3\2\2\2\u013a\u013b\7h\2\2\u013b\u013c\7k\2\2\u013c\u013d\7o\2\2\u013d"+
		"\u013e\7a\2\2\u013e\u013f\7h\2\2\u013f\u0140\7w\2\2\u0140\u0141\7p\2\2"+
		"\u0141\u0142\7e\2\2\u0142\u0143\7c\2\2\u0143\u0144\7q\2\2\u0144\66\3\2"+
		"\2\2\u0145\u0146\7x\2\2\u0146\u0147\7c\2\2\u0147\u0148\7t\2\2\u01488\3"+
		"\2\2\2\u0149\u014a\7n\2\2\u014a\u014b\7g\2\2\u014b\u014c\7k\2\2\u014c"+
		"\u014d\7c\2\2\u014d:\3\2\2\2\u014e\u014f\7g\2\2\u014f\u0150\7u\2\2\u0150"+
		"\u0151\7e\2\2\u0151\u0152\7t\2\2\u0152\u0153\7g\2\2\u0153\u0154\7x\2\2"+
		"\u0154\u0155\7c\2\2\u0155<\3\2\2\2\u0156\u0157\7u\2\2\u0157\u0158\7g\2"+
		"\2\u0158>\3\2\2\2\u0159\u015a\7g\2\2\u015a\u015b\7p\2\2\u015b\u015c\7"+
		"v\2\2\u015c\u015d\7c\2\2\u015d\u015e\7q\2\2\u015e@\3\2\2\2\u015f\u0160"+
		"\7u\2\2\u0160\u0161\7g\2\2\u0161\u0162\7p\2\2\u0162\u0163\7c\2\2\u0163"+
		"\u0164\7q\2\2\u0164B\3\2\2\2\u0165\u0166\7h\2\2\u0166\u0167\7k\2\2\u0167"+
		"\u0168\7o\2\2\u0168\u0169\7a\2\2\u0169\u016a\7u\2\2\u016a\u016b\7g\2\2"+
		"\u016bD\3\2\2\2\u016c\u016d\7e\2\2\u016d\u016e\7c\2\2\u016e\u016f\7u\2"+
		"\2\u016f\u0170\7q\2\2\u0170F\3\2\2\2\u0171\u0172\7u\2\2\u0172\u0173\7"+
		"g\2\2\u0173\u0174\7l\2\2\u0174\u0175\7c\2\2\u0175H\3\2\2\2\u0176\u0177"+
		"\7h\2\2\u0177\u0178\7k\2\2\u0178\u0179\7o\2\2\u0179\u017a\7a\2\2\u017a"+
		"\u017b\7e\2\2\u017b\u017c\7c\2\2\u017c\u017d\7u\2\2\u017d\u017e\7q\2\2"+
		"\u017eJ\3\2\2\2\u017f\u0180\7r\2\2\u0180\u0181\7c\2\2\u0181\u0182\7t\2"+
		"\2\u0182\u0183\7c\2\2\u0183L\3\2\2\2\u0184\u0185\7>\2\2\u0185\u0186\7"+
		"/\2\2\u0186N\3\2\2\2\u0187\u0188\7c\2\2\u0188\u0189\7v\2\2\u0189\u018a"+
		"\7g\2\2\u018aP\3\2\2\2\u018b\u018c\7h\2\2\u018c\u018d\7c\2\2\u018d\u018e"+
		"\7e\2\2\u018e\u018f\7c\2\2\u018fR\3\2\2\2\u0190\u0191\7h\2\2\u0191\u0192"+
		"\7k\2\2\u0192\u0193\7o\2\2\u0193\u0194\7a\2\2\u0194\u0195\7r\2\2\u0195"+
		"\u0196\7c\2\2\u0196\u0197\7t\2\2\u0197\u0198\7c\2\2\u0198T\3\2\2\2\u0199"+
		"\u019a\7g\2\2\u019a\u019b\7p\2\2\u019b\u019c\7s\2\2\u019c\u019d\7w\2\2"+
		"\u019d\u019e\7c\2\2\u019e\u019f\7p\2\2\u019f\u01a0\7v\2\2\u01a0\u01a1"+
		"\7q\2\2\u01a1V\3\2\2\2\u01a2\u01a3\7h\2\2\u01a3\u01a4\7k\2\2\u01a4\u01a5"+
		"\7o\2\2\u01a5\u01a6\7a\2\2\u01a6\u01a7\7g\2\2\u01a7\u01a8\7p\2\2\u01a8"+
		"\u01a9\7s\2\2\u01a9\u01aa\7w\2\2\u01aa\u01ab\7c\2\2\u01ab\u01ac\7p\2\2"+
		"\u01ac\u01ad\7v\2\2\u01ad\u01ae\7q\2\2\u01aeX\3\2\2\2\u01af\u01b0\7t\2"+
		"\2\u01b0\u01b1\7g\2\2\u01b1\u01b2\7v\2\2\u01b2\u01b3\7q\2\2\u01b3\u01b4"+
		"\7t\2\2\u01b4\u01b5\7p\2\2\u01b5\u01b6\7g\2\2\u01b6Z\3\2\2\2\u01b7\u01b8"+
		"\7\60\2\2\u01b8\u01b9\7\60\2\2\u01b9\\\3\2\2\2\u01ba\u01bb\7/\2\2\u01bb"+
		"^\3\2\2\2\u01bc\u01bd\7-\2\2\u01bd`\3\2\2\2\u01be\u01bf\7,\2\2\u01bfb"+
		"\3\2\2\2\u01c0\u01c1\7\61\2\2\u01c1d\3\2\2\2\u01c2\u01c3\7\'\2\2\u01c3"+
		"f\3\2\2\2\u01c4\u01c5\7(\2\2\u01c5h\3\2\2\2\u01c6\u01c7\7>\2\2\u01c7\u01c8"+
		"\7@\2\2\u01c8j\3\2\2\2\u01c9\u01ca\7@\2\2\u01ca\u01cb\7?\2\2\u01cbl\3"+
		"\2\2\2\u01cc\u01cd\7>\2\2\u01cd\u01ce\7?\2\2\u01cen\3\2\2\2\u01cf\u01d0"+
		"\7@\2\2\u01d0p\3\2\2\2\u01d1\u01d2\7>\2\2\u01d2r\3\2\2\2\u01d3\u01d4\7"+
		"p\2\2\u01d4\u01d5\7c\2\2\u01d5\u01d6\7q\2\2\u01d6t\3\2\2\2\u01d7\u01d8"+
		"\7q\2\2\u01d8\u01d9\7w\2\2\u01d9v\3\2\2\2\u01da\u01db\7g\2\2\u01dbx\3"+
		"\2\2\2\u01dc\u01dd\t\2\2\2\u01ddz\3\2\2\2\u01de\u01df\t\3\2\2\u01df|\3"+
		"\2\2\2\u01e0\u01e1\7\"\2\2\u01e1\u01e2\3\2\2\2\u01e2\u01e3\b?\2\2\u01e3"+
		"~\3\2\2\2\u01e4\u01e6\t\4\2\2\u01e5\u01e4\3\2\2\2\u01e6\u01e7\3\2\2\2"+
		"\u01e7\u01e8\b@\2\2\u01e8\u0080\3\2\2\2\u01e9\u01eb\5{>\2\u01ea\u01e9"+
		"\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01ea\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed"+
		"\u0082\3\2\2\2\u01ee\u01f0\5{>\2\u01ef\u01ee\3\2\2\2\u01f0\u01f1\3\2\2"+
		"\2\u01f1\u01ef\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2\u01f3\3\2\2\2\u01f3\u01f5"+
		"\7\60\2\2\u01f4\u01f6\5{>\2\u01f5\u01f4\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7"+
		"\u01f5\3\2\2\2\u01f7\u01f8\3\2\2\2\u01f8\u0084\3\2\2\2\u01f9\u01fd\t\5"+
		"\2\2\u01fa\u01fc\n\5\2\2\u01fb\u01fa\3\2\2\2\u01fc\u01ff\3\2\2\2\u01fd"+
		"\u01fb\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe\u0200\3\2\2\2\u01ff\u01fd\3\2"+
		"\2\2\u0200\u020a\t\5\2\2\u0201\u0205\7$\2\2\u0202\u0204\n\6\2\2\u0203"+
		"\u0202\3\2\2\2\u0204\u0207\3\2\2\2\u0205\u0203\3\2\2\2\u0205\u0206\3\2"+
		"\2\2\u0206\u0208\3\2\2\2\u0207\u0205\3\2\2\2\u0208\u020a\7$\2\2\u0209"+
		"\u01f9\3\2\2\2\u0209\u0201\3\2\2\2\u020a\u0086\3\2\2\2\u020b\u020e\5y"+
		"=\2\u020c\u020e\7a\2\2\u020d\u020b\3\2\2\2\u020d\u020c\3\2\2\2\u020e\u0214"+
		"\3\2\2\2\u020f\u0213\7a\2\2\u0210\u0213\5{>\2\u0211\u0213\5y=\2\u0212"+
		"\u020f\3\2\2\2\u0212\u0210\3\2\2\2\u0212\u0211\3\2\2\2\u0213\u0216\3\2"+
		"\2\2\u0214\u0212\3\2\2\2\u0214\u0215\3\2\2\2\u0215\u0088\3\2\2\2\u0216"+
		"\u0214\3\2\2\2\u0217\u021b\7}\2\2\u0218\u021a\n\7\2\2\u0219\u0218\3\2"+
		"\2\2\u021a\u021d\3\2\2\2\u021b\u0219\3\2\2\2\u021b\u021c\3\2\2\2\u021c"+
		"\u021e\3\2\2\2\u021d\u021b\3\2\2\2\u021e\u021f\7\177\2\2\u021f\u0220\3"+
		"\2\2\2\u0220\u0221\bE\2\2\u0221\u008a\3\2\2\2\u0222\u0226\7}\2\2\u0223"+
		"\u0225\n\7\2\2\u0224\u0223\3\2\2\2\u0225\u0228\3\2\2\2\u0226\u0224\3\2"+
		"\2\2\u0226\u0227\3\2\2\2\u0227\u0229\3\2\2\2\u0228\u0226\3\2\2\2\u0229"+
		"\u022a\7\f\2\2\u022a\u022b\bF\3\2\u022b\u008c\3\2\2\2\u022c\u022d\13\2"+
		"\2\2\u022d\u022e\bG\4\2\u022e\u008e\3\2\2\2\u022f\u0230\13\2\2\2\u0230"+
		"\u0231\bH\5\2\u0231\u0090\3\2\2\2\17\2\u01e5\u01ec\u01f1\u01f7\u01fd\u0205"+
		"\u0209\u020d\u0212\u0214\u021b\u0226\6\b\2\2\3F\2\3G\3\3H\4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}