
package semantic;

import semantic.SymbolTable;
import parser.ASTNode;
import errors.SemanticError;

public class SemanticAnalyzer {

    private SymbolTable table = new SymbolTable();

    public void analyze (ASTNode root) {
        table.enterScope();
        visit(root);
        table.exitScope();
    }

    private void visit (ASTNode node) {
        switch (node.type) {

            case PROGRAM, BLOCK -> {
                table.enterScope();
                for (ASTNode child : node.children) {
                    visit(child);
                }
                table.exitScope();
            }
            case VAR_DECL -> {
                table.declare(node.name, node.dataType, node.line);
            }
            case ASSIGN -> {
                String lhs = table.lookup(node.children.get(0).name, node.line);
                String rhs = table.lookup(node.children.get(1).name, node.line);
                if (!lhs.equals(rhs)) {
                    throw new SemanticError("Line " + node.line + ": Type mismatch");
                }
            }
            case IDENTIFIER -> {
                table.lookup(node.name, node.line);
            }
        }
    }

}
