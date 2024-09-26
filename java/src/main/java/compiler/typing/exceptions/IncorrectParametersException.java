package compiler.typing.exceptions;


public class IncorrectParametersException extends RuntimeException {
	
    public IncorrectParametersException(String functionName, int calling_param, int ftn_param) {
        super("\u001B[31m Function " + functionName + " has "+ftn_param+" parameters " +"but calling with "+calling_param+" parameters !\u001B[0m");
    }
}
