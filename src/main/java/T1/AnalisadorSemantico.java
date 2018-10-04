package T1;

import LA.LABaseVisitor;
import LA.LAParser;

public class AnalisadorSemantico extends LABaseVisitor<Object> {
    private Escopos pilhaDeEscopos;
    private SaidaParser sp;

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
        if(!pilhaDeEscopos.escopoAtual().existeSimbolo(ctx.identificador1.getText())){
            pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.identificador1.getText(), ctx.tipo().getText());
            if (!ctx.tipo().getText().equals("literal") && !ctx.tipo().getText().equals("inteiro") && !ctx.tipo().getText().equals("real") && !ctx.tipo().getText().equals("logico")) {
                sp.println("Linha " + ctx.tipo().getStart().getLine() + ": tipo " + ctx.tipo().getText() + " nao declarado");
            }
        }else{
            sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.identificador1.getText() + " ja declarado anteriormente");
        }
        if(ctx.outrosIdentificadores != null){
            for(LAParser.IdentificadorContext outrosIdentCtx : ctx.outrosIdentificadores){
                if(!pilhaDeEscopos.escopoAtual().existeSimbolo(outrosIdentCtx.getText())){
                    pilhaDeEscopos.escopoAtual().adicionarSimbolo(outrosIdentCtx.getText(), ctx.tipo().getText());
                    if (!ctx.tipo().getText().equals("literal") && !ctx.tipo().getText().equals("inteiro") && !ctx.tipo().getText().equals("real") && !ctx.tipo().getText().equals("logico")) {
                        sp.println("Linha " + ctx.tipo().getStart().getLine() + ": tipo " + ctx.tipo().getText() + " nao declarado");
                    }
                }else{
                    sp.println("Linha " + outrosIdentCtx.getStart().getLine() + ": identificador " + outrosIdentCtx.getText() + " ja declarado anteriormente");
                }
            }
        }
        return null;
    }

    @Override
    public Object visitCmdLeia(LAParser.CmdLeiaContext ctx) {
        if(!pilhaDeEscopos.escopoAtual().existeSimbolo(ctx.id1.getText())){
            sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.id1.getText() + " nao declarado");
        }
        if(ctx.outrosIds != null){
            for (LAParser.IdentificadorContext identificadorCtx : ctx.outrosIds) {
                if (!pilhaDeEscopos.escopoAtual().existeSimbolo(identificadorCtx.getText())) {
                    sp.println("Linha " + identificadorCtx.getStart().getLine() + ": identificador " + identificadorCtx.getText() + " nao declarado");
                }
            }
        }
        return null;
    }
}
