package T1;

import java.util.List;
import java.util.LinkedList;

public class Escopos {
    private LinkedList<TabelaDeSimbolos> pilhaDeEscopos;

    public Escopos() {
        pilhaDeEscopos = new LinkedList<TabelaDeSimbolos>();
        pilhaDeEscopos.push(new TabelaDeSimbolos("Principal"));
    }

    public Escopos(TabelaDeSimbolos Tabela) {
        pilhaDeEscopos = new LinkedList<TabelaDeSimbolos>();
        pilhaDeEscopos.push(Tabela);
    }

    public void empilhar(TabelaDeSimbolos tabela) {
        pilhaDeEscopos.push(tabela);
    }

    public TabelaDeSimbolos escopoAtual() {
        return pilhaDeEscopos.peek();
    }

    public List<TabelaDeSimbolos> percorrerEscoposAninhados() {
        return pilhaDeEscopos;
    }

    public void desempilha(){
        pilhaDeEscopos.pop();
    }

    public boolean existeSimbolo(String simbolo){
        for (TabelaDeSimbolos tabela: pilhaDeEscopos){
            if(tabela.existeSimbolo(simbolo)){
                return true;
            }
        }
        return false;
    }
}
