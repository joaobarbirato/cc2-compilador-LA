package T1;

import LA.LABaseVisitor;
import LA.LAParser;

import java.util.ArrayList;

public class T1Visitor extends LABaseVisitor<Object> {
    private Escopos pilhaDeEscopos;
    private SaidaParser sp;

    public T1Visitor(SaidaParser sp) {
        this.sp = sp;
    }

    //TODO: CTRL+O para dar override nos métodos


    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
        pilhaDeEscopos = new Escopos(new TabelaDeSimbolos("global"));
        super.visitDeclaracoes(ctx.declaracoes());
        super.visitCorpo(ctx.corpo());

        pilhaDeEscopos.desempilhar();

        //if (sp.isModificado()) {
        //    sp.println("Fim da Compilacao");
        //}
        return null;
    }


    @Override
    public Void visitDeclaracao_local(LAParser.Declaracao_localContext ctx) {
        if (ctx.getText().startsWith("declare")) {
            super.visitVariavel(ctx.variavel());
        } else if (ctx.getText().startsWith("constante")) {
            if (!pilhaDeEscopos.escopoAtual().existeSimbolo(ctx.IDENT().toString())) {
                super.visitTipo_basico(ctx.tipo_basico());
                pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo_basico().getText());
                super.visitValor_constante(ctx.valor_constante());
            } else {
                //erro, constante já declarada
                sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().toString() + " ja declarado anteriormente");
            }
        } else if (ctx.getText().startsWith("tipo")) {
            if (!pilhaDeEscopos.escopoAtual().existeSimbolo(ctx.IDENT().toString())) {
                pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.IDENT().getText(), "tipo");
                super.visitTipo(ctx.tipo());
            } else {
                sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().toString() + " ja declarado anteriormente");
            }
        }
        return null;
    }

    @Override
    public Object visitDeclaracao_global_procedimento(LAParser.Declaracao_global_procedimentoContext ctx) {
        if(!pilhaDeEscopos.escopoAtual().existeSimbolo(ctx.IDENT().getText())){
            pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.IDENT().getText(), "função");
            pilhaDeEscopos.empilhar(new TabelaDeSimbolos("procedimento"));
            if(ctx.parametros() != null) {
                for (LAParser.ParametroContext parametroCtx : ctx.parametros().parametro()) {
                    super.visitParametro(parametroCtx);
                }
            }
            if(ctx.declaracao_local() != null) {
                for(LAParser.Declaracao_localContext declaracaoCtx: ctx.declaracao_local()) {
                    super.visitDeclaracao_local(declaracaoCtx);
                }
            }
            if(ctx.cmd() != null){
                for(LAParser.CmdContext cmd: ctx.cmd()){
                    super.visitCmd(cmd);
                }
            }
            pilhaDeEscopos.desempilhar();
        }else{
            sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().getText() + " ja declarado anteriormente");
        }
        return null;
    }

    @Override
    public Object visitDeclaracao_global_funcao(LAParser.Declaracao_global_funcaoContext ctx) {
        if(!pilhaDeEscopos.escopoAtual().existeSimbolo(ctx.getText())) {
            pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.IDENT().getText(), "função");
            pilhaDeEscopos.empilhar(new TabelaDeSimbolos("função"));
            if(ctx.parametros() != null) {
                for (LAParser.ParametroContext parametroCtx : ctx.parametros().parametro()) {
                    super.visitParametro(parametroCtx);
                }
            }
            if(ctx.declaracao_local() != null) {
                for(LAParser.Declaracao_localContext declaracaoCtx: ctx.declaracao_local()) {
                    super.visitDeclaracao_local(declaracaoCtx);
                }
            }
            if(ctx.cmd() != null) {
                for (LAParser.CmdContext cmd : ctx.cmd()) {
                    super.visitCmd(cmd);
                }
            }
            pilhaDeEscopos.desempilhar();
        }else{
            sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().getText() + " ja declarado anteriormente");
        }
        return null;
    }

    //TODO: variavel e parametro
    @Override
    public Object visitVariavel(LAParser.VariavelContext ctx) {
        return super.visitVariavel(ctx);
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
            for(LAParser.VariavelContext variavelCtx : ctx.variavel()){
                super.visitVariavel(variavelCtx);
            }
        }
        return null;
    }
}
