package T1;

import LA.LABaseVisitor;
import LA.LAParser;
import org.antlr.v4.codegen.model.BaseVisitorFile;
import org.antlr.v4.runtime.Token;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* GeradprDeCodigo.java
*  Classe responsável pelos métodos de geração de código.
* */
public class GeradorDeCodigo extends LABaseVisitor<String> {
    static final String BLANK = " "; // String para espaço em branco
    static final String END_CMD_LINE = ";\n"; // String para pular linhas
    StringBuilder codigoC;
    String bufferId;
    boolean estaVisitandoRegistro;
    SaidaParser sp;
    private String bufferTipoExp; // Armazena tipo que a expressao retorna
    private Escopos pilhaDeEscopos;
    TabelaDeSimbolos tdsFunc, tdsProc, tdsGlobal, declAtributosStruct;
    PrintWriter pw;

    // Construtor
    public GeradorDeCodigo( SaidaParser sp, PrintWriter pw ) {
        this.sp = sp;
        bufferTipoExp = "inteiro";
        codigoC = new StringBuilder (  );
        this.pw = pw;
        bufferId = "";
        estaVisitandoRegistro = false;
        declAtributosStruct = new TabelaDeSimbolos ( "struct" );
    }
    
    // Método para escrever no código resultante
//    private String codigoC.append (String codigo) {
//        this.codigoC += BLANK + codigo + BLANK;
//        return codigoC;
//    }
//
//    // Método para escrever no código resultante
////    private String sp.print (String codigo) {
////        this.codigoC += BLANK + codigo + BLANK;
////        return codigoC;
////    }
//
//    // Método para transformar os tipos de variável em tipos em C
//    private String tipoParaC (String tipo) {
//        String novoTipo;
//        switch (tipo){
//            case "literal":
//                novoTipo = "char";
//                break;
//            case "inteiro":
//            case "logico":
//                novoTipo = "int";
//                break;
//            case "real":
//                novoTipo = "float";
//                break;
//            default:
//                novoTipo = tipo;
//        }
//        return novoTipo;
//    }
//
//    private String tipoParaFlagC (String tipo) {
//        String flag;
//        switch (tipo){
//            case "literal":
//                flag = "%c";
//                break;
//            case "initeiro":
//            case "logico":
//                flag = "%d";
//                break;
//            case "real":
//                flag = "float";
//                break;
//            default:
//                flag = "";
//        }
//        return flag;
//    }
//
//
//    /*
//    * Sobrecarga de operadores do visitor
//    * Alguns métodos retornam String, por escolha de implementação.
//    * */
//    @Override
//    public Object visitPrograma ( LAParser.ProgramaContext ctx ) {
//        pilhaDeEscopos = new Escopos(new TabelaDeSimbolos("global"));
//        sp.print (
//                "#include<stdio.h>\n" +
//                "#include<stdlib.h>\n\n"
//        );
//        super.visitPrograma ( ctx );
//        pilhaDeEscopos.desempilhar();
//        return null;
//    }
//
//    @Override
//    public Object visitCorpo ( LAParser.CorpoContext ctx ) {
//        StringBuffer bufferCmd = new StringBuffer (  );
//        if ( ctx.listaComandos != null )
//            for ( LAParser.CmdContext c : ctx.listaComandos )
//                bufferCmd.append ( BLANK + visitCmd ( c ).toString () + END_CMD_LINE );
//
//        StringBuffer bufferDL = new StringBuffer (  );
//        if ( ctx.listaComandos != null )
//            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
//                bufferDL.append ( BLANK + visitDeclaracao_local ( dl ).toString () + BLANK );
//
//        bufferCmd.append ( BLANK );
//        sp.print (
//                "int main () {\n" +
//                    bufferDL.toString () + BLANK + bufferCmd.toString () + BLANK
//                    + "return 0" + END_CMD_LINE +
//                "}\n"
//        );
//        pilhaDeEscopos.desempilhar ();
//        return null;
//    }
//
//    @Override
//    public Object visitVariavel ( LAParser.VariavelContext ctx ) {
//        String tipo = tipoParaC ( ctx.tipo().toString () );
//        StringBuffer buffer = new StringBuffer (  );
//        if ( ctx.identificador1 != null ) {
//            String id = visitIdentificador ( ctx.identificador1 ).toString ();
//            pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
//            buffer.append ( tipo + BLANK + id );
//        }
//        if ( ctx.outrosIdentificadores != null )
//            for (LAParser.IdentificadorContext idc : ctx.outrosIdentificadores){
//                String id = visitIdentificador ( idc ).toString ();
//                pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
//                buffer.append ( "," + BLANK + tipo + BLANK + id );
//            }
//
//        return buffer.toString ();
//    }
//
//    @Override
//    public Object visitIdentificador ( LAParser.IdentificadorContext ctx ) {
//        StringBuffer buffer = new StringBuffer (  );
//        buffer.append( ctx.ident1.toString () );
//        if ( ctx.outrosIdent != null ){
//            buffer.append( "." + ctx.outrosIdent.getText () );
//            String dimensao = visitDimensao ( ctx.dimensao () ).toString ();
//            if( dimensao != null )
//                buffer.append( dimensao );
//        }
//
//        return buffer.toString ();
//    }
//
//    @Override
//    public Object visitDimensao ( LAParser.DimensaoContext ctx ) {
//        StringBuilder bufferDimensao = new StringBuilder (  );
//        if ( ctx.exp_aritimetica () != null )
//            for ( LAParser.Exp_aritimeticaContext eac : ctx.exp_aritimetica () )
//                bufferDimensao.append( "[" ).append ( visitExp_aritimetica ( eac ) ).append ( "]" );
//        return bufferDimensao;
//    }
//
//    @Override
//    public Object visitDeclaracoes ( LAParser.DeclaracoesContext ctx ) {
//        List<LAParser.Decl_local_globalContext> listaDlg = ctx.decl_local_global ();
//        if( listaDlg != null )
//            for(LAParser.Decl_local_globalContext dlg : listaDlg)
//                visitDecl_local_global ( dlg );
//
//        return null;
//    }
//
//    @Override
//    public Object visitDecl_local_global ( LAParser.Decl_local_globalContext ctx ) {
//        visitDeclaracao_global_funcao ( (LAParser.Declaracao_global_funcaoContext) ctx.declaracao_global () );
//        visitDeclaracao_global_procedimento ( (LAParser.Declaracao_global_procedimentoContext) ctx.declaracao_global () );
//        visitDeclaracao_local ( ctx.declaracao_local () );
//        return null;
//    }
//
//    @Override
//    public Object visitValor_constante ( LAParser.Valor_constanteContext ctx ) {
//        String buffer;
//        String cte = ctx.getStart ().getText ();
//        switch (cte){
//            case "verdadeiro":
//                buffer = "true";
//                break;
//            case "falso":
//                buffer = "false";
//                break;
//            default:
//                buffer = cte;
//                break;
//        }
//        return buffer;
//    }
//
//    @Override
//    public Object visitDeclaracao_local ( LAParser.Declaracao_localContext ctx ) {
//        String dlid = ctx.getStart ().getText();
//        String id, tipo;
//        String buffer ;
//        switch (dlid) {
//            case "declare":
//                buffer = visitVariavel ( ctx.variavel ( ) ).toString ();
//                break;
//            case "constante":
//                tipo = tipoParaC ( ctx.tipo_basico ().getText () );
//                id = ctx.IDENT ().toString ();
//                String valorConstante = visitValor_constante ( ctx.valor_constante () ).toString ();
//                pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
//                buffer = "const " + tipo + BLANK + id + " = " + valorConstante + END_CMD_LINE;
//                break;
//            case "tipo":
//                tipo = ctx.tipo().toString ();
//                id = ctx.IDENT ().toString ();
//                pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
//                buffer = "typedef " + BLANK + tipo + BLANK + id + END_CMD_LINE;
//                break;
//            default: // Não vai chegar nesse caso antes de uma detecção sintática
//                return null;
//        }
//        return buffer;
//    }
//
//    @Override
//    public Object visitParametros ( LAParser.ParametrosContext ctx ) {
//        StringBuffer buffer = new StringBuffer (  );
//        if ( ctx.parametro1 != null ) buffer.append( visitParametro (ctx.parametro1) );
//        if ( ctx.outrosParametros != null )
//            for ( LAParser.ParametroContext p : ctx.outrosParametros ) {
//                System.out.println ();
//                buffer.append ( "," + visitParametro ( p ) );
//            }
//
//        return buffer.toString ();
//    }
//
//    @Override
//    public Object visitParametro ( LAParser.ParametroContext ctx ) {
//        String tipo = tipoParaC ( ctx.tipo_estendido ().getText ().replace("^","") );
//        StringBuilder buffer = new StringBuilder ( );
//        if ( ctx.identificador1 != null ) {
//            String id = visitIdentificador ( ctx.identificador1 ).toString ();
//            pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
//            buffer.append ( tipo ).append ( BLANK ).append ( id );
//        }
//
//        if ( ctx.outrosIdentificadores != null )
//            for( LAParser.IdentificadorContext i : ctx.outrosIdentificadores ){
//                String id = visitIdentificador ( i ).toString ();
//                buffer.append ( "," + BLANK ).append ( tipo ).append ( BLANK ).append ( id );
//            }
//
//        return buffer.toString ();
//    }
//
//    @Override
//    public Object visitDeclaracao_global_funcao ( LAParser.Declaracao_global_funcaoContext ctx ) {
//        pilhaDeEscopos.empilhar ( new TabelaDeSimbolos ( "funcao" ) );
//        String nome = ctx.IDENT ().toString ();
//        String tipo = ctx.tipo_estendido ().getText ().replace("^","*");
//        pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( nome, tipo );
//
//        StringBuilder bufferCmd = new StringBuilder (  );
//        if ( ctx.listaComandos != null )
//            for ( LAParser.CmdContext c : ctx.listaComandos )
//                bufferCmd.append ( BLANK ).append ( visitCmd ( c ).toString ( ) ).append ( END_CMD_LINE );
//
//        StringBuilder bufferDL = new StringBuilder (  );
//        if ( ctx.listaComandos != null )
//            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
//                bufferDL.append ( BLANK ).append ( visitDeclaracao_local ( dl ).toString ( ) ).append ( BLANK );
//
//        bufferCmd.append ( BLANK );
//        sp.print (
//                tipoParaC ( tipo )
//                + BLANK + nome + "(" + visitParametros(ctx.parametros ()) + ")"
//                + "{ " + bufferDL.toString () + BLANK + bufferCmd.toString () + " }"
//        );
//        pilhaDeEscopos.desempilhar ();
//        return null;
//    }
//
//    @Override
//    public Object visitDeclaracao_global_procedimento ( LAParser.Declaracao_global_procedimentoContext ctx ) {
//        pilhaDeEscopos.empilhar ( new TabelaDeSimbolos ( "procedimento" ) );
//        String nome = ctx.IDENT ().toString ();
//        String tipo = "void";
//        pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( nome, tipo );
//
//        StringBuilder bufferCmd = new StringBuilder (  );
//        if ( ctx.listaComandos != null )
//            for ( LAParser.CmdContext c : ctx.listaComandos )
//                bufferCmd.append ( BLANK ).append ( visitCmd ( c ).toString ( ) ).append ( END_CMD_LINE );
//
//        StringBuilder bufferDL = new StringBuilder (  );
//        if ( ctx.listaComandos != null )
//            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
//                bufferDL.append ( BLANK ).append ( visitDeclaracao_local ( dl ).toString ( ) ).append ( BLANK );
//
//        bufferCmd.append ( BLANK );
//        sp.print (
//                tipoParaC ( tipo )
//                        + BLANK + nome + "(" + visitParametros(ctx.parametros ()) + ")"
//                        + "{ " + bufferDL.toString () + BLANK + bufferCmd.toString () + " }"
//        );
//        pilhaDeEscopos.desempilhar ();
//        return null;
//    }
//
//    @Override
//    public Object visitCmd ( LAParser.CmdContext ctx ) {
//        String tokenInicio = ctx.getStart ().toString ();
//        String buffer;
//        switch (tokenInicio){
//            case "leia":
//                buffer = visitCmdLeia ( ctx.cmdLeia () ).toString ();
//                break;
//            case "escreva":
//                buffer = visitCmdEscreva ( ctx.cmdEscreva () ).toString ();
//                break;
//            case "se":
//                buffer = visitCmdSe ( ctx.cmdSe () ).toString ();
//                break;
//            case "caso":
//                buffer = visitCmdLeia ( ctx.cmdLeia () ).toString ();
//                break;
//            case "para":
//                buffer = visitCmdPara ( ctx.cmdPara () ).toString ();
//                break;
//            case "enquanto":
//                buffer = visitCmdEnquanto ( ctx.cmdEnquanto () ).toString ();
//                break;
//            case "faca":
//                buffer = visitCmdFaca ( ctx.cmdFaca () ).toString ();
//                break;
//            default:
//                if ( ctx.toString ().contains ( "<-" ) ){ // Atribuição
//                    buffer = visitCmdAtribuicao ( ctx.cmdAtribuicao () ).toString ();
//                } else if ( ctx.toString ().contains ( "(" ) && ctx.toString ().contains ( ")" ) ) { // Chamada
//                    buffer = visitCmdChamada ( ctx.cmdChamada () ).toString ();
//                } else {
//                    buffer = "";
//                }
//                break;
//        }
//        return buffer;
//    }
//
//    @Override
//    public Object visitCmdLeia ( LAParser.CmdLeiaContext ctx ) {
//        String bufferRetorno = "scanf(\"";
//        StringBuilder bufferIdent = new StringBuilder ( ", &" );
//        StringBuilder bufferTipo = new StringBuilder ( );
//
//        String tipo;
//        if ( ctx.id1 != null) {
//            String nomeEntrada= ctx.id1.IDENT ().toString ();
//            EntradaTabelaDeSimbolos ets = pilhaDeEscopos.escopoAtual ().getEntrada ( nomeEntrada );
//            if (ets != null) {
//                bufferIdent.append ( nomeEntrada );
//                tipo = ets.getTipo ();
//                bufferTipo.append ( tipo );
//            }
//        }
//        if ( ctx.outrosIds != null )
//            for (LAParser.IdentificadorContext ic: ctx.outrosIds ){
//                String nomeEntrada= ic.IDENT ().toString ();
//                EntradaTabelaDeSimbolos ets = pilhaDeEscopos.escopoAtual ().getEntrada ( nomeEntrada );
//                if (ets != null) {
//                    bufferIdent.append ( ", &" ).append ( nomeEntrada );
//                    tipo = ets.getTipo ();
//                    bufferTipo.append ( BLANK ).append ( tipo );
//                }
//            }
//
//            bufferTipo.append ("\"") ;
//        bufferRetorno += bufferIdent + ")" + END_CMD_LINE;
//        return bufferRetorno;
//    }
//
//    @Override
//    public Object visitCmdEscreva ( LAParser.CmdEscrevaContext ctx ) {
//        String bufferRetorno = "printf(";
//        // Se há cadeia de caracteres para printar:
//        StringBuilder bufferCadeia = new StringBuilder ( "\"" );
//        StringBuilder bufferVar = new StringBuilder ( );
//
//        for ( LAParser.ExpressaoContext expc: ctx.expressao() ){
//            String textoExp = visitExpressao ( expc ).toString ();
//            boolean ehCadeia = textoExp.startsWith ( "\"" );
//            if (ehCadeia){
//                bufferCadeia.append ( textoExp.replace ( "\"" , "" ) );
//            } else {
//                bufferVar.append ( ", " ).append ( textoExp );
//            }
//
//            switch (bufferTipoExp){
//                case "int":
//                    bufferCadeia.append ( "%d" );
//                    break;
//                case "real":
//                    bufferCadeia.append( "%f" );
//                    break;
//                case "literal":
//                    bufferCadeia.append( "%c" );
//                    break;
//                default:
//                    break;
//            }
//
//        }
//        bufferRetorno += bufferCadeia + "\")" + bufferVar;
//        return bufferRetorno;
//    }
//
//    @Override
//    public Object visitCmdSe ( LAParser.CmdSeContext ctx ) {
//        String bufferRetorno = "if ( "; // se
//        StringBuilder bufferCmdEntao = new StringBuilder (  );
//        StringBuilder bufferCmdSenao = new StringBuilder (  );
//
//        bufferRetorno += visitExpressao ( ctx.expressao () ) + " ) {\n";
//
//        if ( ctx.cmdEntao != null )
//            for ( LAParser.CmdContext c : ctx.cmdEntao )
//                bufferCmdEntao.append ( BLANK ).append ( visitCmd ( c ).toString ( ) );
//        bufferRetorno += bufferCmdEntao;
//
//        if ( ctx.cmdEntao != null ){
//            bufferRetorno += "} else {\n";
//            for ( LAParser.CmdContext c : ctx.cmdSenao )
//                bufferCmdSenao.append ( BLANK ).append ( visitCmd ( c ).toString ( ) );
//            bufferRetorno += bufferCmdSenao;
//        }
//
//        bufferRetorno += "}\n"; // fim_se
//        return bufferRetorno;
//    }
//
//    @Override
//    public Object visitCmdCaso ( LAParser.CmdCasoContext ctx ) {
//        String bufferRetorno = "switch(\n" + visitExp_aritimetica ( ctx.exp_aritimetica () ) + ") {\n";
//        StringBuilder bufferCmd = new StringBuilder (  );
//        bufferRetorno += visitSelecao ( ctx.selecao () );
//        if ( ctx.cmd() != null ) {
//            bufferRetorno  += "default:\n";
//            for ( LAParser.CmdContext c : ctx.cmd() )
//                bufferCmd.append ( BLANK ).append ( visitCmd ( c ).toString ( ) );
//            bufferRetorno += bufferCmd;
//        }
//        bufferRetorno += "}\n";
//        return bufferRetorno;
//    }
//
//    @Override
//    public Object visitSelecao ( LAParser.SelecaoContext ctx ) {
//        StringBuilder buffer = new StringBuilder (  );
//        if ( ctx.item_selecao () != null )
//            for (LAParser.Item_selecaoContext isc : ctx.item_selecao ())
//                buffer.append ( visitItem_selecao ( isc ) );
//        return buffer;
//    }
//
//    @Override
//    public Object visitItem_selecao ( LAParser.Item_selecaoContext ctx ) {
//        String buffer = visitConstantes( ctx.constantes () ).toString ();
//        StringBuilder bufferCmd = new StringBuilder (  );
//        if ( ctx.cmd() != null )
//            for (LAParser.CmdContext c : ctx.cmd ( ))
//                bufferCmd.append ( BLANK ).append ( visitCmd ( c ).toString ( ) );
//        bufferCmd.append( "break" ).append( END_CMD_LINE );
//        return buffer;
//    }
//
//    @Override
//    public Object visitConstantes ( LAParser.ConstantesContext ctx ) {
//        String buffer = visitNumero_intervalo ( ctx.numero_intervalo1 ).toString ();
//        StringBuilder bufferNumInter = new StringBuilder (  );
//        if ( ctx.outrosNumero_intervalo != null )
//            for ( LAParser.Numero_intervaloContext nic : ctx.outrosNumero_intervalo )
//                bufferNumInter.append( "\n" ).append( visitNumero_intervalo ( nic ).toString () ).append ( ":\n" );
//        buffer += bufferNumInter;
//        return buffer;
//    }
//
//    @Override
//    public Object visitNumero_intervalo ( LAParser.Numero_intervaloContext ctx ) {
//        if (ctx.op_unario2 != null && ctx.ni2 != null) return "case " + ctx.op_unario1.getText () + ctx.ni1.toString ();
//        else {
//            String buffer = "";
//            int ni1 = Integer.parseInt ( ctx.ni1.toString () );
//            int ni2 = Integer.parseInt ( ctx.ni2.toString () );
//            for (int i=ni1 + 1; i<=ni2; i+=1)
//                buffer += ":\ncase " + ctx.op_unario2.getText () + String.valueOf ( i );
//
//            return buffer;
//        }
//    }
//
//    @Override
//    public Object visitCmdPara ( LAParser.CmdParaContext ctx ) {
//        String id = ctx.IDENT ().toString ();
//        String buffer = "for(" + id + "=" + visitExp_aritimetica ( ctx.exp_aritmetica1 ) + ";" + id + "<=" + visitExp_aritimetica ( ctx.exp_aritmetica2 ) + "{\n";
//        StringBuilder bufferCmd = new StringBuilder (  );
//        if ( ctx.cmd() != null )
//            for (LAParser.CmdContext c : ctx.cmd ( ))
//                bufferCmd.append ( BLANK ).append ( visitCmd ( c ).toString ( ) );
//        buffer += bufferCmd;
//        buffer += "}\n";
//        return buffer;
//    }
//
//    @Override
//    public Object visitCmdEnquanto ( LAParser.CmdEnquantoContext ctx ) {
//        String buffer = "while(" + visitExpressao ( ctx.expressao () ) + "){\n";
//        StringBuilder bufferCmd = new StringBuilder (  );
//        if ( ctx.cmd() != null )
//            for (LAParser.CmdContext c : ctx.cmd ( ))
//                bufferCmd.append ( BLANK ).append ( visitCmd ( c ).toString ( ) );
//
//        buffer += bufferCmd;
//        buffer += "}\n";
//        return buffer;
//    }
//
//    @Override
//    public Object visitCmdFaca ( LAParser.CmdFacaContext ctx ) {
//        String buffer = "do {";
//        StringBuilder bufferCmd = new StringBuilder (  );
//        if ( ctx.cmd() != null )
//            for (LAParser.CmdContext c : ctx.cmd ( ))
//                bufferCmd.append ( BLANK ).append ( visitCmd ( c ).toString ( ) );
//
//        buffer += bufferCmd;
//        buffer += "} while(" + visitExpressao ( ctx.expressao () ) + "){" + END_CMD_LINE;
//        return buffer;
//    }
//
//    @Override
//    public Object visitCmdAtribuicao ( LAParser.CmdAtribuicaoContext ctx ) {
//        return visitIdentificador ( ctx.identificador () ) + " = " + visitExpressao ( ctx.expressao () ) + END_CMD_LINE;
//    }
//
//    @Override
//    public Object visitCmdChamada ( LAParser.CmdChamadaContext ctx ) {
//        String buffer = ctx.IDENT ().toString () + "("+ visitExpressao ( ctx.exp1 );
//        StringBuilder bufferExp = new StringBuilder (  );
//        if ( ctx.outrasExp != null)
//            for( LAParser.ExpressaoContext ec : ctx.outrasExp )
//                bufferExp.append ( ", " ).append ( visitExpressao ( ec ) );
//        buffer += ")" + END_CMD_LINE;
//        return buffer;
//    }
//
//    @Override
//    public Object visitCmdRetorne ( LAParser.CmdRetorneContext ctx ) {
//        return "return " + visitExpressao ( ctx.expressao () );
//    }
//
//    @Override
//    public Object visitExpressao ( LAParser.ExpressaoContext ctx ) {
//        String buffer = visitTermo_logico ( ctx.termo_l1 ).toString ();
//        StringBuilder bufferTermos = new StringBuilder (  );
//        if ( ctx.outrosTermos != null )
//            for ( LAParser.Termo_logicoContext tlc : ctx.outrosTermos ){
//                bufferTermos.append(" && ");        // Operador logico "e"
//                bufferTermos.append( visitTermo_logico (tlc) );
//            }
//        buffer += bufferTermos;
//        return buffer;
//    }
//
//    @Override
//    public Object visitTermo_logico ( LAParser.Termo_logicoContext ctx ) {
//        String buffer = ctx.fator_l1.toString ();
//        buffer = buffer.replace ("=", "==");     // Operador de igualdade
//        buffer = buffer.replace ("<>", "!=");    // Operador de diferença
//        buffer = buffer.replace (">==", ">=");   // Operador maior igual
//        buffer = buffer.replace ("<==", "<=");   // Operador menor igual
//        buffer = buffer.replace ("nao", "not");  // Operador de negacao
//        buffer = buffer.replace ("verdadeiro", "true");  // valor logico verdadeiro
//        buffer = buffer.replace ("falso", "false");      // valor logico falso
//
//        StringBuilder bufferTermos = new StringBuilder (  );
//        if ( ctx.outrosFatores != null )
//            for ( LAParser.Fator_logicoContext flc : ctx.outrosFatores){
//                bufferTermos.append(" || ");        // Operador logico "ou"
//                String texto = flc.getText ();
//                texto = texto.replace ("=", "==");     // Operador de igualdade
//                texto = texto.replace ("<>", "!=");    // Operador de diferença
//                texto = texto.replace (">==", ">=");   // Operador maior igual
//                texto = texto.replace ("<==", "<=");   // Operador menor igual
//                texto = texto.replace ("nao", "not");  // Operador de negacao
//                texto = texto.replace ("verdadeiro", "true");  // valor logico verdadeiro
//                texto = texto.replace ("falso", "false");      // valor logico falso
//                bufferTermos.append( texto );
//                visitFator_logico ( flc );
//            }
//        buffer += bufferTermos;
//        return buffer;
//    }
//
//    @Override
//    public Object visitParcela_unario_inteiro ( LAParser.Parcela_unario_inteiroContext ctx ) {
//        bufferTipoExp = "inteiro";
//        return null;
//    }
//
//    @Override
//    public Object visitParcela_unario_real ( LAParser.Parcela_unario_realContext ctx ) {
//        bufferTipoExp = "real;";
//        return null;
//    }
//
//    @Override
//    public Object visitParcela_nao_unario_cadeia ( LAParser.Parcela_nao_unario_cadeiaContext ctx ) {
//        bufferTipoExp = "cadeia";
//        return null;
//    }
//
//    @Override
//    public Object visitParcela_unario_id ( LAParser.Parcela_unario_idContext ctx ) {
//        String simbolo = visitIdentificador ( ctx.identificador () ).toString ();
//        if ( pilhaDeEscopos.escopoAtual ().getEntrada ( simbolo ) != null ) {
//            String tipo = pilhaDeEscopos.escopoAtual ().getEntrada ( simbolo ).getTipo ();
//            if ( !tipo.equals ( "void" ) ) {
//                bufferTipoExp = tipo;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Object visitExp_aritimetica ( LAParser.Exp_aritimeticaContext ctx ) {
//        return ctx.getText ();
//    }
//}

