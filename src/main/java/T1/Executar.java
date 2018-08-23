package T1;

import LA.LALexer;
import LA.LAParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.*;

public class Executar {
    private final static String CAMINHO_CASOS_TESTE_SEM_ERRO = "casosDeTesteT1/3.arquivos_sem_erros/";
    private final static String CAMINHO_CASOS_TESTE_ERROS_SINTATICOS = "casosDeTesteT1/1.arquivos_com_erros_sintaticos/";
    private final static String CAMINHO_CASOS_TESTE_ERROS_SEMANTICOS = "casosDeTesteT1/2.arquivos_com_erros_semanticos/";
//    private static final boolean GERA = false;
//    private static final boolean VERIFICA = false;

    private static void executarSintaticos() throws IOException {
        File diretorioErros = new File ( CAMINHO_CASOS_TESTE_ERROS_SINTATICOS + "entrada" );
        File[] casosTeste = diretorioErros.listFiles ( );

        int totalCasosTeste = casosTeste.length;
        int casosTesteErrados = 0;

        assert casosTeste != null;
        for (File casoTeste : casosTeste) {

            SaidaParser out = new SaidaParser ( );
            ANTLRInputStream ais = new ANTLRInputStream ( new FileInputStream ( casoTeste ) );
            LALexer lexer;
            lexer = new LALexer ( ais );
            CommonTokenStream tokens = new CommonTokenStream ( lexer );
            LAParser parser = new LAParser ( tokens );
            parser.addErrorListener ( new T1ErrorListener ( out ) );

            parser.programa ( );

            if ( !out.isModificado ( ) ) {
                out.println ( "Fim da analise. Sem erros sintaticos." );
                out.println ( "Tabela de simbolos:" );

                TabelaDeSimbolos.imprimirTabela ( out );
                System.err.print ( out );
            } else {
                out.println ( "Fim da analise. Com erros sintaticos." );
            }

            File saidaCasoTeste = new File ( CAMINHO_CASOS_TESTE_ERROS_SINTATICOS + "/saida/" + casoTeste.getName ( ) );
            FileReader fr = new FileReader ( saidaCasoTeste );
            StringReader sr = new StringReader ( out.toString ( ) );

            int charFr = -1;
            int charSr = -1;
            boolean passou = true;
            while ((charFr = fr.read ( )) != -1 & (charSr = sr.read ( )) != -1) {
                if ( charFr != charSr ) {
                    casosTesteErrados++;
                    passou = false;
                    break;
                }
            }

            if ( passou ) {
                if ( (charFr == -1 && charSr != -1) ||
                        (charFr != -1 && charSr == -1) ) {
                    casosTesteErrados++;
                    passou = false;
                }
            }
            if ( !passou ) {
                System.out.println ( (passou ? "passou" : "falhou") + " - " + casoTeste.getName ( ) );
                System.out.println ( out.toString ( ) );
            }

        }
        double nota = ((double) (totalCasosTeste - casosTesteErrados) / totalCasosTeste) * 10.0d;
        System.err.println ( "Nota = " + nota + " (" + LAParser.grupo + ")" );
    }

    public static void main(String[] args) throws IOException {

        executarSintaticos ();

    }


}
