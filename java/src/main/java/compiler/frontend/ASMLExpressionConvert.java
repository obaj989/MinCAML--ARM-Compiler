package compiler.frontend;

import java.util.ArrayList;
import java.util.List;

import compiler.asml.BinaryOperation;
import compiler.asml.CallExpression;
import compiler.asml.Expression;
import compiler.asml.FunctionDefinition;
import compiler.asml.LetExpression;
import compiler.asml.MainFunction;
import compiler.common.Exp;
import compiler.common.ObjVisitor;
import compiler.common.expression.*;
import compiler.common.expression.Float;

public class ASMLExpressionConvert implements ObjVisitor<Expression> {
	MainFunction main;
	FunctionDefinition fun_def;
	boolean is_main = true;

	public ASMLExpressionConvert() {
		main = new compiler.asml.MainFunction();
		// add "let _ = " to the beginning of the main function
		String s = "let _ = ";
//		if(is_main) {
//			main.addExpression(new compiler.asml.VariableExpression(s));
//		}
		List<Expression> parameters = new ArrayList<Expression>();
		List<Expression> body = new ArrayList<Expression>();
		fun_def = new compiler.asml.FunctionDefinition("");
		
//		main.addExpression(new compiler.asml.LetExpression("_", new compiler.asml.ValueExpression(0), new compiler.asml.ValueExpression(0)));
    }

	public MainFunction getMainFunction() {
		return this.main;
	}

	public FunctionDefinition getFunctionDefinition() {
		return this.fun_def;
	}

	@Override
	public Expression visit(Unit e) {
		return null;
	}

	@Override
	public Expression visit(Bool e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Int e) {

		return new compiler.asml.ValueExpression(e.i);
	}

	@Override
	public Expression visit(Float e) {
		return null;
	}

	@Override
	public Expression visit(Not e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Neg e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Add e) {
		Expression left = e.e1.accept(this);
		Expression right = e.e2.accept(this);

		BinaryOperation binary_op = new compiler.asml.BinaryOperation("add", left, right);
		return binary_op;
	}

	@Override
	public Expression visit(Sub e) {
		Expression left = e.e1.accept(this);
		Expression right = e.e2.accept(this);

		BinaryOperation binary_op = new compiler.asml.BinaryOperation("sub", left, right);
		if (is_main) {
			main.addExpression(binary_op);
		} else {
			fun_def.addExpression(binary_op);
		}
		return binary_op;
	}

	@Override
	public Expression visit(FNeg e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(FAdd e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(FSub e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(FMul e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(FDiv e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Eq e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(LE e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(If e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Let e) {
		String var = e.id.toString();
		Expression assignment = e.e1.accept(this);
		Expression body = e.e2.accept(this);

		LetExpression let_exp = new compiler.asml.LetExpression(var, assignment, body);
		if (is_main) {
			main.addExpression(let_exp);
		} else {
			fun_def.addExpression(let_exp);
		}

		return let_exp;
	}

	@Override
	public Expression visit(Var e) {

		return new compiler.asml.VariableExpression(e.id.toString());
	}

	@Override
	public Expression visit(LetRec e) {

		is_main = false;

		String fun_name = e.fd.id.toString();
		List<Expression> parameters = new ArrayList<Expression>();
		List<Expression> body = new ArrayList<Expression>();
		for (compiler.common.Id id : e.fd.args) {
			parameters.add(new compiler.asml.VariableExpression(id.toString()));
		}

		body.add(e.fd.e.accept(this));
		fun_def = new compiler.asml.FunctionDefinition(fun_name);
		fun_def.setParameters(parameters);
		fun_def.setBody(body);
		// add function definition to main function
		main.addExpression(fun_def);

		//iterate through the e.e.accept(this) to add the function definition to the main function
		e.e.accept(this);

		is_main = true;

		return e.e.accept(this);
	}
		
//		//if fun_def is main function, return main function
//		if (fun_name.equals("main")) {
//			return fun_def;
//		}
//		
//		LetExpression let_exp = new compiler.asml.LetExpression(fun_name, fun_def, e.e.accept(this));
//		return e.e.accept(this);
//		
//		}

	@Override
	public Expression visit(App e) {
		Expression fun = e.e.accept(this);
		String fun_name = ((compiler.asml.VariableExpression) fun).getName();

		switch (fun_name) {
		case "print_int":
			fun_name = "_min_caml_print_int";
			break;
		default:
			break;
		}

		List<Expression> args = new ArrayList<Expression>();
		for (Exp exp : e.es) {
			args.add(exp.accept(this));
		}
		CallExpression call_exp = new compiler.asml.CallExpression(fun_name, args);
		if (is_main) {
			main.addExpression(call_exp);
		} else {
			fun_def.addExpression(call_exp);
		}
		return call_exp;
	}

	@Override
	public Expression visit(Tuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(LetTuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Array e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Get e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression visit(Put e) {
		// TODO Auto-generated method stub
		return null;
	}

}