    // Método para transformar os tipos de variável em tipos em C
    private String tipoParaC (String tipo) {
        String ponteiro = "";
        if(tipo != null) {
            if ( tipo.startsWith ( "^" ) ) {
                tipo = tipo.replace ( "^" , "" );
                ponteiro = "*";
            }
            if ( tipo.startsWith ( "struct" ) ) return tipo;
            String novoTipo;
            switch (tipo) {
                case "literal":
                    novoTipo = "char*";
                    break;
                case "inteiro":
                case "logico":
                    novoTipo = "int";
                    break;
                case "real":
                    novoTipo = "float";
                    break;
                default:
                    novoTipo = tipo;
            }
            return novoTipo + ponteiro;
        } else {
            return null;
        }
    }

    private String tipoParaFlagC (String tipo) {
        String flag;
        switch (tipo){
            case "literal":
            case "char*":
                flag = "%s";
                break;
            case "inteiro":
            case "int":
            case "logico":
                flag = "%d";
                break;
            case "real":
            case "float":
                flag = "%f";
                break;
            default:
                flag = "";
        }
        return flag;
    }


    /*
    * Sobrecarga de operadores do visitor
    * Alguns métodos retornam String, por escolha de implementação.
    * */
    @Override
    public String visitPrograma ( LAParser.ProgramaContext ctx ) {
        //pw.print("hello! ");
        pilhaDeEscopos = new Escopos();
        tdsGlobal = new TabelaDeSimbolos("global");
        pilhaDeEscopos.empilhar ( tdsGlobal );
        codigoC.append (
                "#include<stdio.h>\n" +
                "#include<stdlib.h>\n\n"
        );
        //super.visitPrograma ( ctx );
        visitDeclaracoes ( ctx.declaracoes () );
//        if ( pilhaDeEscopos.escopoAtual ().vazia () ) pw.print("hello! ta vazia! ");
//        else pw.print("NAO TA VAZIA AQUI");
        visitCorpo ( ctx.corpo () );
        sp.println ( codigoC.toString () );
        pilhaDeEscopos.desempilhar();
        return codigoC.toString ();
    }

