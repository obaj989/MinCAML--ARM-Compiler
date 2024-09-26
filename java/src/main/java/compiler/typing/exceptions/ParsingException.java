package compiler.typing.exceptions;


public class ParsingException extends RuntimeException {
	
    public ParsingException(String filePath) {
        super("File " + filePath + " failed to parse");
    }
}
