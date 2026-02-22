
package errors;

public class SemanticError extends RuntimeException{

    public SemanticError(String message) {
        super(message);
    }

    public SemanticError(int line, String message) {
        super("Semantic Error at line " + line + ": " + message);
    }
    
}
