package T1;

import LA.LABaseVisitor;
import LA.LAParser;
import org.antlr.v4.runtime.Token;

/* GeradprDeCodigo.java
*  Classe responsável pelos métodos de geração de código.
* */
public class GeradorDeCodigo extends LABaseVisitor<String> {
    private static final String BLANK = " "; // String para espaço em branco
    private static final String END_CMD_LINE = ";\n"; // String para pular linhas
    private StringBuilder codigoC;
    private boolean estaVisitandoRegistro;
    private SaidaParser sp;
    private String bufferTipoExp; // Armazena tipo que a expressao retorna
    private Escopos pilhaDeEscopos;
    private TabelaDeSimbolos declAtributosStruct;

    // Construtor
    public GeradorDeCodigo( SaidaParser sp ) {
        this.sp = sp;
        bufferTipoExp = "inteiro";
        codigoC = new StringBuilder (  );
        estaVisitandoRegistro = false;
        declAtributosStruct = new TabelaDeSimbolos ( "struct" );
    }

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
        pilhaDeEscopos = new Escopos();
        pilhaDeEscopos.empilhar ( new TabelaDeSimbolos("global") );
        codigoC.append (
                "#include<stdio.h>\n" +
                "#include<stdlib.h>\n\n"
        );
        visitDeclaracoes ( ctx.declaracoes () );
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
        StringBuilder bufferCmd = new StringBuilder (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.CmdContext c : ctx.listaComandos )
                bufferCmd.append ( BLANK ).append ( visitCmd ( c ) );

        bufferCmd.append ( BLANK );
        codigoC.append ( "int main () {\n" ).append ( bufferDL.toString ( ) ).append ( BLANK ).append ( bufferCmd.toString ( ) ).append ( BLANK ).append ( "return 0" ).append ( END_CMD_LINE ).append ( "}\n" );
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
        StringBuilder buffer = new StringBuilder (  );
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
        StringBuilder buffer = new StringBuilder (  );
        if ( ctx.parametro1 != null ) buffer.append( visitParametro (ctx.parametro1) );
        if ( ctx.outrosParametros != null )
            for ( LAParser.ParametroContext p : ctx.outrosParametros ) {
                buffer.append ( "," ).append ( visitParametro ( p ) );
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
        return buffer.toString ();
    }

    @Override
    public String visitDeclaracao_global_funcao ( LAParser.Declaracao_global_funcaoContext ctx ) {
        pilhaDeEscopos.empilhar ( new TabelaDeSimbolos ( "funcao" ) );
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
        pilhaDeEscopos.empilhar ( new TabelaDeSimbolos ( "procedimento" ) );
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
        if ( !ctx.outrosNumero_intervalo.isEmpty () )
            for ( LAParser.Numero_intervaloContext nic : ctx.outrosNumero_intervalo )
                bufferNumInter.append( "\n" ).append( visitNumero_intervalo ( nic ) ).append ( ":\n" );
        buffer += bufferNumInter.toString ();
        return buffer;
    }

    @Override
    public String visitNumero_intervalo ( LAParser.Numero_intervaloContext ctx ) {
        StringBuilder buffer = new StringBuilder ( );
        if( ctx.op_unario1 != null )
            buffer.append ( "case " ).append ( ctx.op_unario1.getText ( ) ).append ( ctx.ni1.getText ( ) );
        else
            buffer.append ( "case " ).append ( ctx.ni1.getText ( ) );
        buffer.append ( ":\n" );
        if ( ctx.ni2 != null ) {
            StringBuilder bufferOpUnario2 = new StringBuilder (  );
            if ( !ctx.op_unario2.isEmpty () )
                for ( LAParser.Op_unarioContext ouc : ctx.op_unario2 )
                    bufferOpUnario2.append ( ouc.getText () );

            int ni1 = Integer.parseInt ( ctx.ni1.getText () );
            int ni2 = Integer.parseInt ( ctx.ni2.getText ( ) );
            for (int i = ni1+1; i <= ni2; i += 1)
                if ( i == ni1+1 )
                    buffer.append ( "case " ).append ( bufferOpUnario2.toString ( ) ).append ( String.valueOf ( i ) );
                else
                    buffer.append ( ":\ncase " ).append ( bufferOpUnario2.toString ( ) ).append ( String.valueOf ( i ) );
            buffer.append ( ":\n" );
        }


        return buffer.toString ( );
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
        return ctx.getText ();
    }

    @Override
    public String visitRegistro ( LAParser.RegistroContext ctx ) {
        estaVisitandoRegistro = true;
        StringBuilder bufferRetorno = new StringBuilder ( "struct {\n" );
        StringBuilder bufferVar = new StringBuilder (  );
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
        buffer += bufferExp.toString () + ")" + END_CMD_LINE;
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
