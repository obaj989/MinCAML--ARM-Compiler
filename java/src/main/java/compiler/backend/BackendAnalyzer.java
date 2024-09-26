package compiler.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import compiler.asml.*;
import compiler.backend.arm.ArmGenerator;
import compiler.backend.registers.RegisterAllocator;
import compiler.backend.registers.SymbolTable;

public class BackendAnalyzer {
	private SymbolTable symbolTable;

	public BackendAnalyzer() {
		this.symbolTable = new SymbolTable();
	}

	public void generateArm(Program program, String filename) throws IOException, InterruptedException {

	//	Program program = allocateRegister();
		if (program == null) program = allocateRegister();
		MainFunction mainProg = (MainFunction) program.getExpressions().get(0);
		
		symbolTable.enterScope(); // Enter the main function's scope
		analyze(mainProg);
		ArmGenerator codeGenerator = new ArmGenerator(symbolTable);
		int liveVar = countLiveVariables(mainProg);
		// Generate assembly-like code for each expression in the program
		String assemblyCode = codeGenerator.generateArmFile(filename, mainProg,liveVar);
		generateARMFile(filename);
		System.out.println(assemblyCode);
		symbolTable.exitScope(); // Exit the main function's scope

	}

	public Program allocateRegister() {
		RegisterAllocator allocator = new RegisterAllocator();
		allocator.allocate(allocator.makeModifiedArithmeticProgram());
		Program program = allocator.makeModifiedArithmeticProgram();

		System.out.println("Register Allocation:");
		allocator.getRegisterMap()
				.forEach((var, reg) -> System.out.println("Variable " + var + " allocated to register " + reg));

		if (!allocator.getSpillList().isEmpty()) {
			System.out.println("Spilled Variables: " + allocator.getSpillList());
		}
		return program;
	}

    public int countLiveVariables(MainFunction mainFunction) {

        // Count live variables at each point and find the maximum
        int liveVariableCount = 0;
        List<Expression> expressions = mainFunction.getExpressions();
        for (int i = 0; i < expressions.size(); i++) {
        	Expression expr = expressions.get(i);
        	if (expr instanceof LetExpression) {
        		LetExpression letExpr = (LetExpression) expr;
        		liveVariableCount++;
        	}
        }

        return liveVariableCount;
    }

    
	public void analyze(MainFunction mainFunction) {

		for (Expression expression : mainFunction.getExpressions()) {
			analyzeExpression(expression);
		}

	}

	private void analyzeExpression(Expression expression) {
		if (expression instanceof LetExpression) {
			LetExpression letExpr = (LetExpression) expression;
			String varName = letExpr.getVariableName();
			String varType = letExpr.getVariableName();

			if (!symbolTable.containsSymbol(varName)) {
				symbolTable.addSymbolToCurrentScope(varName, varType);
				// Analyze the initialization expression, if any
				if (letExpr.getAssignment() != null) {
					analyzeExpression(letExpr.getAssignment());
				}
			} else {
				throw new RuntimeException("Variable already declared in this scope: " + varName);
			}
		} else if (expression instanceof VariableExpression) {
			VariableExpression varExpr = (VariableExpression) expression;
			String varName = varExpr.getName();
			if (!symbolTable.containsSymbol(varName)) {
				throw new RuntimeException("Variable not declared: " + varName);
			}
		} else if (expression instanceof BinaryOperation) {
			BinaryOperation binExpr = (BinaryOperation) expression;
			analyzeExpression(binExpr.getLeftOperand());
			analyzeExpression(binExpr.getRightOperand());
		}
	}
	
	public static int generateARMFile(String sFile) throws IOException, InterruptedException {
        String fileNameWithOutExt = sFile.replaceFirst("[.][^.]+$", "");

        // Compile the assembly file
        Process compileProcess = new ProcessBuilder(
                "arm-none-eabi-as",
                "-march=armv7-a+neon",
                "-mfloat-abi=hard",
                "-mfpu=neon-vfpv4",
                "-o", fileNameWithOutExt + ".o",
                sFile,
                "../ARM/libmincaml.S"
        ).start();
        compileProcess.waitFor();

        // Link the object file
        Process linkProcess = new ProcessBuilder(
                "arm-none-eabi-ld",
                "-o", fileNameWithOutExt + ".arm",
                fileNameWithOutExt + ".o"
        ).start();
        linkProcess.waitFor();

        System.out.println("Successfully converted your s file '" + sFile + "' and stored it as " + fileNameWithOutExt + ".o");
        
        return 0;
	}
}
