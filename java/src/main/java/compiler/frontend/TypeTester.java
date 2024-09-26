package compiler.frontend;
import java_cup.runtime.*;
import java.io.*;
import java.util.*;

import compiler.common.*;
import compiler.frontend.Parser;
import compiler.frontend.Lexer;
import compiler.typing.*;
import compiler.typing.exceptions.*;

public class TypeTester {
	
	 public static void main(String[] args) throws FileNotFoundException, ParsingException{
	    if (args.length != 1) {
	        System.err.println("Usage: TypeTester <filePath>");
	        System.exit(1);
	    }

	    String filePath = args[0];
	    processTest(filePath);
	  }

	  static public Exp parseFile(String filePath) throws FileNotFoundException, ParsingException {
	    try {
	        Parser p = new Parser(new Lexer(new FileReader(filePath)));
	        Exp expression = (Exp) p.parse().value;
	        assert (expression != null);
	        return expression;
	    } catch (FileNotFoundException e) {
	        // Handle parsing-related exceptions
	        throw e;
	    } catch (Exception e) {
	        // Handle parsing-related exceptions
	        throw new ParsingException(filePath);
	    }
	  }

	  static public void processTest(String filePath) throws FileNotFoundException, ParsingException {
        Exp expression = parseFile(filePath);
        
        if (expression != null) {
            TypeChecking tc = TypeChecking.getInstance();
            tc.generateEquations(tc.env, expression, Type.gen());
            // tc.printingEquations();

            boolean isWellTyped = tc.unification();
            // tc.printFinalTypes();
            if (isWellTyped) {
              System.out.print("\u001B[32mThe given program is well typed !\u001B[0m");
//                System.exit(0);
            } else {
            	throw new IncompatibleTypeException();
            }
        } else {
            throw new NullExpressionException();
        }
	  }
}
