package semantic;


import semantic.Symbol;
import errors.SemanticError;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {

    private Stack<Map<String, Symbol> > scopes = new Stack<>();

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        scopes.pop();
    }

    public void declare(String name, String type, int line) {
        Map<String, Symbol> current = scopes.peek();
        if (current.containsKey(name)) {
            throw new SemanticError(line, "Redeclaration of " + name);
        }
        current.put(name, new Symbol(name, type));
    }

    public String lookup(String name, int line) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return scopes.get(i).get(name).type;
            }
        }
        throw new SemanticError("Line " + line + ": Undeclared variable " + name);
    }

}