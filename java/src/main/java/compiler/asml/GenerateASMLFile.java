package compiler.asml;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class GenerateASMLFile implements ASMLVisitor {

    // create a new file .asml
	private Boolean is_main = false;
    private String fileName;
    private PrintWriter writer;

    // Constructor to initialize the file name and writer
    public GenerateASMLFile(String fileName) {
        this.fileName = fileName;
        try {
            this.writer = new PrintWriter(fileName, "UTF-8");
            writer.println("let _ = ");

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Ensure to close the PrintWriter
    public void closeWriter() {
        if (writer != null) {
            writer.close();
        }
    }
    
    @Override
    public void visit(ValueExpression e) {
        writer.print(e.getValue());
    }

    @Override
    public void visit(BinaryOperation e) {
        
        String op = e.getOperator();
        writer.print("\t"+ op + " ");
        e.getLeftOperand().accept(this);
        e.getRightOperand().accept(this);

    }

    // print sequence of Expression
    void printSequence(List<Expression> expressions, String op) {
        
        if (expressions.size() == 0) {
            return;
        }

        Iterator<Expression> it = expressions.iterator();
        it.next().accept(this);
        while (it.hasNext()) {
            writer.print(op);
            it.next().accept(this);
        }
    }

    // print sequence of identifiers
    <E> void printInfix(List<E> l, String op) {
        if (l.isEmpty()) {
            return;
        }
        
        Iterator<E> it = l.iterator();
        writer.print(it.next()); // Print the first element
    
        while (it.hasNext()) {
            writer.print(op);    // Print the operator
            writer.print(it.next()); // Print the next element
        }
    }

    @Override
    public void visit(CallExpression e) {
        
        writer.print("\tcall "+e.getFunctionName());
        writer.print(" ");
        printSequence(e.getArguments(), " ");
        
    }

    @Override
    public void visit(VariableExpression e) {
        
        writer.print(e.getName());
        writer.print(" ");
    }

    @Override
    public void visit(LetExpression e) {
        
        writer.print("\tlet ");
        writer.print(e.getVariableName());
        writer.print(" = ");
        e.getAssignment().accept(this);
        writer.println(" in ");
        e.getBody().accept(this);

    }

    @Override
    public void visit(ConditionalExpression e) {
        writer.print("ConditionalExpression");
    }

    @Override
    public void visit(MainFunction e) {
        writer.println("let _ = ");
    }

    @Override
    public void visit(FunctionCallExpression e) {
        writer.println("FunctionCallExpression");
    }

    @Override
    public void visit(FunctionDefinition e) {
    	is_main = true;
        writer.print("let " + e.getFunctionName() + " ");
        List<Expression> parameters = e.getParameters();
        List<String> parametersName = new ArrayList<String>();
        for (Expression parameter : parameters) {
            parametersName.add(((VariableExpression) parameter).getName());
        }
        printInfix(parametersName, " ");
        writer.println(" = ");
        printSequence(e.getBody(), " ");
        writer.println("in");
        is_main = false;
    }


}
