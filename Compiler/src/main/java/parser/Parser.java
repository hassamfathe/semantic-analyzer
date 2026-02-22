package parser;

import lexer.Token;
import parser.ASTNode;

import java.util.List;


public class Parser {

    private List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ASTNode parse() {
        ASTNode program = new ASTNode(NodeType.PROGRAM);
        program.children.add(parseBlock());
        return program;
    }

    private ASTNode parseBlock() {

    if (!tokens.get(pos).value.equals("{")) {
        throw new RuntimeException("Expected '{'");
    }
    pos++; 

    ASTNode block = new ASTNode(NodeType.BLOCK);

    while (!tokens.get(pos).value.equals("}")) {
        block.children.add(parseStatement());
    }

    pos++; 

    return block;
}

    private ASTNode parseStatement() {

        Token current = tokens.get(pos);
        if (current.type.equals("TYPE")) {
            return parseVarDecl();
        } else if (current.type.equals("IDENTIFIER")) {
            return parseAssign();
        }

        throw new RuntimeException("Unexpected token: " + current.value);


    }

    private ASTNode parseVarDecl() {
        Token typeToken = tokens.get(pos++);
        Token nameToken = tokens.get(pos++);

        if (!tokens.get(pos).value.equals(";")) {
            throw new RuntimeException("Missing semicolon at line " + nameToken.line);
        }
        pos++;

        ASTNode node = new ASTNode(NodeType.VAR_DECL);
        node.name = nameToken.value;
        node.dataType = typeToken.value;
        node.line = nameToken.line;

        return node;

    }

    // assign → IDENTIFIER = IDENTIFIER ;
    private ASTNode parseAssign() {
        Token left = tokens.get(pos++); // IDENTIFIER

        if (!tokens.get(pos).value.equals("=")) {
            throw new RuntimeException("Expected '=' at line " + left.line);
        }
        pos++; // skip '='

        Token right = tokens.get(pos++); // IDENTIFIER

        if (!tokens.get(pos).value.equals(";")) {
            throw new RuntimeException("Missing semicolon at line " + right.line);
        }
        pos++; // skip ;

        ASTNode assign = new ASTNode(NodeType.ASSIGN);
        assign.line = left.line;

        ASTNode leftNode = new ASTNode(NodeType.IDENTIFIER);
        leftNode.name = left.value;

        ASTNode rightNode = new ASTNode(NodeType.IDENTIFIER);
        rightNode.name = right.value;

        assign.children.add(leftNode);
        assign.children.add(rightNode);

        return assign;
    }
}
