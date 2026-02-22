
import lexer.Lexer;
import parser.Parser;
import parser.ASTNode;
import semantic.SemanticAnalyzer;

public class Main {
    public static void main(String[] args) {
        String code = """
            {
                int x;
                int x;
                x = y;
            }
            z = x;
        """;

        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer.tokenize());
        ASTNode ast = parser.parse();

        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        analyzer.analyze(ast);
    }
}
