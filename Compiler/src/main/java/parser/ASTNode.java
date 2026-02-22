package parser;

import parser.NodeType;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    public NodeType type;
    public String name;
    public String dataType;
    public int line;
    public List<ASTNode> children = new ArrayList<>();

    public ASTNode(NodeType type) {
        this.type = type;
    }
    
}