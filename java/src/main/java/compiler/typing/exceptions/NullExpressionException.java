package compiler.typing.exceptions;


public class NullExpressionException extends RuntimeException {
	
    public NullExpressionException() {
        super("No Expression found after parsing");
    }
}
