package T1;

import java.util.List;
import java.util.LinkedList;

/* Pilha de tabelas de símbolos que tem como objetivo principal alterar o contexto.
   Quando se chama uma função, você tem uma tabela de símbolos da mesma */
public class Escopos {
    private LinkedList<TabelaDeSimbolos> pilhaDeEscopos;

    public Escopos() {
        pilhaDeEscopos = new LinkedList <> ( );
        pilhaDeEscopos.push(new TabelaDeSimbolos("Principal"));
    }

    public Escopos(TabelaDeSimbolos Tabela) {
        pilhaDeEscopos = new LinkedList <> ( );
        pilhaDeEscopos.push(Tabela);
    }

    /* Empilha um escopo no topo da pilha de tabelas */
    public void empilhar(TabelaDeSimbolos tabela) {
        pilhaDeEscopos.push(tabela);
    }

    /* Retorna o topo da pilha */
    public TabelaDeSimbolos escopoAtual() {
        return pilhaDeEscopos.peek();
    }

    /* Função que percorre os escopos aninhados */
    public List<TabelaDeSimbolos> percorrerEscoposAninhados() {
        return pilhaDeEscopos;
    }

    /* Remove o topo da pilha */
    public void desempilhar(){
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

    public String getSimboloTipo(String nome){
        for(TabelaDeSimbolos tabela: pilhaDeEscopos){
            if(tabela.existeSimbolo(nome)){
                return tabela.getTipo(nome);
            }
        }
        return "falso";
    }
    public void adicionaSimboloTopo (EntradaTabelaDeSimbolos ets){
        this.escopoAtual ().adicionarSimbolo ( ets.getNome (), ets.getTipo () );
    };

    public void adicionaSimboloTopo (String nome, String tipo){
        this.escopoAtual ().adicionarSimbolo ( nome, tipo );
    };
}
