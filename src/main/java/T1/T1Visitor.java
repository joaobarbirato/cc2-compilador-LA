package T1;

import LA.LABaseVisitor;
import LA.LAParser;

public class T1Visitor extends LABaseVisitor<Void> {
    private Escopos pilhaDeEscopos;
    private SaidaParser sp;

    public T1Visitor(SaidaParser sp) {
        this.sp = sp;
    }

    //TODO: CTRL+O para dar override nos métodos


    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
        pilhaDeEscopos = new Escopos(new TabelaDeSimbolos("global"));

        super.visitPrograma(ctx);
        visitDeclaracoes(ctx.declaracoes());
        visitCorpo(ctx.corpo());
        pilhaDeEscopos.desempilha();

        if(sp.isModificado()){
            sp.println("Fim da Compilacao");
        }
        return null;
    }

    @Override
    public Void visitDeclaracoes(LAParser.DeclaracoesContext ctx) {
        if(ctx.children != null){
            visitDecl_local_global((LAParser.Decl_local_globalContext) ctx.decl_local_global());
        }
        return null;
    }

    @Override
    public Void visitDecl_local_global(LAParser.Decl_local_globalContext ctx) {
        if(ctx.declaracao_global() != null){
            visitDeclaracao_local(ctx.declaracao_local());
        }else if(ctx.declaracao_global() != null){
            visitDeclaracao_global_funcao((LAParser.Declaracao_global_funcaoContext) ctx.declaracao_global());
            visitDeclaracao_global_procedimento((LAParser.Declaracao_global_procedimentoContext) ctx.declaracao_global());
        }
        return null;
    }

    @Override
    public Void visitDeclaracao_local(LAParser.Declaracao_localContext ctx) {
        TabelaDeSimbolos escopoAtual = pilhaDeEscopos.escopoAtual();
        if(ctx.getText().startsWith("declare")){
            visitVariavel(ctx.variavel());
        }else if(ctx.getText().startsWith("constante")) {
            if (!escopoAtual.existeSimbolo(ctx.IDENT().toString())) {
                visitTipo_basico(ctx.tipo_basico());
                escopoAtual.adicionarSimbolo(ctx.IDENT().toString(), ctx.tipo_basico().getText());
                visitValor_constante(ctx.valor_constante());
            } else {
                //erro, constante já declarada
                sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().toString() + " ja declarado anteriormente");
            }
        }else if(ctx.getText().startsWith("tipo")){
            if(!escopoAtual.existeSimbolo(ctx.IDENT().toString())){
                escopoAtual.adicionarSimbolo(ctx.IDENT().getText(), "tipo");
                visitTipo(ctx.tipo());
            }else{
                sp.println("Linha " + ctx.getStart().getLine() + ": identificador " + ctx.IDENT().toString() + " ja declarado anteriormente");
            }
        }
        return null;
    }



    @Override
    public Void visitDeclaracao_global_procedimento(LAParser.Declaracao_global_procedimentoContext ctx) {
        pilhaDeEscopos.empilhar(new TabelaDeSimbolos(ctx.IDENT().getText()));

        ctx.IDENT().getSymbol().getLine();

        super.visitDeclaracao_global_procedimento(ctx);

        pilhaDeEscopos.desempilha();

        return null;
    }

}
