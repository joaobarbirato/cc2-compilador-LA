package T1;

import LA.LABaseVisitor;
import LA.LAParser;

import java.util.List;

public class AnalisadorSemantico extends LABaseVisitor<Object> {
    private Escopos pilhaDeEscopos;
    private SaidaParser sp;
    private String bufferTipoExp = "null";
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
        //Variavel necessária para garantir que seja checado somente o tipo do ponteiro, e não a string começando com ^
        String tipoChecado = "null";
        if (!pilhaDeEscopos.escopoAtual().existeSimbolo(ctx.identificador1.getText())) {
            pilhaDeEscopos.escopoAtual().adicionarSimbolo(ctx.identificador1.getText(), ctx.tipo().getText());
            if(ctx.tipo().getText().startsWith("^")){
                tipoChecado = ctx.tipo().getText().substring(1);
            }else{
                tipoChecado = ctx.tipo().getText();
            }
            if (!tipoChecado.equals("literal") && !tipoChecado.equals("inteiro") && !tipoChecado.equals("real") && !tipoChecado.equals("logico")) {
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
        String tipoVariavel = pilhaDeEscopos.getSimboloTipo(ctx.identificador().getText());
        bufferTipoExp = "null";
        visitExpressao(ctx.expressao());

        if(bufferTipoExp.equals("inteiro") && tipoVariavel.equals("real")){
            bufferTipoExp = "real";
        }
        if(tipoVariavel.startsWith("^") && (bufferTipoExp != "endereco")){
            sp.println("Linha "+ ctx.identificador().getStart().getLine() + ": atribuicao nao compativel para ^" + ctx.identificador().getText());
            return null;
/*        }else if(tipoVariavel.startsWith("^") && (bufferTipoExp != "ponteiro")){
            sp.println("Linha "+ ctx.identificador().getStart().getLine() + ": atribuicao nao compativel para ^" + ctx.identificador().getText());
            return null;*/
        }else if(!tipoVariavel.equals(bufferTipoExp) && !tipoVariavel.startsWith("^")){
            sp.println("Linha "+ ctx.identificador().getStart().getLine() + ": atribuicao nao compativel para " + ctx.identificador().getText());
        }
        return null;
    }
    
    @Override
    public Object visitExp_relacional(LAParser.Exp_relacionalContext ctx) {
        if(ctx.exp_a1 != null){
            visitExp_aritimetica(ctx.exp_a1);
        }
        if(ctx.op_relacional != null){
            bufferTipoExp = "logico";
        }
        return null;
    }

    @Override
    public Object visitParcela_unario_id(LAParser.Parcela_unario_idContext ctx) {
        visitIdentificador(ctx.identificador());
        bufferTipoExp = pilhaDeEscopos.getSimboloTipo(ctx.identificador().getText());
        return null;
    }

    @Override
    public Object visitParcela_unario_inteiro(LAParser.Parcela_unario_inteiroContext ctx) {
        bufferTipoExp = "inteiro";
        return null;
    }

    @Override
    public Object visitParcela_unario_real(LAParser.Parcela_unario_realContext ctx) {
        bufferTipoExp = "real";
        return null;
    }

    @Override
    public Object visitParcela_nao_unario_cadeia(LAParser.Parcela_nao_unario_cadeiaContext ctx) {
        bufferTipoExp = "literal";
        return null;
    }

    @Override
    public Object visitParcela_nao_unario_id(LAParser.Parcela_nao_unario_idContext ctx) {
        bufferTipoExp = "endereco";
        return null;
    }
}

