/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package T1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class TabelaDeSimbolos {
    private String escopo;
    private List<EntradaTabelaDeSimbolos> simbolos;

    public TabelaDeSimbolos(String escopo) {
        simbolos = new ArrayList<EntradaTabelaDeSimbolos>();
        this.escopo = escopo;
    }

    public void printEntradas () {
        for(EntradaTabelaDeSimbolos e : simbolos) {
            System.out.println ( e.getNome ( ) );
            System.out.println ( e.getTipo ( ) );
        }
    }

    public void adicionarSimbolo(String nome, String tipo) {
        simbolos.add(new EntradaTabelaDeSimbolos(nome,tipo));
    }

    public void adicionarSimbolos(List<String> nomes, String tipo) {
        for(String s:nomes) {
            simbolos.add(new EntradaTabelaDeSimbolos(s, tipo));
        }
    }

    public List<EntradaTabelaDeSimbolos> getSimbolos(){
        return simbolos;
    }

    public boolean existeStructId(String id){
        for ( EntradaTabelaDeSimbolos etds : simbolos )
            if ( etds.getNome ().equals ( id ) && etds.getTipo ().equals ( "struct" ) )
                return true;
        return false;
    }

    public boolean existeSimbolo(String nome) {
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            if(etds.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    public String getTipo(String nome) {
        for(EntradaTabelaDeSimbolos etds:simbolos){
            if (etds.getNome().equals(nome)) {
                return etds.getTipo();
            }
        }
        return "false";
    }

    public EntradaTabelaDeSimbolos getEntrada(String nome){
        for(EntradaTabelaDeSimbolos ets:simbolos){
            if( ets.getNome ( ).equals ( nome ) )
                return ets;
        }
        return null;
    }

    @Override
    public String toString() {
        String ret = "Escopo: "+escopo;
        for(EntradaTabelaDeSimbolos etds:simbolos) {
            ret += "\n   "+etds;
        }
        return ret;
    }
}