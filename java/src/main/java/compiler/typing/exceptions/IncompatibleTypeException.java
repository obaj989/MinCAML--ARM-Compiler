package compiler.typing.exceptions;


public class IncompatibleTypeException extends RuntimeException {
	
    public IncompatibleTypeException() {
        super("\u001B[31mTyping is not properly defined in the program !\u001B[0m");
    }
}
