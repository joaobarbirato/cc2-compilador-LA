//package T1;
//
//import LA.LABaseVisitor;
//import LA.LAParser;
//import org.antlr.v4.codegen.model.BaseVisitorFile;
//
//import java.util.ArrayList;
//import java.util.List;
//
///* GeradprDeCodigo.java
//*  Classe responsável pelos métodos de geração de código.
//* */
//public class GeradorDeCodigo extends LABaseVisitor {
//    static final String BLANK = " "; // String para espaço em branco
//    static final String END_CMD_LINE = ";\n"; // String para pular linhas
//    SaidaParser sp;
//    private String bufferTipoExp; // Armazena tipo que a expressao retorna
//    private Escopos pilhaDeEscopos;
//
//    // Construtor
//    public GeradorDeCodigo(SaidaParser sp) {
//        this.sp = sp;
//        bufferTipoExp = "inteiro";
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
