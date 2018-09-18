package T1;

import LA.LABaseVisitor;
import LA.LAParser;

public class T1Visitor extends LABaseVisitor<Void> {
    private Escopos pilhaDeEscopos;
    private SaidaParser sp;

    public T1Visitor(SaidaParser sṕ) {
        this.sp = sp;
        pilhaDeEscopos = new Escopos();
    }

    //TODO: CTRL+O para dar override nos métodos


    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
        pilhaDeEscopos.empilhar(new TabelaDeSimbolos("global"));

        super.visitPrograma(ctx);

        pilhaDeEscopos.removerEscopo();

        return null;
    }

    @Override
    public Void visitDeclaracao_global_procedimento(LAParser.Declaracao_global_procedimentoContext ctx) {
        pilhaDeEscopos.empilhar(new TabelaDeSimbolos(ctx.IDENT().getText()));

        ctx.IDENT().getSymbol().getLine()

        super.visitDeclaracao_global_procedimento(ctx);

        pilhaDeEscopos.removerEscopo();
    }

    @Override
    public Void visitIdentificador(LAParser.IdentificadorContext ctx) {
        sp.println("Achei um id: "+ctx.IDENT(0).getText());

        return null;
    }
}
