package compiler.frontend;

import java.util.HashSet;
import java.util.Set;

import compiler.common.*;
import compiler.common.expression.*;
import compiler.common.expression.Float;

// Assuming Exp and its subclasses (like Unit, Bool, Int, etc.) are defined elsewhere
// Also assuming the Id class is defined elsewhere

public class VariableCollector {

    static Set<Id> collect(Exp exp) {
        Set<Id> set = new HashSet<>();

        if (exp instanceof Unit) {
            // Handle Unit
        } else if (exp instanceof Bool) {
            // Handle Bool
        } else if (exp instanceof Int) {
            // Handle Int
        } else if (exp instanceof Float) {
            // Handle Float

        } else if (exp instanceof Not) {
            Not notExp = (Not) exp;
            set.addAll(collect(((Not) notExp).e));

        } else if (exp instanceof Neg) {
            Neg negExp = (Neg) exp;
            set.addAll(collect(((Neg) negExp).e));

        } else if (exp instanceof Add) {
            Add addExp = (Add) exp;
            set.addAll(collect(((Add) addExp).e1));
            set.addAll(collect(((Add) addExp).e2));

        } else if (exp instanceof Sub) {
            Sub subExp = (Sub) exp;
            set.addAll(collect(((Sub) subExp).e1));
            set.addAll(collect(((Sub) subExp).e2));

        } else if (exp instanceof FNeg) {
            FNeg fNegExp = (FNeg) exp;
            set.addAll(collect(((FNeg) fNegExp).e));

        } else if (exp instanceof FAdd) {
            FAdd fAddExp = (FAdd) exp;
            set.addAll(collect(((FAdd) fAddExp).e1));
            set.addAll(collect(((FAdd) fAddExp).e2));

        } else if (exp instanceof FSub) {
            FSub fSubExp = (FSub) exp;
            set.addAll(collect(((FSub) fSubExp).e1));
            set.addAll(collect(((FSub) fSubExp).e2));

        } else if (exp instanceof FMul) {
            FMul fMulExp = (FMul) exp;
            set.addAll(collect(((FMul) fMulExp).e1));
            set.addAll(collect(((FMul) fMulExp).e2));

        } else if (exp instanceof FDiv) {
            FDiv fDivExp = (FDiv) exp;
            set.addAll(collect(((FDiv) fDivExp).e1));
            set.addAll(collect(((FDiv) fDivExp).e2));

        } else if (exp instanceof Eq) {
            Eq eqExp = (Eq) exp;
            set.addAll(collect(((Eq) eqExp).e1));
            set.addAll(collect(((Eq) eqExp).e2));

        } else if (exp instanceof LE) {
            LE leExp = (LE) exp;
            set.addAll(collect(((LE) leExp).e1));
            set.addAll(collect(((LE) leExp).e2));

        } else if (exp instanceof If) {
            If ifExp = (If) exp;
            set.addAll(collect(((If) ifExp).e1));
            set.addAll(collect(((If) ifExp).e2));
            set.addAll(collect(((If) ifExp).e3));

        } 
        else if (exp instanceof Var) {
            Var varExp = (Var) exp;
            if (!set.contains(((Var) varExp).id)) {
                set.add(((Var) varExp).id);
            }
        }
        else if (exp instanceof App){
            App appExp = (App) exp;
            // set.addAll(collect(((App) appExp).e));
            for (Exp arg : ((App) appExp).es) {
                set.addAll(collect(arg));
            }
        }
        else if (exp instanceof Tuple){
            Tuple tupleExp = (Tuple) exp;
            for (Exp arg: ((Tuple) tupleExp).es) {
                set.addAll(collect(arg));
            }
        }
        else if (exp instanceof Let) {
            // we add the variable to the set if it's not already there
            Let letExp = (Let) exp;
            if (!set.contains(((Let) letExp).id)) {
                set.add(((Let) letExp).id);
            }

            set.addAll(collect(((Let) letExp).e1));
            set.addAll(collect(((Let) letExp).e2));
        }
        else if (exp instanceof LetTuple) {
            LetTuple letTupleExp = (LetTuple) exp;
            for (Id id : ((LetTuple) letTupleExp).ids) {
                if (!set.contains(id))
                    set.add(id);
            }
            set.addAll(collect(((LetTuple) letTupleExp).e1));
            set.addAll(collect(((LetTuple) letTupleExp).e2));

        }
        else if (exp instanceof Array){
            Array arrayExp = (Array) exp;
            set.addAll(collect(((Array) arrayExp).e1));
            set.addAll(collect(((Array) arrayExp).e2));
        }
        else if (exp instanceof Get){
            Get getExp = (Get) exp;
            set.addAll(collect(((Get) getExp).e1));
            set.addAll(collect(((Get) getExp).e2));
        }
        else if (exp instanceof Put){
            Put putExp = (Put) exp;
            set.addAll(collect(((Put) putExp).e1));
            set.addAll(collect(((Put) putExp).e2));
            set.addAll(collect(((Put) putExp).e3));
        }
        else if (exp instanceof LetRec) {
            LetRec letRecExp = (LetRec) exp;
            
            // Add arguments of the function to the set, assuming they are variables
            for (Id arg : letRecExp.fd.args) {
                if (!set.contains(arg))
                    set.add(arg);
            }
        
            // Collect variables from the body of the function
            set.addAll(collect(letRecExp.fd.e));
            // Collect variables from where the function is used
            set.addAll(collect(letRecExp.e));
        
            // Optionally, remove the function identifier if it's mistakenly collected
            set.remove(letRecExp.fd.id);
        }

        return set;
    }
}
