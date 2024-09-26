package compiler.asml;

import java.util.ArrayList;
import java.util.List;

import compiler.common.Exp;

public class FunctionDefinition extends Expression {
	private String functionName;
	private List<Expression> parameters;
	private List<Expression> body;

	public FunctionDefinition(String functionName) {
		this.functionName = functionName;
		this.setParameters(new ArrayList<>());
		this.setBody(new ArrayList<>());
	}

	@Override
	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public List<Expression> getParameters() {
		return parameters;
	}

	public void setParameters(List<Expression> parameters) {
		this.parameters = parameters;
	}

	public List<Expression> getBody() {
		return body;
	}

	public void setBody(List<Expression> body) {
		this.body = body;
	}
	
	public void addExpression(Expression expression) {
		body.add(expression);
	}
	
	public void addParameter(Expression expression) {
		parameters.add(expression);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("let " + getFunctionName() + " =\n");
		for (Expression expression : body) {
			sb.append("  ");
			sb.append(expression.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}