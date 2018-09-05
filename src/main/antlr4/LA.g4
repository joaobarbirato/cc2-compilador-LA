grammar LA;

@lexer::members {
   void erroLexico(String mensagem) {
      throw new ParseCancellationException(mensagem);
   }
}

fragment LETRA: [a-zA-Z];
fragment ALGARISMO: [0-9];

WS:   (' ') -> skip;
ENDL:  ([\n] | [\t] | [\r]) -> skip;

NUM_INT: (ALGARISMO)+;

NUM_REAL: (ALGARISMO)+ '.' (ALGARISMO)+;

CADEIA: ([\\'] (~[\\'])* [\\']) | ('"' (~'"')* '"');

IDENT: LETRA (ALGARISMO|LETRA)*;

COMENTARIO: '{' ~('}'|'\n'|'\r')* '}' -> skip;

COMENTARIO_NAO_FECHADO: '{' ~('}'|'\n'|'\r')* '\n' { { erroLexico("Linha "+getLine()+": comentario nao fechado"); }; };

ERRO: . { erroLexico("Linha "+getLine()+": "+getText()+" - simbolo nao identificado"); };

/* Programas são constituidos de declarações e um corpo do código */
programa : declaracoes 'algoritmo' corpo 'fim_algoritmo';

/* Declarações podem ser globais ou locais e se referem a variáveis, constantes e seus
respectivos tipos */
declaracoes : (decl_local_global)*;
decl_local_global : declaracao_local | declaracao_global;
declaracao_local : 	'declare' variavel
				 |	'constante' IDENT ':' tipo_basico '=' valor_constante
				 |	'tipo' IDENT ':' tipo ;

/* Variáveis são constituidas de identificadores */
variavel : identificador (',' identificador)* ':' tipo ;
identificador : IDENT ('.' IDENT)* dimensao ;
dimensao : ('[' exp_aritimetica ']')* ;

/* Cada variável (ou constante) possui seu tipo especificado */
tipo : 	registro
	 |	tipo_estendido ;

tipo_basico : 'literal' | 'inteiro' | 'real' | 'logico' ;
tipo_basico_ident : tipo_basico | IDENT ;
tipo_estendido : ('^')? tipo_basico_ident ;
valor_constante : CADEIA | NUM_INT | NUM_REAL | 'verdadeiro' | 'falso' ;
registro : 'registro' (variavel)* 'fim_registro' ;

/* As declarações globais indicam funções ou procedimentos, onde estão explicitados seus
parâmetros e comandos */
declaracao_global : 'procedimento' IDENT '(' (parametros)? ')' (declaracao_local)* (cmd)* 'fim_procedimento'
				  | 'funcao' IDENT '(' (parametros)? ')' ':' tipo_estendido (declaracao_local)* (cmd)* 'fim_funcao' ;

parametro : ('var')? identificador (',' identificador)* ':' tipo_estendido ;
parametros : parametro (',' parametro)* ;
corpo : (declaracao_local)* (cmd)* ;

/* Comandos definem qualquer tipo de chamada do programa */

cmd : cmdLeia | cmdEscreva | cmdSe | cmdCaso | cmdPara | cmdEnquanto | cmdFaca |
	  cmdAtribuicao | cmdChamada | cmdRetorne ;

cmdLeia : 'leia' '(' ('^')? identificador (',' ('^')? identificador)* ')' ;
cmdEscreva : 'escreva' '(' expressao (',' expressao)* ')' ;
cmdSe : 'se' expressao 'entao' (cmd)* ('senao' (cmd)*)? 'fim_se' ;
cmdCaso : 'caso' exp_aritimetica 'seja' selecao ('senao' (cmd)*)? 'fim_caso' ;
cmdPara : 'para' IDENT '<-'exp_aritimetica 'ate' exp_aritimetica 'faca' (cmd)* 'fim_para' ;
cmdEnquanto :  'enquanto' expressao 'faca' (cmd)* 'fim_enquanto' ;
cmdFaca : 'faca' (cmd)* 'ate' expressao ;
cmdAtribuicao : ('^')? identificador '<-' expressao ;
cmdChamada : IDENT '(' expressao (',' expressao)* ')' ;
cmdRetorne : 'retorne' expressao ;

/* Regras utilizadas para o cmdCaso */
selecao : (item_selecao)* ;
item_selecao : constantes ':' (cmd)* ;
constantes : numero_intervalo (',' numero_intervalo)* ;
numero_intervalo : (op_unario)? NUM_INT ('..' (op_unario)? NUM_INT)? ;

/* Precedência de operadores */
op_unario : '-' ;
exp_aritimetica : termo (op1 termo)* ;
termo : fator (op2 fator)* ;
fator : parcela (op3 parcela)* ;
op1 : '+' | '-' ;
op2 : '*' | '/' ;
op3 : '%' ;

parcela : (op_unario)? parcela_unario | parcela_nao_unario ;
parcela_unario : ('^')? identificador
			   | IDENT '(' expressao (',' expressao)* ')'
			   | NUM_INT
			   | NUM_REAL
			   | '(' expressao ')' ;

parcela_nao_unario : '&' identificador | CADEIA ;
exp_relacional : exp_aritimetica (op_relacional exp_aritimetica)? ;
op_relacional : '=' | '<>' | '>=' | '<=' | '>' | '<' ;
expressao : termo_logico (op_logico_1 termo_logico)* ;
termo_logico : fator_logico (op_logico_2 fator_logico)* ;
fator_logico : ('nao')? parcela_logica ;
parcela_logica : ( 'verdadeiro' | 'falso') | exp_relacional ;
op_logico_1 : 'ou' ;
op_logico_2 : 'e' ;