    @Override
    public String visitCorpo ( LAParser.CorpoContext ctx ) {
        StringBuilder bufferDL = new StringBuilder (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
                bufferDL.append ( BLANK ).append ( visitDeclaracao_local ( dl ) ).append ( BLANK );
//        pw.println ( pilhaDeEscopos.escopoAtual ().toString () );
        StringBuilder bufferCmd = new StringBuilder (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.CmdContext c : ctx.listaComandos )
                bufferCmd.append ( BLANK + visitCmd ( c ) );

        bufferCmd.append ( BLANK );
        codigoC.append (
                "int main () {\n" +
                    bufferDL.toString () + BLANK + bufferCmd.toString () + BLANK
                    + "return 0" + END_CMD_LINE +
                "}\n"
        );
        return null;
    }

    @Override
    public String visitVariavel ( LAParser.VariavelContext ctx ) {
        String tipo;
        if ( !ctx.getText ().contains ( "registro" ) ) {
            StringBuilder buffer = new StringBuilder ( );
            tipo = tipoParaC ( visitTipo ( ctx.tipo ( ) ) );
            if ( !ctx.identificador1.isEmpty ( ) ) {
                String id = visitIdentificador ( ctx.identificador1 );
                if ( !estaVisitandoRegistro )
                    pilhaDeEscopos.adicionaSimboloTopo ( id , tipo );
                else
                    declAtributosStruct.adicionarSimbolo ( id, tipo );

                if ( tipo.equals ( "char*" ) )
                    buffer.append ( tipo.replace("*","") ).append ( BLANK ).append ( id ).append("[80]");
                else
                    buffer.append ( tipo ).append ( BLANK ).append ( id );

                if ( tipo.equals ( tipoParaC ( tipo ) ) ) { // Typedef
                    for ( EntradaTabelaDeSimbolos ets : declAtributosStruct.getSimbolos () )
                        pilhaDeEscopos.adicionaSimboloTopo ( id + "." + ets.getNome (), ets.getTipo () );
                }
            }
            if ( !ctx.outrosIdentificadores.isEmpty ( ) )
                for (LAParser.IdentificadorContext idc : ctx.outrosIdentificadores) {
                    String id = visitIdentificador ( idc );
                    if ( !estaVisitandoRegistro )
                        pilhaDeEscopos.adicionaSimboloTopo ( id , tipo );
                    else
                        declAtributosStruct.adicionarSimbolo ( id, tipo );
                    buffer.append ( "," ).append ( BLANK ).append ( id );
                }
            return buffer.toString ( );

        } else {
            StringBuilder buffer = new StringBuilder ( visitTipo ( ctx.tipo () ) );
            tipo = "struct";
            if ( !ctx.identificador1.isEmpty ( ) ) {
                String id = visitIdentificador ( ctx.identificador1 );
                bufferId = id;
                pilhaDeEscopos.adicionaSimboloTopo ( id , tipo );
                buffer.append ( id );

                for (EntradaTabelaDeSimbolos ets : declAtributosStruct.getSimbolos ( )) {
                    pilhaDeEscopos.adicionaSimboloTopo ( id + "." + ets.getNome ( ) , ets.getTipo ( ) );
                }

            }

            return buffer.toString ();
        }
    }

    @Override
    public String visitIdentificador ( LAParser.IdentificadorContext ctx ) {
        StringBuffer buffer = new StringBuffer (  );
        buffer.append( ctx.ident1.getText () );
        if ( ctx.outrosIdent != null )
            for (Token idc : ctx.outrosIdent)
                buffer.append ( "." ).append ( idc.getText ( ) );

        String dimensao = visitDimensao ( ctx.dimensao () );
        if( dimensao != null )
            buffer.append( dimensao );

        return buffer.toString ();
    }

    @Override
    public String visitDimensao ( LAParser.DimensaoContext ctx ) {
        StringBuilder bufferDimensao = new StringBuilder (  );
        if ( !ctx.exp_aritimetica ().isEmpty () )
            for ( LAParser.Exp_aritimeticaContext eac : ctx.exp_aritimetica () )
                bufferDimensao.append( "[" ).append ( visitExp_aritimetica ( eac ) ).append ( "]" );

        return bufferDimensao.toString ();
    }

    @Override
    public String visitDeclaracoes ( LAParser.DeclaracoesContext ctx ) {
        if( !ctx.decl_local_global ().isEmpty () )
            for (LAParser.Decl_local_globalContext dlg : ctx.decl_local_global ())
                visitDecl_local_global ( dlg );

        return null;
    }

    @Override
    public String visitDecl_local_global ( LAParser.Decl_local_globalContext ctx ) {
//        visitDeclaracao_global_funcao ( (LAParser.Declaracao_global_funcaoContext) ctx.declaracao_global () );
//        visitDeclaracao_global_procedimento ( (LAParser.Declaracao_global_procedimentoContext) ctx.declaracao_global () );
//        visitDeclaracao_local ( ctx.declaracao_local () );
        super.visitDecl_local_global ( ctx );
        return null;
    }

    @Override
    public String visitValor_constante ( LAParser.Valor_constanteContext ctx ) {
        String buffer;
        String cte = ctx.getStart ().getText ();
        switch (cte){
            case "verdadeiro":
                buffer = "true";
                break;
            case "falso":
                buffer = "false";
                break;
            default:
                buffer = cte;
                break;
        }
        return buffer;
    }

    @Override
    public String visitDeclaracao_local ( LAParser.Declaracao_localContext ctx ) {
        String dlid = ctx.getStart ().getText();
        String id, tipo;
        String buffer ;
        switch (dlid) {
            case "declare":
                buffer = visitVariavel ( ctx.variavel ( ) );
                if ( buffer.startsWith ( "char" ) ) buffer += "[80]";

                buffer += END_CMD_LINE;

                break;
            case "constante":
                tipo = tipoParaC ( ctx.tipo_basico ().getText () );
                id = ctx.IDENT ().getText ();
                String valorConstante = visitValor_constante ( ctx.valor_constante () );
                pilhaDeEscopos.adicionaSimboloTopo ( id, tipo );
                codigoC.append ( "#define " ).append ( id ).append ( BLANK ).append ( valorConstante ).append ( "\n\n" );
                return null;
            case "tipo":
                tipo = tipoParaC ( visitTipo(ctx.tipo()) );
                id = ctx.IDENT ().getText ();
                bufferId = id;
                pilhaDeEscopos.adicionaSimboloTopo ( id, "struct" );
                if ( !declAtributosStruct.getSimbolos ().isEmpty () )
                    for ( EntradaTabelaDeSimbolos ets : declAtributosStruct.getSimbolos () )
                        pilhaDeEscopos.adicionaSimboloTopo ( id + "." + ets.getNome (), ets.getTipo () );

                buffer = "typedef " + BLANK + tipo + BLANK + id + END_CMD_LINE;
                break;
            default:
                return null;
        }
        return buffer;
    }

    @Override
    public String visitParametros ( LAParser.ParametrosContext ctx ) {
        StringBuffer buffer = new StringBuffer (  );
        if ( ctx.parametro1 != null ) buffer.append( visitParametro (ctx.parametro1) );
        if ( ctx.outrosParametros != null )
            for ( LAParser.ParametroContext p : ctx.outrosParametros ) {
                buffer.append ( "," + visitParametro ( p ) );
            }

        return buffer.toString ();
    }

    @Override
    public String visitParametro ( LAParser.ParametroContext ctx ) {
        String tipo = tipoParaC ( ctx.tipo_estendido ().getText ().replace("^","*") );
        StringBuilder buffer = new StringBuilder ( );
        if ( !ctx.identificador1.isEmpty () ) {
            String id = visitIdentificador ( ctx.identificador1 );
            pilhaDeEscopos.adicionaSimboloTopo ( id, tipo );
            buffer.append ( tipo ).append ( BLANK ).append ( id );
        }

        if ( !ctx.outrosIdentificadores.isEmpty () )
            for( LAParser.IdentificadorContext i : ctx.outrosIdentificadores ){
                String id = visitIdentificador ( i );
                pilhaDeEscopos.adicionaSimboloTopo ( id, tipo );
                buffer.append ( "," + BLANK ).append ( tipo ).append ( BLANK ).append ( id );
            }
//        pw.println ( pilhaDeEscopos.escopoAtual ().toString () );
        return buffer.toString ();
    }

    @Override
    public String visitDeclaracao_global_funcao ( LAParser.Declaracao_global_funcaoContext ctx ) {
        tdsFunc = new TabelaDeSimbolos ( "funcao" );
        pilhaDeEscopos.empilhar ( tdsFunc );
        String nome = ctx.IDENT ().toString ();
        String tipo = ctx.tipo_estendido ().getText ().replace("^","*");
        pilhaDeEscopos.adicionaSimboloTopo ( nome, tipo );

        StringBuilder bufferDL = new StringBuilder (  );
        if ( !ctx.listaComandos.isEmpty () )
            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
                bufferDL.append ( BLANK ).append ( visitDeclaracao_local ( dl ) ).append ( BLANK );

        StringBuilder bufferCmd = new StringBuilder (  );
        if ( !ctx.listaComandos.isEmpty () )
            for ( LAParser.CmdContext c : ctx.listaComandos )
                bufferCmd.append ( BLANK ).append ( visitCmd ( c ) );

        bufferCmd.append ( BLANK );
        codigoC.append ( tipoParaC ( tipo ) ).append ( BLANK ).append ( nome ).append ( "(" ).append ( visitParametros ( ctx.parametros ( ) ) ).append ( ")" ).append ( "{\n " ).append ( bufferDL.toString ( ) ).append ( BLANK ).append ( bufferCmd.toString ( ) ).append ( "\n}\n\n" );
        pilhaDeEscopos.desempilhar ();
        return null;
    }

    @Override
    public String visitDeclaracao_global_procedimento ( LAParser.Declaracao_global_procedimentoContext ctx ) {
        tdsProc = new TabelaDeSimbolos ( "procedimento" );
        pilhaDeEscopos.empilhar ( tdsProc );
        String nome = ctx.IDENT ().toString ();
        String tipo = "void";
        pilhaDeEscopos.adicionaSimboloTopo ( nome, tipo );

        StringBuilder bufferDL = new StringBuilder (  );
        if ( !ctx.listaDL.isEmpty ())
            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
                bufferDL.append ( BLANK ).append ( visitDeclaracao_local ( dl ) ).append ( BLANK );

        codigoC.append ( tipoParaC ( tipo ) ).append ( BLANK ).append ( nome ).append ( "(" ).append ( visitParametros ( ctx.parametros ( ) ) );
        codigoC.append ( ")" ).append ( "{\n " ).append ( bufferDL.toString ( ) );

        StringBuilder bufferCmd = new StringBuilder (  );
        if ( !ctx.listaComandos.isEmpty () )
            for ( LAParser.CmdContext c : ctx.listaComandos )
                bufferCmd.append ( BLANK ).append ( visitCmd ( c ) );
        bufferCmd.append ( BLANK );

        codigoC.append ( BLANK ).append ( bufferCmd.toString ( ) ).append ( " \n}\n" );
        pilhaDeEscopos.desempilhar ();
        return null;
    }

    @Override
    public String visitCmdLeia ( LAParser.CmdLeiaContext ctx ) {
        String bufferRetorno = "scanf(\"";
        StringBuilder bufferIdent = new StringBuilder ( "\", &" );
        StringBuilder bufferTipo = new StringBuilder ( );
        String tipo = null;
        if ( ctx.id1 != null) {
            String nomeEntrada= visitIdentificador ( ctx.id1 );
            EntradaTabelaDeSimbolos ets = pilhaDeEscopos.escopoAtual ().getEntrada ( nomeEntrada );
            bufferIdent.append ( nomeEntrada );
            if (ets != null) {
                tipo = tipoParaFlagC ( tipoParaC ( ets.getTipo () ) );
                bufferTipo.append ( tipo );
            }
        }
        if ( ctx.outrosIds != null )
            for (LAParser.IdentificadorContext ic: ctx.outrosIds ){
                String nomeEntrada= ic.IDENT ().toString ();
                EntradaTabelaDeSimbolos ets = pilhaDeEscopos.escopoAtual ().getEntrada ( nomeEntrada );
                if (ets != null) {
                    bufferIdent.append ( ", &" ).append ( nomeEntrada );
                    tipo = tipoParaFlagC ( tipoParaC ( ets.getTipo () ) );
                    bufferTipo.append ( BLANK ).append ( tipo );
                }
            }
        if ( tipo != null && tipo.equals ( "%s" ) )
            bufferRetorno = "gets(" + bufferIdent.toString ().replace ( "\"," , "" ).replace ( "&", "" ).replace ( " ", "" ) + ")";
        else
            bufferRetorno += bufferTipo.toString ( ) + bufferIdent.toString ( ) + ")";

        return bufferRetorno + END_CMD_LINE;
    }

    @Override
    public String visitCmdEscreva ( LAParser.CmdEscrevaContext ctx ) {
        // Se há cadeia de caracteres para printar:
//        pw.println(pilhaDeEscopos.escopoAtual ().toString ());
        StringBuilder bufferRetorno = new StringBuilder ( );
        for ( LAParser.ExpressaoContext expc: ctx.expressao() ){
            StringBuilder bufferCadeia = new StringBuilder ( "\"" );
            StringBuilder bufferVar = new StringBuilder ( );
            String textoExp = visitExpressao ( expc );
            boolean ehCadeia = textoExp.startsWith ( "\"" );
            if (ehCadeia){
                bufferCadeia.append ( textoExp.replace ( "\"" , "" ) );
            } else {
                bufferVar.append ( ", " ).append ( textoExp );
            }
            if (!pilhaDeEscopos.escopoAtual ().existeSimbolo ( textoExp )) {
                switch (bufferTipoExp) {
                    case "inteiro":
                    case "int":
                        bufferCadeia.append ( "%d" );
                        break;
                    case "real":
                    case "float":
                        bufferCadeia.append ( "%f" );
                        break;
                    case "literal":
                    case "char*":
                        bufferCadeia.append ( "%s" );
                        break;
                    default:
                        break;
                }
            } else {
                bufferCadeia.append( tipoParaFlagC ( pilhaDeEscopos.escopoAtual ().getEntrada ( textoExp ).getTipo () ) );
            }
            bufferRetorno.append ( "printf(" ).append ( bufferCadeia.toString ( ) ).append ( "\"" ).append ( bufferVar.toString ( ) ).append ( ")" ).append ( END_CMD_LINE );
        }

        return bufferRetorno.toString ( );
    }

    @Override
    public String visitCmdSe ( LAParser.CmdSeContext ctx ) {
        String bufferRetorno = "if ( "; // se
        StringBuilder bufferCmdEntao = new StringBuilder (  );
        StringBuilder bufferCmdSenao = new StringBuilder (  );

        bufferRetorno += visitExpressao ( ctx.expressao () ) + " ) {\n";

        if ( !ctx.cmdEntao.isEmpty () )
            for ( LAParser.CmdContext c : ctx.cmdEntao )
                bufferCmdEntao.append ( BLANK ).append ( visitCmd ( c ) );
        bufferRetorno += bufferCmdEntao;

        if ( !ctx.cmdSenao.isEmpty () ){
            bufferRetorno += "\n} else {\n";
            for ( LAParser.CmdContext c : ctx.cmdSenao )
                bufferCmdSenao.append ( BLANK ).append ( visitCmd ( c ) );
            bufferRetorno += bufferCmdSenao;
        }

        bufferRetorno += "}\n"; // fim_se
        return bufferRetorno;
    }

    @Override
    public String visitCmdCaso ( LAParser.CmdCasoContext ctx ) {
        String bufferRetorno = "switch(" + visitExp_aritimetica ( ctx.exp_aritimetica () ) + ") {\n";
        StringBuilder bufferCmd = new StringBuilder (  );
        bufferRetorno += visitSelecao ( ctx.selecao () );
        if ( !ctx.cmd().isEmpty () ) {
            bufferRetorno  += "\ndefault:\n";
            for ( LAParser.CmdContext c : ctx.cmd() )
                bufferCmd.append ( BLANK ).append ( visitCmd ( c ) );
            bufferRetorno += bufferCmd;
        }
        bufferRetorno += "}\n";
        return bufferRetorno;
    }

    @Override
    public String visitSelecao ( LAParser.SelecaoContext ctx ) {
        StringBuilder buffer = new StringBuilder (  );
        if ( ctx.item_selecao () != null )
            for (LAParser.Item_selecaoContext isc : ctx.item_selecao ())
                buffer.append ( visitItem_selecao ( isc ) );
        return buffer.toString ();
    }

    @Override
    public String visitItem_selecao ( LAParser.Item_selecaoContext ctx ) {
        String buffer = visitConstantes( ctx.constantes () );
        StringBuilder bufferCmd = new StringBuilder (  );
        if ( !ctx.cmd().isEmpty () )
            for (LAParser.CmdContext c : ctx.cmd ( ))
                bufferCmd.append ( BLANK ).append ( visitCmd ( c ) );
        bufferCmd.append( "break" ).append( END_CMD_LINE );
        return buffer + bufferCmd.toString ();
    }

    @Override
    public String visitConstantes ( LAParser.ConstantesContext ctx ) {
        String buffer = visitNumero_intervalo ( ctx.numero_intervalo1 );
        StringBuilder bufferNumInter = new StringBuilder (  );
        if ( ctx.outrosNumero_intervalo.isEmpty () )
            for ( LAParser.Numero_intervaloContext nic : ctx.outrosNumero_intervalo )
                bufferNumInter.append( "\n" ).append( visitNumero_intervalo ( nic ) ).append ( ":\n" );
        buffer += bufferNumInter.toString ();
        return buffer;
    }

    @Override
    public String visitNumero_intervalo ( LAParser.Numero_intervaloContext ctx ) {
        String buffer = "";
        if( ctx.op_unario1 != null )
            buffer += "case " + ctx.op_unario1.getText () + ctx.ni1.getText ();
        else
            buffer += "case " +  ctx.ni1.getText ();
        buffer +=  ":\n";
        if ( ctx.ni2 != null ) {
            StringBuilder bufferOpUnario2 = new StringBuilder (  );
            if ( !ctx.op_unario2.isEmpty () )
                for ( LAParser.Op_unarioContext ouc : ctx.op_unario2 )
                    bufferOpUnario2.append ( ouc.getText () );

            int ni1 = Integer.parseInt ( ctx.ni1.getText () );
            int ni2 = Integer.parseInt ( ctx.ni2.getText ( ) );
//            pw.println ( ctx.op_unario2.toString ( ) );
            for (int i = ni1+1; i <= ni2; i += 1)
                if ( i == ni1+1 )
                    buffer += "case " + bufferOpUnario2.toString ( ) + String.valueOf ( i );
                else
                    buffer += ":\ncase " + bufferOpUnario2.toString ( ) + String.valueOf ( i );
            buffer += ":\n";
        }


        return buffer;
    }

    @Override
    public String visitCmdPara ( LAParser.CmdParaContext ctx ) {
        String id = ctx.IDENT ().toString ();
        String buffer = "for(" + id + "=" + visitExp_aritimetica ( ctx.exp_aritmetica1 ) + ";" + id + "<=" + visitExp_aritimetica ( ctx.exp_aritmetica2 ) + ";" + id + "++){\n";
        StringBuilder bufferCmd = new StringBuilder (  );
        if ( ctx.cmd() != null )
            for (LAParser.CmdContext c : ctx.cmd ( ))
                bufferCmd.append ( BLANK ).append ( visitCmd ( c ) );
        buffer += bufferCmd;
        buffer += "\n}\n";
        return buffer;
    }

    @Override
    public String visitCmdEnquanto ( LAParser.CmdEnquantoContext ctx ) {
        String buffer = "while(" + visitExpressao ( ctx.expressao () ) + "){\n";
        StringBuilder bufferCmd = new StringBuilder (  );
        if ( ctx.cmd() != null )
            for (LAParser.CmdContext c : ctx.cmd ( ))
                bufferCmd.append ( BLANK ).append ( visitCmd ( c ) );

        buffer += bufferCmd;
        buffer += "\n}\n";
        return buffer;
    }

    @Override
    public String visitCmdFaca ( LAParser.CmdFacaContext ctx ) {
        String buffer = "do {";
        StringBuilder bufferCmd = new StringBuilder (  );
        if ( ctx.cmd() != null )
            for (LAParser.CmdContext c : ctx.cmd ( ))
                bufferCmd.append ( BLANK ).append ( visitCmd ( c ) );

        buffer += bufferCmd;
        buffer += "\n} while(" + visitExpressao ( ctx.expressao () ) + ")" + END_CMD_LINE;
        return buffer;
    }

    @Override
    public String visitCmdAtribuicao ( LAParser.CmdAtribuicaoContext ctx ) {
        String buffer = "";
        String id = visitIdentificador ( ctx.identificador () );
        String expr = visitExpressao ( ctx.expressao () );
        if( pilhaDeEscopos.escopoAtual ().existeSimbolo ( id ) &&
            pilhaDeEscopos.escopoAtual ().getEntrada ( id ).getTipo ().contains ( "*" ) &&
            !expr.contains ( "&" )
        )
            buffer += "*";

        buffer += id + " = " + expr + END_CMD_LINE;
        if ( pilhaDeEscopos.escopoAtual ().existeSimbolo ( id ) &&
             pilhaDeEscopos.escopoAtual ( ).getEntrada ( id ).getTipo ( ).equals ( "char*" )
        )
            buffer = "strcpy(" + buffer.replace(" = ", ", ").replace(END_CMD_LINE, ")" + END_CMD_LINE).replace("*", "");
        return buffer;
    }

    @Override
    public String visitTipo ( LAParser.TipoContext ctx ) {
        return super.visitTipo ( ctx );
    }

    @Override
    public String visitTipo_basico ( LAParser.Tipo_basicoContext ctx ) {
        return ctx.getText ();
    }

    @Override
    public String visitTipo_basico_ident ( LAParser.Tipo_basico_identContext ctx ) {
        return super.visitTipo_basico_ident ( ctx );
    }

    @Override
    public String visitTipo_estendido ( LAParser.Tipo_estendidoContext ctx ) {
        String tipo = ctx.getText ();
//        pw.println ( tipo );
//        pw.println ( pilhaDeEscopos.escopoAtual ().existeStructId ( tipo ) );
//        pw.println ( pilhaDeEscopos.escopoAtual ().toString () );
        return tipo;
    }

    @Override
    public String visitRegistro ( LAParser.RegistroContext ctx ) {
        estaVisitandoRegistro = true;
        StringBuilder bufferRetorno = new StringBuilder ( "struct {\n" );
        StringBuilder bufferVar = new StringBuilder (  );
        //pw.println(bufferId);
        if ( !ctx.variavel().isEmpty () )
            for (LAParser.VariavelContext vc : ctx.variavel () ) {
                bufferVar.append ( visitVariavel ( vc ) ).append ( END_CMD_LINE );
            }

        bufferRetorno.append (bufferVar).append ("} ");
        estaVisitandoRegistro = false;
        return bufferRetorno.toString ();
    }

    @Override
    public String visitCmdChamada ( LAParser.CmdChamadaContext ctx ) {
        String buffer = ctx.IDENT ().toString () + "("+ visitExpressao ( ctx.exp1 );
        StringBuilder bufferExp = new StringBuilder (  );
        if ( ctx.outrasExp != null)
            for( LAParser.ExpressaoContext ec : ctx.outrasExp )
                bufferExp.append ( ", " ).append ( visitExpressao ( ec ) );
        buffer += ")" + END_CMD_LINE;
        return buffer;
    }

    @Override
    public String visitCmdRetorne ( LAParser.CmdRetorneContext ctx ) {
        return "return " + visitExpressao ( ctx.expressao () ) + END_CMD_LINE;
    }

    @Override
    public String visitExpressao ( LAParser.ExpressaoContext ctx ) {
        String buffer = visitTermo_logico ( ctx.termo_l1 );
        StringBuilder bufferTermos = new StringBuilder (  );
        if ( ctx.outrosTermos != null )
            for ( LAParser.Termo_logicoContext tlc : ctx.outrosTermos ){
                bufferTermos.append(" || ");        // Operador logico "e"
                bufferTermos.append( visitTermo_logico (tlc) );
            }
        buffer += bufferTermos.toString ();
        return buffer;
    }

    @Override
    public String visitTermo_logico ( LAParser.Termo_logicoContext ctx ) {
        String buffer = ctx.fator_l1.getText ();
        buffer = buffer.replace ("=", "==");     // Operador de igualdade
        buffer = buffer.replace ("<>", "!=");    // Operador de diferença
        buffer = buffer.replace (">==", ">=");   // Operador maior igual
        buffer = buffer.replace ("<==", "<=");   // Operador menor igual
        buffer = buffer.replace ("nao", "!");  // Operador de negacao
        buffer = buffer.replace ("verdadeiro", "true");  // valor logico verdadeiro
        buffer = buffer.replace ("falso", "false");      // valor logico falso
        super.visitFator_logico ( ctx.fator_l1 );

        StringBuilder bufferTermos = new StringBuilder (  );
        if ( !ctx.outrosFatores.isEmpty () )
            for ( LAParser.Fator_logicoContext flc : ctx.outrosFatores){
                bufferTermos.append(" && ");        // Operador logico "e"
                String texto = flc.getText ();
                texto = texto.replace ("=", "==");     // Operador de igualdade
                texto = texto.replace ("<>", "!=");    // Operador de diferença
                texto = texto.replace (">==", ">=");   // Operador maior igual
                texto = texto.replace ("<==", "<=");   // Operador menor igual
                texto = texto.replace ("nao", "!");  // Operador de negacao
                texto = texto.replace ("verdadeiro", "true");  // valor logico verdadeiro
                texto = texto.replace ("falso", "false");      // valor logico falso
                bufferTermos.append( texto );
                super.visitFator_logico ( flc );
            }

        buffer += bufferTermos.toString ();
        return buffer;
    }

    @Override
    public String visitParcela_unario_inteiro ( LAParser.Parcela_unario_inteiroContext ctx ) {
        bufferTipoExp = "inteiro";
        return null;
    }

    @Override
    public String visitParcela_unario_real ( LAParser.Parcela_unario_realContext ctx ) {
        bufferTipoExp = "real;";
        return null;
    }

    @Override
    public String visitParcela_nao_unario_cadeia ( LAParser.Parcela_nao_unario_cadeiaContext ctx ) {
        bufferTipoExp = "cadeia";
        return null;
    }

    @Override
    public String visitParcela_unario_id ( LAParser.Parcela_unario_idContext ctx ) {
        String simbolo = visitIdentificador ( ctx.identificador () );
        if ( pilhaDeEscopos.escopoAtual ().getEntrada ( simbolo ) != null ) {
            String tipo = pilhaDeEscopos.escopoAtual ( ).getEntrada ( simbolo ).getTipo ( );
            if ( !tipo.equals ( "void" ) ) {
                bufferTipoExp = tipo;
            }
        }
        return null;
    }

    @Override
    public String visitExp_aritimetica ( LAParser.Exp_aritimeticaContext ctx ) {
        super.visitExp_aritimetica ( ctx );
        return ctx.getText ();
    }
}
