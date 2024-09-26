package compiler.backend.arm;

import compiler.backend.assembly.Assembler;
import compiler.backend.registers.Register;
import compiler.backend.registers.SymbolTable;
import compiler.backend.registers.SymbolTableEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import compiler.asml.*;

public class ArmGenerator {
	private SymbolTable symbolTable;
	private StringBuilder armCode;

	public ArmGenerator(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
		this.armCode = new StringBuilder();
	}

	public String generateArmFile(String filename, MainFunction mainProg, int num_live_vars) {
		String Starter = "\t.section .data\r\n"
    			+ "\t.section .text\r\n"
    			+ "\t.globl _start\n" ;
    	Starter += "_start:\n";
    	
    	armCode.append(Starter);

		processProgram(mainProg, num_live_vars);
		
	    String fullPath = "../arm/" + filename;

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	        writer.write(armCode.toString());
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null; // or handle the error as you see fit
	    }
	    return filename;
	}

	private void prologue(int num_live_vars) {
		armCode.append(Assembler.Prologue(num_live_vars * 4)); // Adjust size as needed
	}

	private void epilogue() {
		armCode.append(Assembler.Epilogue());
	}

	private void processProgram(MainFunction mainProg, int num_live_vars) {
		prologue(num_live_vars);
		for (Expression expr : mainProg.getExpressions()) {
			processExpression(expr);
		}
		epilogue();
		processExpression(new CallExpression("_min_caml_exit", null));
	}

	private void processExpression(Expression expr) {
		if (expr instanceof LetExpression) {
			processLetExpression((LetExpression) expr);
		} else if (expr instanceof ValueExpression) {
			processValueExpression((ValueExpression) expr);
		} else if (expr instanceof VariableExpression) {
			processVariableExpression((VariableExpression) expr, null);
		} else if (expr instanceof BinaryOperation) {
			processBinaryOperation((BinaryOperation) expr);
		} else if (expr instanceof CallExpression) {
			processFunctionCall((CallExpression) expr);
		}
	}
	
	private void processExpression(Expression expr, Register r) {
		if (expr instanceof VariableExpression) processVariableExpression((VariableExpression) expr, r);
	}

	private void processLetExpression(LetExpression letExpr) {
		armCode.append(Assembler.COMMENT("Process LetExpression"));
		if (letExpr.getAssignment() != null) {
			processExpression(letExpr.getAssignment());
			int stackOffset = symbolTable.getSymbolStackOffset(letExpr.getVariableName());
			armCode.append(Assembler.Store(Register.R4, Register.FP, stackOffset)); // Store the result on the stack
		}

	}

	private void processValueExpression(ValueExpression valExpr) {
		// E.g., move the literal value into a register
		String assembly = Assembler.COMMENT("Process ValueExpression");
		assembly += Assembler.MOV(Register.R4, valExpr.getValue());
		armCode.append(assembly);
	}

	private void processVariableExpression(VariableExpression varExpr, Register r) {
		if (r == null) {
			r = Register.R4;
		}
		// Loading a value from the stack into a register
		armCode.append(Assembler.COMMENT("Process VariableExpression: " + varExpr.getName()));
		// Retrieve the stack offset for the variable
		int stackOffset = symbolTable.getSymbolStackOffset(varExpr.getName());
		armCode.append(Assembler.Load(r, Register.FP, stackOffset));
	}

	private void processBinaryOperation(BinaryOperation binOp) {
		// Handle binary operations
		armCode.append(Assembler.COMMENT("Process BinaryOperation"));
		processExpression(binOp.getLeftOperand());

		processExpression(binOp.getRightOperand(), Register.R5);

		switch (binOp.getOperator()) {
		case "add":
			armCode.append(Assembler.ADD(Register.R4, Register.R5, Register.R4));
			break;
		case "sub":
			armCode.append(Assembler.SUB(Register.R4, Register.R5, Register.R4));
			break;
		}
	}

	private void processFunctionCall(CallExpression callExpr) {
		armCode.append(Assembler.COMMENT("Process FunctionCall"));
		if (callExpr.getArguments().size() > 4)
			throw new RuntimeException("Cant have more than 4 arguments in function: " + callExpr.getFunctionName());
		Register [] argumentRegisters = {Register.R0, Register.R1, Register.R2, Register.R3};
		
		for (int i = 0; i < callExpr.getArguments().size(); i++) {
			Expression argExpr = callExpr.getArguments().get(i);
			processExpression(argExpr, argumentRegisters[i]);
		}

		armCode.append(Assembler.BranchToLabel(callExpr.getFunctionName()));

	}

}
