//package compiler.backend;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import compiler.asml.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.util.Arrays;
//import org.junit.jupiter.api.AfterEach;
//
//public class MachineCodeGeneratorTest {
//	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//	private final PrintStream originalOut = System.out;
//	private BackendAnalyzer backendAnalyzer;
//
//	@BeforeEach
//	public void setUp() {
//		System.setOut(new PrintStream(outContent)); // Redirect System.out for capturing output
//	}
//
//	@Test
//	public void testGenerateArmWithValidProgram() throws IOException, InterruptedException {
//		Program program = createSimpleArithmeticProgram();
//		generateArm(program);
//		String generatedCode = outContent.toString();
//		String expectedCode = "MOV R1, #10\n" +
//                			  "MOV R2, #20\n" +
//                              "ADD R3, R1, R2\n" +
//                              "BL _min_caml_print_int\n";
//		assertEquals(expectedCode, generatedCode.trim());
//	}
//
//	@Test
//	public void testGenerateArmWithEmptyProgram() throws IOException, InterruptedException {
//		Program program = new Program();
//		generateArm(program);
//		String generatedCode = outContent.toString();
//		assertEquals("", generatedCode.trim());
//	}
//
//	private Program createSimpleArithmeticProgram() {
//		Program program = new Program();
//		LetExpression letX = new LetExpression("x", new ValueExpression(10), null);
//		LetExpression letY = new LetExpression("y", new ValueExpression(20), null);
//		BinaryOperation addXY = new BinaryOperation("add", new VariableExpression("x"), new VariableExpression("y"));
//		LetExpression letSum = new LetExpression("sum", addXY, null);
//		CallExpression callPrint = new CallExpression("_min_caml_print_int",
//				Arrays.asList(new VariableExpression("sum")));
//		program.addExpression(letX);
//		program.addExpression(letY);
//		program.addExpression(letSum);
//		program.addExpression(callPrint);
//		return program;
//	}
//
//	private void generateArm(Program program) throws IOException, InterruptedException {
//		String output = "../ARM/demo123545.S";
//		backendAnalyzer.generateArm(program, output);
//	}
//
//	@AfterEach
//	public void tearDown() {
//		System.setOut(originalOut); // Restore System.out to its original state
//	}
//}
