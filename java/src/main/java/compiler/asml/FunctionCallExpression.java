package compiler.asml;

import java.util.List;

public class FunctionCallExpression extends Expression {

	private String functionName;
	private List<Expression> arguments;

	public String getFunctionName() {
		return functionName;
	}

	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public List<Expression> getArguments() {
		return arguments;
	}

	public void setArguments(List<Expression> arguments) {
		this.arguments = arguments;
	}

}