package T1;

import LA.LALexer;
import LA.LAParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Principal {
    public static void main(String [] args) throws IOException {
        SaidaParser sp = new SaidaParser();
        CharStream input = CharStreams.fromFileName ( args[0] );
        try (PrintWriter output = new PrintWriter ( new FileWriter ( args[1] ) )) {

            LALexer lexer = new LALexer ( input );
            CommonTokenStream stream = new CommonTokenStream ( lexer );
            LAParser parser = new LAParser( stream );

            parser.removeErrorListeners ();

            T1ErrorListener tel = new T1ErrorListener ( sp );
            parser.addErrorListener ( tel );

            LAParser.ProgramaContext arvore = null;

            try {
                arvore = parser.programa ( );
            } catch(ParseCancellationException pce) {
                if(pce.getMessage() != null) {
                    output.println(pce.getMessage ());
                }
            }

            if(!sp.modificado) {
                sp.reset();
                T1Visitor semantico = new T1Visitor(sp);
                semantico.visitPrograma(arvore);
            }

            output.print(sp.toString ());
            output.println("Fim da compilacao");
        }
    }
}
