package T1;

import LA.LABaseVisitor;
import LA.LAParser;

import java.util.List;

/* GeradprDeCodigo.java
*  Classe responsável pelos métodos de geração de código.
* */
public class GeradorDeCodigo extends LABaseVisitor {
    static final String BLANK = " "; // String para espaço em branco
    private String codigoC; // String para armazenar o código escreveCdo em C
    
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
        escreveC (
                "#include<stdio.h>\n" +
                "#include<stdlib.h>"
        );
        super.visitPrograma ( ctx );
        return null;
    }

    @Override
    public Object visitVariavel ( LAParser.VariavelContext ctx ) {
        String tipo = tipoParaC ( ctx.tipo().toString () );
        StringBuffer buffer = new StringBuffer (  );
        buffer.append ( tipo + visitIdentificador ( ctx.identificador1 ).toString () );
        if ( ctx.outrosIdentificadores != null )
            for (LAParser.IdentificadorContext id : ctx.outrosIdentificadores)
                buffer.append ( "," + visitIdentificador ( id ).toString () );

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
    public Object visitDecl_local_global ( LAParser.Decl_local_globalContext ctx ) {
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
        TabelaDeSimbolos local = new TabelaDeSimbolos ( "local" );
        String dlid = ctx.getStart ().getText();
        switch (dlid) {
            case "declare":
                escreveC( visitVariavel ( ctx.variavel ( ) ).toString () );
                break;
            case "constante":
                String tipoBasico = tipoParaC ( ctx.tipo_basico ().getText () );
                String valorConstante = visitValor_constante ( ctx.valor_constante () ).toString ();
                escreveC (
                        "const " +
                        tipoBasico +
                        BLANK + ctx.IDENT ().toString () +
                        "=" + valorConstante + ";"
                );
                break;
            case "tipo":
                escreveC(
                        "typedef " +
                        ctx.tipo().toString () + ";"
                );
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public Object visitParametros ( LAParser.ParametrosContext ctx ) {
        StringBuffer buffer = new StringBuffer (  );
        if ( ctx.parametro1 != null ) buffer.append( visitParametro (ctx.parametro1) );
        if ( ctx.outrosParametros != null )
            for ( LAParser.ParametroContext p : ctx.outrosParametros )
                buffer.append(
                        "," +
                        visitParametro( p ).toString ()
                );

        return buffer.toString ();
    }

    @Override
    public Object visitParametro ( LAParser.ParametroContext ctx ) {
        String tipo = tipoParaC ( ctx.tipo_estendido ().getText ().replace("^","") );
        StringBuffer buffer = new StringBuffer (  );
        if ( ctx.identificador1 != null ) buffer.append( tipo + BLANK + visitIdentificador ( ctx.identificador1 ) );
        if ( ctx.outrosIdentificadores != null )
            for( LAParser.IdentificadorContext i : ctx.outrosIdentificadores )
                buffer.append ( "," + tipo + BLANK + visitIdentificador ( i ) );

        return buffer.toString ();
    }

    @Override
    public Object visitDeclaracao_global_funcao ( LAParser.Declaracao_global_funcaoContext ctx ) {
        String nome = ctx.IDENT ().toString ();
        StringBuffer bufferCmd = new StringBuffer (  );
        if ( ctx.listaComandos != null )
            for ( LAParser.CmdContext c : ctx.listaComandos )
                bufferCmd.append ( BLANK + visitCmd ( c ).toString () );

        bufferCmd.append ( BLANK );
        escreveC (
                tipoParaC ( ctx.tipo_estendido ().getText ().replace("^","") )
                + BLANK + nome + "(" + visitParametros(ctx.parametros ()) + ")"
                + "{ " + bufferCmd.toString () + " }"
        );

        return null;
    }
}
