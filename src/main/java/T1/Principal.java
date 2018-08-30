package T1;

import LA.LALexer;
import LA.LAParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

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

            parser.programa ();

            output.print(sp.toString ());
            output.println("Fim da compilacao");
        }
    }
}
