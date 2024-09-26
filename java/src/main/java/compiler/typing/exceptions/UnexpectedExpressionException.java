package compiler.typing.exceptions;

import compiler.common.Exp;

public class UnexpectedExpressionException extends RuntimeException {
	
    public UnexpectedExpressionException(Exp functionExp) {
        super("\u001B[31m Unexpected expression type in App: " + functionExp.getClass().getName()+"!\u001B[0m");
    }
}
