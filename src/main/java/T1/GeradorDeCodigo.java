package T1;

import LA.LABaseVisitor;
import LA.LAParser;

import java.util.ArrayList;
import java.util.List;

/* GeradprDeCodigo.java
*  Classe responsável pelos métodos de geração de código.
* */
public class GeradorDeCodigo extends LABaseVisitor {
    static final String BLANK = " "; // String para espaço em branco
    private String codigoC; // String para armazenar o código escreveCdo em C
    private Escopos pilhaDeEscopos;

    private class Tupla{
        private String tipo;
        private String var;
        public Tupla (String var, String tipo){
            this.tipo = tipo;
            this.var = var;
        }
        public String getVar() { return var; };
        public String getTipo() { return tipo; };

        public boolean setVar(String var) {
            if(var != null){
                this.var = var;
                return true;
            }
            return false;
        }

        public boolean setTipo(String tipo) {
            if(tipo != null){
                this.tipo = tipo;
                return true;
            }
            return false;
        }
    }


    // Construtor
    public GeradorDeCodigo() {
        codigoC = "";
    }
    
    // Método para escrever no código resultante
    private String escreveC (String codigo) {
        this.codigoC += BLANK + codigo + BLANK;
        return codigoC;
    }

    // Método para transformar os tipos de variável em tipos em C
    private String tipoParaC (String tipo) {
        String novoTipo;
        switch (tipo){
            case "literal":
                novoTipo = "char";
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
        return novoTipo;
    }


    /*
    * Sobrecarga de operadores do visitor
    * Alguns métodos retornam String, por escolha de implementação.
    * */
    @Override
    public Object visitPrograma ( LAParser.ProgramaContext ctx ) {
        pilhaDeEscopos = new Escopos(new TabelaDeSimbolos("global"));
        escreveC (
                "#include<stdio.h>\n" +
                "#include<stdlib.h>"
        );
        super.visitPrograma ( ctx );
        pilhaDeEscopos.desempilhar();
        return null;
    }

    @Override
    public Object visitCorpo ( LAParser.CorpoContext ctx ) {
        StringBuffer bufferCmd = new StringBuffer (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.CmdContext c : ctx.listaComandos )
                bufferCmd.append ( BLANK + visitCmd ( c ).toString () + ";" );

        StringBuffer bufferDL = new StringBuffer (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
                bufferDL.append ( BLANK + visitDeclaracao_local ( dl ).toString () + BLANK );

        bufferCmd.append ( BLANK );
        escreveC (
                "int main () {" +
                    bufferDL.toString () + BLANK + bufferCmd.toString () + BLANK
                    + "return 0; " +
                "}"
        );
        pilhaDeEscopos.desempilhar ();
        return null;
    }

    @Override
    public Object visitVariavel ( LAParser.VariavelContext ctx ) {
        String tipo = tipoParaC ( ctx.tipo().toString () );
        StringBuffer buffer = new StringBuffer (  );
        if ( ctx.identificador1 != null ) {
            String id = visitIdentificador ( ctx.identificador1 ).toString ()
            pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
            buffer.append ( tipo + BLANK + id );
        }
        if ( ctx.outrosIdentificadores != null )
            for (LAParser.IdentificadorContext idc : ctx.outrosIdentificadores){
                String id = visitIdentificador ( idc ).toString ();
                pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
                buffer.append ( "," + BLANK + tipo + BLANK + id );
            }


        return buffer.toString ();
    }

    @Override
    public Object visitIdentificador ( LAParser.IdentificadorContext ctx ) {
        StringBuffer buffer = new StringBuffer (  );
        buffer.append( ctx.ident1.toString () );
        if ( ctx.outrosIdent != null )
            buffer.append( "." + ctx.outrosIdent.getText () );

        return buffer.toString ();
    }

    @Override
    public Object visitDeclaracoes ( LAParser.DeclaracoesContext ctx ) {
        List<LAParser.Decl_local_globalContext> listaDlg = ctx.decl_local_global ();
        if( listaDlg != null )
            for(LAParser.Decl_local_globalContext dlg : listaDlg)
                visitDecl_local_global ( dlg );

        return null;
    }

    @Override
    public Object visitDecl_local_global ( LAParser.Decl_local_globalContext ctx ) {]
        visitDeclaracao_global_funcao ( (LAParser.Declaracao_global_funcaoContext) ctx.declaracao_global () );
        visitDeclaracao_global_procedimento ( (LAParser.Declaracao_global_procedimentoContext) ctx.declaracao_global () );
        visitDeclaracao_local ( ctx.declaracao_local () );
        return null;
    }

    @Override
    public Object visitValor_constante ( LAParser.Valor_constanteContext ctx ) {
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
    public Object visitDeclaracao_local ( LAParser.Declaracao_localContext ctx ) {
        String dlid = ctx.getStart ().getText();
        String id, tipo;
        String buffer ;
        switch (dlid) {
            case "declare":
                buffer = visitVariavel ( ctx.variavel ( ) ).toString ();
                break;
            case "constante":
                tipo = tipoParaC ( ctx.tipo_basico ().getText () );
                id = ctx.IDENT ().toString ();
                String valorConstante = visitValor_constante ( ctx.valor_constante () ).toString ();
                pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
                buffer =
                        "const " +
                        tipo +
                        BLANK + id +
                        "=" + valorConstante + ";";
                break;
            case "tipo":
                tipo = ctx.tipo().toString ();
                id = ctx.IDENT ().toString ();
                pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
                buffer = "typedef " + BLANK + tipo + BLANK + id + ";";
                break;
            default: // Não vai chegar nesse caso antes de uma detecção sintática
                return null;
        }
        return buffer;
    }

    @Override
    public Object visitParametros ( LAParser.ParametrosContext ctx ) {
        StringBuffer buffer = new StringBuffer (  );
        if ( ctx.parametro1 != null ) buffer.append( visitParametro (ctx.parametro1) );
        if ( ctx.outrosParametros != null )
            for ( LAParser.ParametroContext p : ctx.outrosParametros ) {
                System.out.println ();
                buffer.append ( "," + visitParametro ( p ) );
            }

        return buffer.toString ();
    }

    @Override
    public Object visitParametro ( LAParser.ParametroContext ctx ) {
        String tipo = tipoParaC ( ctx.tipo_estendido ().getText ().replace("^","") );
        StringBuffer buffer = new StringBuffer ( );
        if ( ctx.identificador1 != null ) {
            String id = visitIdentificador ( ctx.identificador1 ).toString ();
            pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( id, tipo );
            buffer.append( tipo + BLANK + id );
        }
        if ( ctx.outrosIdentificadores != null )
            for( LAParser.IdentificadorContext i : ctx.outrosIdentificadores ){
                String id = visitIdentificador ( i ).toString ();
                buffer.append ( "," + BLANK + tipo + BLANK + id );
            }

        return buffer.toString ();
    }

    @Override
    public Object visitDeclaracao_global_funcao ( LAParser.Declaracao_global_funcaoContext ctx ) {
        pilhaDeEscopos.empilhar ( new TabelaDeSimbolos ( "funcao" ) );
        String nome = ctx.IDENT ().toString ();
        String tipo = ctx.tipo_estendido ().getText ().replace("^","*");
        pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( nome, tipo );

        StringBuffer bufferCmd = new StringBuffer (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.CmdContext c : ctx.listaComandos )
                bufferCmd.append ( BLANK + visitCmd ( c ).toString () + ";" );

        StringBuffer bufferDL = new StringBuffer (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
                bufferDL.append ( BLANK + visitDeclaracao_local ( dl ).toString () + BLANK );

        bufferCmd.append ( BLANK );
        escreveC (
                tipoParaC ( tipo )
                + BLANK + nome + "(" + visitParametros(ctx.parametros ()) + ")"
                + "{ " + bufferDL.toString () + BLANK + bufferCmd.toString () + " }"
        );
        pilhaDeEscopos.desempilhar ();
        return null;
    }

    @Override
    public Object visitDeclaracao_global_procedimento ( LAParser.Declaracao_global_procedimentoContext ctx ) {
        pilhaDeEscopos.empilhar ( new TabelaDeSimbolos ( "procedimento" ) );
        String nome = ctx.IDENT ().toString ();
        String tipo = "void";
        pilhaDeEscopos.escopoAtual ().adicionarSimbolo ( nome, tipo );

        StringBuffer bufferCmd = new StringBuffer (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.CmdContext c : ctx.listaComandos )
                bufferCmd.append ( BLANK + visitCmd ( c ).toString () + ";" );

        StringBuffer bufferDL = new StringBuffer (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.Declaracao_localContext dl : ctx.listaDL )
                bufferDL.append ( BLANK + visitDeclaracao_local ( dl ).toString () + BLANK );

        bufferCmd.append ( BLANK );
        escreveC (
                tipoParaC ( tipo )
                        + BLANK + nome + "(" + visitParametros(ctx.parametros ()) + ")"
                        + "{ " + bufferDL.toString () + BLANK + bufferCmd.toString () + " }"
        );
        pilhaDeEscopos.desempilhar ();
        return null;
    }

    @Override
    public Object visitCmd ( LAParser.CmdContext ctx ) {
        String tokenInicio = ctx.getStart ().toString ();
        String buffer;
        switch (tokenInicio){
            case "leia":
                buffer = visitCmdLeia ( ctx.cmdLeia () ).toString ();
                break;
            case "escreva":
                buffer = visitCmdEscreva ( ctx.cmdEscreva () ).toString ();
                break;
            case "se":
                buffer = visitCmdSe ( ctx.cmdSe () ).toString ();
                break;
            case "caso":
                buffer = visitCmdLeia ( ctx.cmdLeia () ).toString ();
                break;
            case "para":
                buffer = visitCmdPara ( ctx.cmdPara () ).toString ();
                break;
            case "enquanto":
                buffer = visitCmdEnquanto ( ctx.cmdEnquanto () ).toString ();
                break;
            case "faca":
                buffer = visitCmdFaca ( ctx.cmdFaca () ).toString ();
                break;
            default:
                if ( ctx.toString ().contains ( "<-" ) ){ // Atribuição
                    buffer = visitCmdAtribuicao ( ctx.cmdAtribuicao () ).toString ();
                } else if ( ctx.toString ().contains ( "(" ) && ctx.toString ().contains ( ")" ) ) { // Chamada
                    buffer = visitCmdChamada ( ctx.cmdChamada () ).toString ();
                } else {
                    buffer = "";
                }
                break;
        }
        return buffer;
    }
    @Override
    public Object visitCmdLeia ( LAParser.CmdLeiaContext ctx ) {
        String buffer = ctx.getText ();
        buffer = buffer.replace("escreva", "scanf");

        buffer += ";";
        return null;
    }
}
