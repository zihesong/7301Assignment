import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.TokenStreamRewriter;
import java.util.regex.*;

public class Java8ifListener extends Java8BaseListener {
	TokenStreamRewriter rewriter;

    public Java8ifListener(TokenStream tokens) {
        rewriter = new TokenStreamRewriter(tokens);
    }

    @Override
    public void enterStatement(Java8Parser.StatementContext ctx) {
        
        if(ctx.getStart().getText().equals("if")) {
			String child = ctx.getChild(0).getText();
			int first = child.indexOf("(");
			int last = child.indexOf(")");
            child = child.substring(first + 1, last);
            
			Pattern p = Pattern.compile("[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t");
            Matcher m = p.matcher(child);

            if(!m.find() && child.length()>3) {
                int line = ctx.getStart().getLine();   
                System.out.println(rewriter.getText());
                int ix = ctx.start.getTokenIndex();
                while(!(rewriter.getTokenStream().get(ix).getText().equals(")"))) {
                    ix++;
                }
                if (rewriter.getTokenStream().get(ix+2).getText().equals("{")) {
                    rewriter.insertAfter(rewriter.getTokenStream().get(ix+2), "\n\t\t\t\tSystem.out.println(\""+child+" "+line+"\");\n\t\t\t\t");
                } else {
                    rewriter.insertAfter(rewriter.getTokenStream().get(ix), "{\n\t\t\t\tSystem.out.println(\""+child+" "+line+"\");\n\t\t\t\t");
                    while(!(rewriter.getTokenStream().get(ix).getText().equals(";")) && !(rewriter.getTokenStream().get(ix).getText().equals("}")) ) {
                        ix++;
                    }
                    rewriter.insertAfter(rewriter.getTokenStream().get(ix), "}");
                }
            }
        }
    }
}   