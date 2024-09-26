package compiler.asml;

public interface ASMLVisitor  {
    
    void visit(ValueExpression e);
    void visit(BinaryOperation e);
    void visit(CallExpression e);
    void visit(VariableExpression e);
    void visit(LetExpression e);
    void visit(ConditionalExpression e);
    void visit(MainFunction e);
    void visit(FunctionCallExpression e);
    void visit(FunctionDefinition e);
    
}
