import java.io.IOException;
import java.util.*;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class Java8ParserTest_Part_3 extends Java8BaseListener{

	public static void main(String[] args) throws IOException {
		if(args.length<1)
		{
			System.err.println("java Java8ParserTest input-filename\n"
					+"Example: java Java8ParserTest Test.java");
			return;
		}
		String inputFile = args[0];
    	CharStream input = new ANTLRFileStream(inputFile);
    	Java8Lexer lexer = new Java8Lexer(input);
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
    	Java8Parser parser = new Java8Parser(tokens); //create parser

    	ParseTree tree = parser.compilationUnit();
    	ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
    	// Java8ParserTest listener = new Java8ParserTest(); // create a parse tree listener
    	Java8ifListener extractor = new Java8ifListener(tokens);

		walker.walk(extractor, tree); // traverse parse tree with listener
		System.out.print("########################## New Code Starts here ##########################\n");
		System.out.print(extractor.rewriter.getText());
    }
}


	