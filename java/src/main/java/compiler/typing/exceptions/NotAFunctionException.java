package compiler.typing.exceptions;


public class NotAFunctionException extends RuntimeException {
	
	public NotAFunctionException(String functionName) {
        super("\u001B[31m"+functionName + " is not a function !\u001B[0m");
    }
}
