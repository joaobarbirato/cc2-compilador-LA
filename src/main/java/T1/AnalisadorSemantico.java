package T1;

import LA.LABaseVisitor;
import LA.LAParser;

import java.util.List;

public class AnalisadorSemantico extends LABaseVisitor<Object> {
    private Escopos pilhaDeEscopos;
    private SaidaParser sp;
    private List<String> listaTipos;

    public AnalisadorSemantico(SaidaParser sp) {
        this.sp = sp;
    }

    //TODO: CTRL+O para dar override nos métodos


    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
        pilhaDeEscopos = new Escopos(new TabelaDeSimbolos("global"));
        visitDeclaracoes(ctx.declaracoes());
        visitCorpo(ctx.corpo());

        pilhaDeEscopos.desempilhar();
        return null;
    }

    @Override
    public Object visitVariavel(LAParser.VariavelContext ctx) {
        if (!pilhaDeEscopos.escopoAtual().existeSimbolo(ctx.identificador1.getText())) {
            pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.identificador1.getText(), ctx.tipo().getText());
            if (!ctx.tipo().getText().equals("literal") && !ctx.tipo().getText().equals("inteiro") && !ctx.tipo().getText().equals("real") && !ctx.tipo().getText().equals("logico")) {
                sp.println("Linha " + ctx.tipo().getStart().getLine() + ": tipo " + ctx.tipo().getText() + " nao declarado");
            }
        } else {
            sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.identificador1.getText() + " ja declarado anteriormente");
        }
        if (ctx.outrosIdentificadores != null) {
            for (LAParser.IdentificadorContext outrosIdentCtx : ctx.outrosIdentificadores) {
                if (!pilhaDeEscopos.escopoAtual().existeSimbolo(outrosIdentCtx.getText())) {
                    pilhaDeEscopos.escopoAtual().adicionarSimbolo(outrosIdentCtx.getText(), ctx.tipo().getText());
                    if (!ctx.tipo().getText().equals("literal") && !ctx.tipo().getText().equals("inteiro") && !ctx.tipo().getText().equals("real") && !ctx.tipo().getText().equals("logico")) {
                        sp.println("Linha " + ctx.tipo().getStart().getLine() + ": tipo " + ctx.tipo().getText() + " nao declarado");
                    }
                } else {
                    sp.println("Linha " + outrosIdentCtx.getStart().getLine() + ": identificador " + outrosIdentCtx.getText() + " ja declarado anteriormente");
                }
            }
        }
        return null;
    }

    @Override
    public Object visitIdentificador(LAParser.IdentificadorContext ctx) {
        if(!pilhaDeEscopos.existeSimbolo(ctx.ident1.getText())){
            sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.ident1.getText() + " nao declarado");
        }
        return null;
    }

    @Override
    public Object visitCmdAtribuicao(LAParser.CmdAtribuicaoContext ctx) {
        String tipoAtribuir = pilhaDeEscopos.getSimboloTipo(ctx.identificador().getText());
        String tipoAtribuido =
    }
}
