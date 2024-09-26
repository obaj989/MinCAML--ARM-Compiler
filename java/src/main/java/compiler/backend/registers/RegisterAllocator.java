package compiler.backend.registers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import compiler.asml.*;

public class RegisterAllocator {
	private Map<String, Integer> registerMap = new HashMap<>();
    private List<String> spillList = new ArrayList<>();
    private LinkedList<String> usageOrder = new LinkedList<>();
    private int nextRegister = 0;
    private static final int MAX_REGISTERS = 10; // Assume a limit for demonstration

    public void allocate(Program program) {
        for (Expression expr : program.getExpressions()) {
            allocateForExpression(expr);
        }
    }

    private void allocateForExpression(Expression expr) {
        if (expr instanceof LetExpression) {
            LetExpression letExpr = (LetExpression) expr;
            allocateForExpression(letExpr.getAssignment());
            allocateRegister(letExpr.getVariableName());
            if (letExpr.getBody() != null) {
                allocateForExpression(letExpr.getBody());
            }
        } else if (expr instanceof BinaryOperation) {
            BinaryOperation binOp = (BinaryOperation) expr;
            allocateForExpression(binOp.getLeftOperand());
            allocateForExpression(binOp.getRightOperand());
        } else if (expr instanceof CallExpression) {
            CallExpression callExpr = (CallExpression) expr;
            for (Expression arg : callExpr.getArguments()) {
                allocateForExpression(arg);
            }
        } else if (expr instanceof VariableExpression) {
            String varName = ((VariableExpression) expr).getName();
            usageOrder.remove(varName); // Remove if already present
            usageOrder.addFirst(varName); // Add to the front as most recently used
        }
    }

    private void allocateRegister(String variableName) {
        if (!registerMap.containsKey(variableName)) {
            if (nextRegister < MAX_REGISTERS) {
                registerMap.put(variableName, nextRegister++);
            } else {
                handleSpill();
                registerMap.put(variableName, nextRegister - 1); // Reuse the spilled register
            }
        }
        usageOrder.remove(variableName); // Remove if already present
        usageOrder.addFirst(variableName); // Add to the front as most recently used
    }

    private void handleSpill() {
        if (!usageOrder.isEmpty()) {
            String variableToSpill = usageOrder.getLast();
            spillList.add(variableToSpill);
            usageOrder.removeLast();
            // Remove the mapping from registerMap, freeing up a register
            registerMap.remove(variableToSpill);
        }
    }
    public Map<String, Integer> getRegisterMap() { return registerMap; }
    public List<String> getSpillList() { return spillList; }

    public Program makeModifiedArithmeticProgram() {

        Program program = new Program();
        
        // Define the main function
        MainFunction mainFunction = new MainFunction();

        // let x = 10
        LetExpression letX = new LetExpression("x", new ValueExpression(10), null);

        // let y = 20
        LetExpression letY = new LetExpression("y", new ValueExpression(20), null);

        // add x y
        BinaryOperation addXY = new BinaryOperation("add", new VariableExpression("x"), new VariableExpression("y"));

        // let sum = add x y
        LetExpression letSum = new LetExpression("sum", addXY, null);

        // call _min_caml_print_int sum
        CallExpression callPrint = new CallExpression("_min_caml_print_int",
                Arrays.asList(new VariableExpression("sum")));

        // Add expressions to the main function
        mainFunction.addExpression(letX);
        mainFunction.addExpression(letY);
        mainFunction.addExpression(letSum);
        mainFunction.addExpression(callPrint);

        // Add the main function to the program
        program.addExpression(mainFunction);

        return program;
    }

	// Create the program

}
