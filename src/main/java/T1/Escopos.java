package T1;

import java.util.List;
import java.util.LinkedList;

/* Pilha de tabelas de símbolos que tem como objetivo principal alterar o contexto.
   Quando se chama uma função, você tem uma tabela de símbolos da mesma */
public class Escopos {
    private LinkedList<TabelaDeSimbolos> pilhaDeTabelas;

    public Escopos() {
        pilhaDeTabelas = new LinkedList<TabelaDeSimbolos>();
        pilhaDeTabelas.push(new TabelaDeSimbolos("Principal"));
    }

    /* Empilha um escopo no topo da pilha de tabelas */
    public void empilhar(TabelaDeSimbolos tabela) {
        pilhaDeTabelas.push(tabela);
    }

    /* Retorna o topo da pilha */
    public TabelaDeSimbolos escopoAtual() {
        return pilhaDeTabelas.peek();
    }

    /* Função que percorre os escopos aninhados */
    public List<TabelaDeSimbolos> percorrerEscoposAninhados() {
        return pilhaDeTabelas;
    }

    /* Remove o topo da pilha */
    public void removerEscopo(){
        pilhaDeTabelas.pop();
    }
}
