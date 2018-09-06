package T1;

import java.util.List;
import java.util.LinkedList;

public class Escopos {
    private LinkedList<TabelaDeSimbolos> pilhaDeTabelas;

    public Escopos() {
        pilhaDeTabelas = new LinkedList<TabelaDeSimbolos>();
        pilhaDeTabelas.push(new TabelaDeSimbolos("Principal"));
    }

    public void empilhar(TabelaDeSimbolos tabela) {
        pilhaDeTabelas.push(tabela);
    }

    public TabelaDeSimbolos escopoAtual() {
        return pilhaDeTabelas.peek();
    }

    public List<TabelaDeSimbolos> percorrerEscoposAninhados() {
        return pilhaDeTabelas;
    }

    public void removerEscopo(){
        pilhaDeTabelas.pop();
    }
}
