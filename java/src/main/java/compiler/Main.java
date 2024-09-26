package compiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import compiler.asml.Program;
import compiler.backend.*;
import compiler.frontend.*;
import compiler.typing.exceptions.*;

public class Main {
	public static void main(String[] args){
		try {
			// Check if any command-line arguments were provided
			if (args.length == 0) {
				System.err.println("No arguments provided. Use -help for help.");
				System.exit(1);
			}
			
			int i = 0;
	
			// Parse and process the command-line arguments
			String arg = args[i];
            switch (arg) {
                case "-o":
                    // Process the -o option and get the output file name from args[i+1]
                    if (i + 2 < args.length) {
                        String outputFile = args[i + 2];
                        // Implement logic to handle the output file
                        
                        String inputFile = args[i + 1];
                        TypeTester.processTest(inputFile);
                        
                        Program p = FunctionsTester.processFile(inputFile);
                        
                        BackendAnalyzer backend = new BackendAnalyzer();
                        backend.generateArm(p, outputFile);
                        
                    } else {
                        System.err.println("-o option requires an argument.");
                        System.exit(1);
                    }
                    break;
                case "-help":
                    // Display help and exit
                    displayHelp();
                    System.exit(0);
                    break;
                case "-v":
                    // Display version information and exit
                    displayVersion();
                    System.exit(0);
                    break;
                 case "-t":
                    if (i + 1 < args.length) {
                        String inputFile = args[i + 1];
                        TypeTester.processTest(inputFile);
                    }
                    else{
                        System.out.println("-t option requires an argument.");
                    }
                    
                    break;
                case "-p":
                    // Parse only logic here
                    System.out.println("Parsing only.");
                    System.out.println("----Parse---.");
                    if (i + 1 < args.length) {
                        String inputFile = args[i + 1];
                        FunctionsTester.parseFunction(inputFile);
                    }
					 break;
                case "-asml":
                if (i + 2 <= args.length) {
                    // Output ASML logic here
                    String outputFile = args[i + 2];
                    // Implement logic to handle the output file
                    
                    String inputFile = args[i + 1];
                    TypeTester.processTest(inputFile);
                    
                    FunctionsTester.ASMLFileGenerator(inputFile,outputFile);
                                                        }
                else{
                    System.err.println("-asml option requires an argument.");
                    System.exit(1);
                }
                    
                    break;

                case "-b":
                	if (i + 1 < args.length) {
                		String outputFile2 = args[i + 1];
	                	BackendAnalyzer backend1 = new BackendAnalyzer();
                        backend1.generateArm(null, outputFile2);
                    } else {
                        System.err.println("-b option requires an output file path.");
                        System.exit(1);
                    }
                    break;
                    
                    case "-test-all":
                    runCommand("mvn test");
                    break;
                    case "-test-backend":
                        runCommand("mvn exec:exec@test-backend");
                        break;
                    case "-test-typeChecking":
                    runCommand("mvn exec:exec@test-typeChecking");
                        break;
                    case "-test-frontend":
                    runCommand("mvn exec:exec@test-frontend");
                        break;

                    default:
                    System.err.println("Unknown option: " + arg);
                    System.exit(1);
            }
			System.exit(0);
		} catch (UndefinedFunctionException | NotAFunctionException | IncorrectParametersException | 
                UnexpectedExpressionException | UndeclaredVariableException | ParsingException |
                IncompatibleTypeException | NullExpressionException | FileNotFoundException e) {
           System.out.println(e.getMessage());
           System.exit(1);
       } catch (Exception e) {
           System.out.println("An unexpected error occurred: " + e.getMessage());
           System.exit(1);
       }
    
	}

    private static void displayHelp() {
        System.out.println("Usage: java compiler.Main [options]");
        System.out.println("Options:");
        System.out.println("  -o <input file> <output file>      : Compiles the input file and generates an output file.");
        System.out.println("  -help                              : Displays this help message.");
        System.out.println("  -v                                 : Displays version information.");
        System.out.println("  -t <file>                          : Performs type checking on the specified file.");
        System.out.println("  -p <file>                          : Parses the specified file only, without further processing.");
        System.out.println("  -asml <input file> <output file>   : Generates ASML code for the input file and outputs to the specified file.");
        System.out.println("  -b <output file>                   : Executes backend analysis and generates output to the specified file.");
        System.out.println("  -test-all                          : Runs all the test cases automatically.");
        System.out.println("  -test-backend                      : Runs backend tests.");
        System.out.println("  -test-typeChecking                 : Runs type checking tests.");
        System.out.println("  -test-frontend                     : Runs frontend tests.");
    }
    
	private static void displayVersion() {
		// Display version information here
		System.out.println("Compiler Version 1.0");
	}
    private static void runCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Read the output of the command
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Command executed successfully");
            } else {
                System.err.println("Command failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error running the command: " + e.getMessage());
            System.exit(1);
        }
    }
}