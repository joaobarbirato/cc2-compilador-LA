package LACompiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;



public class Compilador{

    public static void main(String[] args) throws IOException, RecognitionException {
        SaidaParser out = new SaidaParser()/
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0])); //Essa linha garante que estamos pegando os casos de teste




    }

}