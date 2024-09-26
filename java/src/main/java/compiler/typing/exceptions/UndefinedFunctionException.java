package compiler.typing.exceptions;


public class UndefinedFunctionException extends RuntimeException {

	public UndefinedFunctionException(String functionName) {
        super("\u001B[31m The function " + functionName + " is used without being defined !\u001B[0m");
    }
}
