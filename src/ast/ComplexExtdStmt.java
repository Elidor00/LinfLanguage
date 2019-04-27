package ast;

import ast.ComplexExtdType;
import ast.Node;
import utils.Environment;

public abstract class ComplexExtdStmt implements Node {
    @Override
    public ComplexExtdType checkType(Environment env) {
        return null;
    }
}
