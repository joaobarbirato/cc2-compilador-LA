package T1;

import LA.LABaseVisitor;
import LA.LAParser;

import java.util.ArrayList;

public class T1Visitor extends LABaseVisitor<Object> {
    private Escopos pilhaDeEscopos;
    private SaidaParser sp;
    private int contadorParametros;

    ArrayList<String> funcoes = new ArrayList<String>();

    public T1Visitor(SaidaParser sp) {
        this.sp = sp;
    }

    //TODO: CTRL+O para dar override nos métodos


    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
        pilhaDeEscopos = new Escopos(new TabelaDeSimbolos("global"));
        super.visitPrograma(ctx);

        pilhaDeEscopos.desempilhar();

        //if (sp.isModificado()) {
        //    sp.println("Fim da Compilacao");
        //}
        return null;
    }

    @Override
    public Void visitDecl_local_global(LAParser.Decl_local_globalContext ctx) {
        if (ctx.declaracao_local() != null) {
            super.visitDeclaracao_local(ctx.declaracao_local());
        } else if (ctx.declaracao_global() != null) {
            super.visitDeclaracao_global(ctx.declaracao_global());
        }
        return null;
    }

    @Override
    public Void visitDeclaracao_local(LAParser.Declaracao_localContext ctx) {
        TabelaDeSimbolos escopoAtual = pilhaDeEscopos.escopoAtual();
        if (ctx.getText().startsWith("declare")) {
            super.visitVariavel(ctx.variavel());
        } else if (ctx.getText().startsWith("constante")) {
            if (!escopoAtual.existeSimbolo(ctx.IDENT().toString())) {
                super.visitTipo_basico(ctx.tipo_basico());
                escopoAtual.adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo_basico().getText());
                super.visitValor_constante(ctx.valor_constante());
            } else {
                //erro, constante já declarada
                sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().toString() + " ja declarado anteriormente");
            }
        } else if (ctx.getText().startsWith("tipo")) {
            if (!escopoAtual.existeSimbolo(ctx.IDENT().toString())) {
                escopoAtual.adicionarSimbolo(ctx.IDENT().getText(), "tipo");
                super.visitTipo(ctx.tipo());
            } else {
                sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().toString() + " ja declarado anteriormente");
            }
        }
        return null;
    }

    @Override
    public Object visitVariavel(LAParser.VariavelContext ctx) {
        return super.visitVariavel(ctx);
    }

    @Override
    public Void visitIdentificador(LAParser.IdentificadorContext ctx) {
        if (ctx.children != null) {
            TabelaDeSimbolos escopoAtual = pilhaDeEscopos.escopoAtual();
            super.visitDimensao(ctx.dimensao());
            super.visitOutro_identificador(ctx.outro_identificador());
            if (ctx.outro_identificador().getText().startsWith(".")) {
                String[] outroIdentificador = ctx.getText().split("\\.");
                if (!pilhaDeEscopos.existeSimbolo(outroIdentificador[1])) {
                    sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().toString() + " nao declarado");
                }else if(!escopoAtual.existeSimbolo(ctx.IDENT().toString())){
                    sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().toString() + " nao declarado");
                }
            }
        }
        return null;
    }

    @Override
    public Void visitOutro_identificador(LAParser.Outro_identificadorContext ctx) {
        return null;
    }

    @Override
    public Void visitDimensao(LAParser.DimensaoContext ctx) {
        if (ctx.children != null) {
            for (LAParser.Exp_aritimeticaContext ct : ctx.exp_aritimetica()) {
                super.visitExp_aritimetica(ct);
            }
        }
        return null;
    }

    @Override
    public Void visitTipo(LAParser.TipoContext ctx) {
        if (ctx.registro() != null) {
            super.visitRegistro(ctx.registro());
        } else if (ctx.tipo_estendido() != null) {
            super.visitTipo_estendido(ctx.tipo_estendido());
        }
        return null;
    }

    @Override
    public String visitTipo_basico(LAParser.Tipo_basicoContext ctx) {
        if (ctx.getText().equals("literal") || ctx.getText().equals("inteiro") || ctx.getText().equals("real") || ctx.getText().equals("logico")) {
            return ctx.getText();
        } else {
            sp.println("Linha " + ctx.getStart().getLine() + ": tipo " + ctx.getText() + " nao declarado");
            return "erro de compilacao";
        }
    }

    @Override
    public String visitTipo_basico_ident(LAParser.Tipo_basico_identContext ctx) {
        if (ctx.tipo_basico() != null) {
            return visitTipo_basico(ctx.tipo_basico());
        } else {
            if (pilhaDeEscopos.existeSimbolo(ctx.IDENT().toString())) {
                return ctx.IDENT().toString();
            } else {
                sp.println("Linha " + ctx.getStart().getLine() + ": tipo " + ctx.IDENT().toString() + " nao declarado");
                return "erro de compilacao";
            }
        }
    }

    @Override
    public String visitTipo_estendido(LAParser.Tipo_estendidoContext ctx) {
        if(ctx.children != null){
            return visitTipo_basico_ident(ctx.tipo_basico_ident());
        }
        return "erro de compilacao";
    }

    @Override
    public Object visitValor_constante(LAParser.Valor_constanteContext ctx) {
        return ctx.getText();
    }

    @Override
    public Object visitRegistro(LAParser.RegistroContext ctx) {
        if(ctx.children != null){
            for(LAParser.VariavelContext ct : ctx.variavel()){
                super.visitVariavel(ct);
            }
        }
        return null;
    }

    @Override
    public Object visitDeclaracao_global(LAParser.Declaracao_globalContext ctx) {
        if(ctx.getText().startsWith("procedimento")){
            if(!pilhaDeEscopos.existeSimbolo(ctx.IDENT().toString())){
                pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.IDENT().getText(), "procedimento");
            }

            pilhaDeEscopos.empilhar(new TabelaDeSimbolos("procedimento "+ctx.IDENT().getText()));
            super.visitParametros(ctx.parametros());
            for(LAParser.Declaracao_localContext ct : ctx.declaracao_local()){
                super.visitDeclaracao_local(ct);
            }
            for(LAParser.CmdContext ct : ctx.cmd()){
                super.visitCmd(ct);
            }
            pilhaDeEscopos.desempilhar();
        } else{
            if(!pilhaDeEscopos.existeSimbolo(ctx.IDENT().toString())){
                pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.IDENT().getText(), "funcao");
            }
            pilhaDeEscopos.empilhar(new TabelaDeSimbolos("funcao " + ctx.IDENT().getText()));
            contadorParametros = 0;
            super.visitParametros(ctx.parametros());
            funcoes.add(ctx.IDENT().getText() + "," + contadorParametros + "," + ctx.tipo_estendido().getText());
            visitTipo_estendido(ctx.tipo_estendido());
            for(LAParser.Declaracao_localContext ct: ctx.declaracao_local()){
                super.visitDeclaracao_local(ct);
            }
            for(LAParser.CmdContext ct : ctx.cmd()){
                super.visitCmd(ct);
            }
            pilhaDeEscopos.desempilhar();
        }
        return null;
    }

//    @Override
//    public Object visitParametro(LAParser.ParametroContext ctx) {
//        if(ctx.children != null){
//            contadorParametros++;
//            TabelaDeSimbolos escopoAtual = pilhaDeEscopos.escopoAtual();
//            if(!escopoAtual.existeSimbolo()
//        }
//    }


}
