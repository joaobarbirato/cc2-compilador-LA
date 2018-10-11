/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package T1;

/**
 *
 * @author daniel
 */

/* Entrada da tabela de simbolos se refere as linhas da tabela, que, por sua vez, possuem nome e tipo */
public class EntradaTabelaDeSimbolos {
    private String nome, tipo;

    public EntradaTabelaDeSimbolos(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    /* Retorna o nome na tabela */
    public String getNome() {
        return nome;
    }

    /* Retorna o tipo na tabela */
    public String getTipo() {
        return tipo;
    }

    /* Função que transforma em string */
    @Override
    public String toString() {
        return nome+"("+tipo+")";
    }
}
