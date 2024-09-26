//package compiler.frontend;
//
//import java.util.*;
//
//import compiler.common.Id;
//import compiler.common.Type;
//import compiler.common.ObjVisitor;
//import compiler.common.expression.*;
//import compiler.common.expression.Float;
//import compiler.frontend.Closure.Tuple_var;
//
//// we include the arguments of a function (let rec)
//public class FreeVariables implements ObjVisitor<List<Tuple_var<Id, Type>>> {
//
//    private List<Tuple_var<Id, Type>> currentScope = new ArrayList<>();
//
//	@Override
//    public List<Tuple_var<Id, Type>> visit(Unit e) {
//        return new ArrayList<>();
//    }
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(Bool e) {
//		return new ArrayList<>();
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(Int e) {
//		return new ArrayList<>();
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(Float e) {
//		return new ArrayList<>();
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(Not e) {
//		return e.e.accept(this);
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(Neg e) {
//		return e.e.accept(this);
//	}
//
//	@Override
//	public List<Closure.Tuple_var<Id, Type>> visit(Add e) {
//		
//		List<Closure.Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(Sub e) {
//
//		List<Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(FNeg e) {
//
//		return e.e.accept(this);
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(FAdd e) {
//
//		List<Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(FSub e) {
//
//		List<Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(FMul e) {
//
//		List<Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(FDiv e) {
//
//		return e.e1.accept(this);
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(Eq e) {
//
//		List<Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(LE e) {
//
//		List<Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Tuple_var<Id, Type>> visit(If e) {
//
//		List<Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		Tuple.addAll(e.e3.accept(this)); // Collect variables from the third subexpression
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Closure.Tuple_var<Id, Type>> visit(Let e) {
//
//		List<Closure.Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		Tuple.addAll(e.e1.accept(this)); // Collect variables from the first subexpression
//		currentScope.add(e.id); // Add variable to scope
//		Tuple.addAll(e.e2.accept(this)); // Collect variables from the second subexpression
//		currentScope.remove(e.id); // Remove variable from scope
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Closure.Tuple_var<Id, Type>> visit(Var e) {
//
//		List<Closure.Tuple_var<Id, Type>> Tuple = new ArrayList<>();
//		if (!currentScope.contains(e.id)) {
//			Tuple.add(new Closure.Tuple_var<Id, Type>(e.id, e.type));
//		}
//		return Tuple;
//
//	}
//
//	@Override
//	public List<Closure.Tuple_var<Id, Type>> visit(LetRec e) {
//		// Collect variables from the function body
//		List<Closure.Tuple_var<Id, Type>> bodyVars = e.fd.getBody().accept(this);
//
//		// Collect variables from the expression
//		List<Closure.Tuple_var<Id, Type>> eVars = e.e.accept(this);
//
//		// Combine variables from the body and expression
//		bodyVars.addAll(eVars);
//
//		// Create a list of function argument variables
//		List<Id> functionArgs = new ArrayList<>();
//		for (Id arg : e.fd.args) {
//			functionArgs.add(arg);
//		}
//
//		// Remove function arguments from the body scope if they appear in free variables
//		bodyVars.removeIf(var -> functionArgs.contains(var));
//
//		return bodyVars;
//	}
//
//
//	@Override
//	public Tuple<Id, Type> visit(App e) {
//
//		// don't return anything
//		return new Tuple<Id, Type>();
//	}
//
//	@Override
//	public Tuple<Id, Type> visit(Tuple e) {
//
//		// don't return anything
//		return new Tuple<Id, Type>();
//	}
//
//	@Override
//	public Tuple<Id, Type> visit(LetTuple e) {
//
//		// don't return anything
//		return new Tuple<Id, Type>();
//	}
//
//	@Override
//	public Tuple<Id, Type> visit(Array e) {
//
//		// don't return anything
//		return new Tuple<Id, Type>();
//	}
//
//	@Override
//	public Tuple<Id, Type> visit(Get e) {
//
//		// don't return anything
//		return new Tuple<Id, Type>();
//	}
//
//	@Override
//	public Tuple<Id, Type> visit(Put e) {
//
//		// don't return anything
//		return new Tuple<Id, Type>();
//	}
//
//	public Tuple<Id, Type> visit(ClosureFunDef closureFunDef) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
