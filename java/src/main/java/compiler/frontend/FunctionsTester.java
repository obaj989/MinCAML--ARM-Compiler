package compiler.frontend;

import java.io.*;
import java.util.*;

import compiler.asml.Expression;
import compiler.asml.GenerateASMLFile;
import compiler.asml.PrintVisitorASML;
import compiler.asml.Program;
import compiler.common.*;

public class FunctionsTester {
	static public Program processFile(String filePath) throws Exception {


	  Exp k_expression = kNormExpression(filePath);

      Exp a_expression = alphaConversionExpression(k_expression);

      Exp n_expression = nestedLetExpression(a_expression);

      	// System.out.println("------ ASMLConverter ----"); // which takes the Nested
   		// Let as input and converts it to ASML
   		ASMLExpressionConvert asml = new ASMLExpressionConvert();

   		Program program = new Program();
   		// reverse the expressions list
   		Expression asml_exp = n_expression.accept(asml);
   		List<Expression> program_formatted = (List<Expression>) asml.getMainFunction().getExpressions();
   		Collections.reverse(program_formatted);

   		program.addExpression(asml.getMainFunction());

   		asml_exp.accept(new PrintVisitorASML());

   		return program;

	}

	static public void parseFunction(String filePath) {
		try {
			Parser p = new Parser(new Lexer(new FileReader(filePath)));
			Exp expression = (Exp) p.parse().value;
			assert (expression != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public void ASMLFileGenerator(String filePath, String outputFilePath) throws Exception {

		Exp k_expression = kNormExpression(filePath);

		Exp a_expression = alphaConversionExpression(k_expression);

		Exp n_expression = nestedLetExpression(a_expression);

		ASMLExpressionConvert asml = new ASMLExpressionConvert();
		Program program = new Program();
		// Define the main function
		Expression asml_exp = n_expression.accept(asml);
		List<Expression> program_formatted = (List<Expression>) asml.getMainFunction().getExpressions();
		Collections.reverse(program_formatted);

		program.addExpression(asml.getMainFunction());
		GenerateASMLFile generator = new GenerateASMLFile(outputFilePath);
		asml_exp.accept(generator);
		generator.closeWriter();
		asml_exp.accept(new PrintVisitorASML());
	}

	static public Exp kNormExpression(String filePath) throws Exception {
		Parser p = new Parser(new Lexer(new FileReader(filePath)));
		Exp expression = (Exp) p.parse().value;
		assert (expression != null);

		// System.out.println("------ ASML File Generator ------");
		// System.out.println();

		// System.out.println("------ AST ------");
		expression.accept(new PrintVisitor());
		// System.out.println();

		// System.out.println("------ Height of the AST ----");
		int height = Height.computeHeight(expression);
		// System.out.println("using Height.computeHeight: " + height);

		ObjVisitor<Integer> v = new HeightVisitor();
		height = expression.accept(v);
		// System.out.println("using HeightVisitor: " + height);

		// System.out.println("------ K-normalization ----");
		K_normalization k = new K_normalization();
		Exp k_expression = expression.accept(k);
		k_expression.accept(new PrintVisitor());
		// System.out.println();

		return k_expression;
	}

	static public Exp alphaConversionExpression(Exp knormExp) throws Exception {

		// k-normalized AST as input
		AlphaConversion a = new AlphaConversion();
		Exp a_expression = knormExp.accept(a);
		a_expression.accept(new PrintVisitor());
		// System.out.println();

		return a_expression;
	}

	static public Exp nestedLetExpression(Exp alphaExp) throws Exception {

		// which takes the alpha-converted AST as input
		NestedLet n = new NestedLet();
		Exp n_expression = alphaExp.accept(n);
		n_expression.accept(new PrintVisitor());
		return n_expression;
	}
}
