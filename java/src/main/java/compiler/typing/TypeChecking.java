package compiler.typing;


import compiler.common.Exp;
import compiler.common.Id;
import compiler.common.Type;
import compiler.common.Type.*;
import compiler.common.expression.LE;
import compiler.common.expression.Int;
import compiler.common.expression.Sub;
import compiler.common.expression.Add;
import compiler.common.expression.App;
import compiler.common.expression.Bool;
import compiler.common.expression.Eq;
import compiler.common.expression.FAdd;
import compiler.common.expression.Let;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import compiler.common.expression.LetRec;
import compiler.common.expression.Neg;
import compiler.common.expression.Var;
import compiler.common.expression.Unit;
import compiler.common.expression.Not;
import compiler.common.expression.FDiv;
import compiler.common.expression.FMul;
import compiler.common.expression.FNeg;
import compiler.common.expression.FSub;
import compiler.common.expression.Float;
import compiler.common.expression.If;
import compiler.typing.exceptions.*;


public class TypeChecking {
    
     public HashMap<String, Type> env ; // mapping functions to their type
     public static TypeChecking instance =null; //Singileton pattern (having 1 instance of the class)
     public ArrayList<Equation> equationList; // generated equationList are added to this arrayList
    
     private TypeChecking() {
        env = new HashMap<>();
        equationList = new ArrayList<Equation>();

        this.env = new HashMap<String, Type>() ;
        this.equationList   = new ArrayList<Equation>() ;
      initializeEnvironment() ;
    }

    public static TypeChecking getInstance() {
        if (instance == null) {
            instance = new TypeChecking();
        }
        return instance;
    }
    
    private void initializeEnvironment() {
        Type returnType = new TUnit() ;
        // Defining the type for the print_int function
        TFun print_int = new TFun() ;
        ArrayList<Type> parameters = new ArrayList<Type>() ;
        parameters.add( new TInt() ) ;
        print_int.returnType = returnType ;
        print_int.params = parameters ;
        this.env.put("print_int", print_int) ;
        
        // Defining the type for the print_string function
        TFun print_string = new TFun();
        print_string.params = new ArrayList<>(Arrays.asList(new TString())); // print_string takes a string parameter
        ArrayList<Type> str_parameters = new ArrayList<Type>() ;
        str_parameters.add( new TString() ) ;
        print_string.returnType = returnType ;
        print_string.params = parameters ;
        env.put("print_string", print_string);

        /** Defining the type for the new line function**/
        TFun print_new_line = new TFun() ;
        Type returnTypePNL = new TUnit() ;
        ArrayList<Type> parametersPNL = new ArrayList<Type>() ;
        print_new_line.returnType = returnTypePNL ;
        print_new_line.params = parametersPNL ;
        this.env.put("print_new_line", print_new_line) ;

         // Defining the type for the exit function
         TFun exit = new TFun() ;
         Type returnTypeExit = new TUnit() ;
         ArrayList<Type> parametersExit = new ArrayList<Type>() ;
         exit.returnType = returnTypeExit ;
         print_new_line.params = parametersExit ;
         this.env.put("print_new_line", exit) ;
    }

    public void generateEquations(HashMap<String, Type> env , Exp exp, Type type ){
        
        /**Basic type **/
        if (exp instanceof Int) {
            // System.out.println("1");
           equationList.add (new Equation ( new TInt(), type)) ;
        }

        else if (exp instanceof Bool) {
            // System.out.println("9");
           equationList.add (new Equation ( new TBool(), type)) ;
        }

        else if (exp instanceof Float) {
           equationList.add(new Equation ( new TFloat(), type)) ;
        }

        else if (exp instanceof Unit) {
            // System.out.println("2");
            equationList.add(new Equation( new TUnit(), type));
        }

        /**Arithmetic operator **/
        else if (exp instanceof Add) {
            // System.out.println("ADD");
            equationList.add(new Equation(new TInt(), type ));
            generateEquations (env, ((Add)exp).e1, new TInt());
            generateEquations (env, ((Add)exp).e2, new TInt());
        }

        else if (exp instanceof Sub) {
           equationList.add(new Equation(new TInt(), type)) ;
           generateEquations (env, ((Sub)exp).e1, new TInt() );
           generateEquations (env, ((Sub)exp).e2, new TInt());
        }

        else if (exp instanceof Neg) {
           equationList.add(new Equation(new TInt(), type)) ;
           generateEquations (env, ((Neg)exp).e, new TInt());
        }

        else if (exp instanceof FAdd ) {
            // System.out.println("4");
            equationList.add(new Equation(new TFloat(), type)) ;
            generateEquations(env, ((FAdd)exp).e1, new TFloat()) ;
            generateEquations(env, ((FAdd)exp).e2, new TFloat()) ;
        }

         /** Floating-point operators */
        else if (exp instanceof FSub ) {
           equationList.add(new Equation(new TFloat(), type)) ;
           generateEquations(env, ((FSub)exp).e1, new TFloat()) ;
           generateEquations(env, ((FSub)exp).e2, new TFloat()) ;
        }

        else if (exp instanceof FMul ) {
           equationList.add(new Equation(new TFloat(), type)) ;
           generateEquations(env, ((FMul)exp).e1, new TFloat()) ;
           generateEquations(env, ((FMul)exp).e2, new TFloat()) ;
        }

        else if (exp instanceof FDiv ) {
           equationList.add(new Equation(new TFloat(), type)) ;
           generateEquations(env, ((FDiv)exp).e1, new TFloat()) ;
           generateEquations(env, ((FDiv)exp).e2, new TFloat()) ;
        }

        else if (exp instanceof FNeg ) {
           equationList.add(new Equation(new TFloat(), type)) ;
           generateEquations(env, ((FNeg)exp).e, new TFloat()) ;
        }

        /** Boolean operator **/
        else if (exp instanceof Eq ) {
            // System.out.println("5");
           equationList.add(new Equation(new TBool(), type)) ;
           Type operands = Type.gen() ;
           generateEquations(env, ((Eq)exp).e1, operands);
           generateEquations(env, ((Eq)exp).e2, operands) ;
        }

        /*Unary operator is made in two step, check the exp is of the right type, and check the whole expression is of type */
        else if (exp instanceof Not ) {
           equationList.add(new Equation(new TBool(), type)) ;
           generateEquations(env, ((Not)exp).e, new TBool()) ;
        }
   
        //    Assuming the function is designed strictly for integers but could be extended to other type
        else if (exp instanceof LE ) {
            // System.out.println("6");

           equationList.add(new Equation(new TBool(), type)) ;
           generateEquations(env, ((LE)exp).e1, new TInt()) ;
           generateEquations(env, ((LE)exp).e2, new TInt()) ;
        }

        /**ConditionStm  **/
         else if (exp instanceof If ) {
            // System.out.println("7");
            // In first type should be bool and other should have same type
            generateEquations (env,  ((If)exp).e1, new TBool()) ; 
            generateEquations ( env,  ((If)exp).e2 , type ) ;     
            generateEquations ( env,  ((If)exp).e3 , type ) ;
         }

        /***definition of Functions **/
        if (exp instanceof LetRec) {
            // System.out.println("Lec Rec");
            TFun typeFun = new TFun() ;
            env.put( ((LetRec)exp).fd.id.toString(), typeFun ) ;  // adding the function name (e.g adder) and function type (e.g TFun) in environment

             //Function environment creation
            HashMap<String, Type> fctEnv = copyMap(env) ;

            //Assigning each parameter a generics type
            ((LetRec)exp).fd.type = Type.gen();

            //Assigning  returntype of  typeFun
            typeFun.returnType = ((LetRec)exp).fd.type ;

            //Filling parameter arraylist with types of typeFun object
            typeFun.params = new ArrayList<Type>() ;
            for (Id idArg : ((LetRec)exp).fd.args) {
                //For now just assign a generic type
                fctEnv.put( idArg.toString() , Type.gen() )  ;
                typeFun.params.add( fctEnv.get(idArg.toString()) ) ;
            }

            generateEquations( fctEnv,  ((LetRec)exp).fd.e ,  ((LetRec)exp).fd.type ) ; //Check return type is the same as the parser
            //Checking the resting exp is well type (in old env)
            generateEquations(env,  ((LetRec)exp).e , type ) ; 
        }

        if (exp instanceof App) {
            try{
                Var temp = (Var) ((App)exp).e;
            }
            catch (Exception e){
                System.out.println("Unexpected expression found");
                System.exit(1);
            }
            String functionName = ((Var) ((App)exp).e).id.toString();
            //checking the previously declared function )
            if (env.get( functionName ) == null )   {
                throw new UndefinedFunctionException(functionName);
            }
            //checking if its a function
            if ( !  (env.get( functionName ) instanceof TFun))   {
                throw new NotAFunctionException(functionName);
            }

            //Checking that either the parameter is compatible
            if ( ((App)exp).es.size() != ((TFun) (env.get( functionName))).params.size() ) {
               
                int calling_param = ((App)exp).es.size() ;
                int ftn_param = ((TFun) (env.get( functionName))).params.size();
               throw new IncorrectParametersException(functionName, calling_param, ftn_param);
            }

            //checking parameter is well typed
            for ( int nbParam = 0 ; nbParam < (((App)exp).es.size()) ; nbParam++) {
                generateEquations( env, ((App)exp).es.get(nbParam), ((TFun) (env.get( functionName))).params.get(nbParam)) ;//cannot have var declaration in the parameters ? even if, i think it migth be ok // work bcz they are suposed to be called in the well order
            }
            //adding  in equ list (type, ftn return type as generated at definition type)
            this.equationList.add(new Equation( type, ((TFun) (env.get( functionName))).returnType  )) ; //t is the in part ?
        }

        /***Variables use***/
        if (exp instanceof Var) {
          if ( ! (env.containsKey( ((Var)exp).id.toString())) ) {
        	  throw new UndeclaredVariableException((Var)exp);
          } ;
          
          equationList.add (new Equation ( env.get( ((Var)exp).id.toString()) , type)) ; //1st parameter could NULL )
        }

        /** declaration of Variable**/
        if (exp instanceof Let) {
            // System.out.println("8");

            // Id id = ((Let)expr).id ; //Variable name
            Type type_let = ((Let)exp).t ; //Type generated by the parser
            HashMap<String, Type> newEnv = copyMap(env) ; //Update the env after execution of exp1 //env != ast
            
            generateEquations (env, ( (Let) exp).e1 , type_let) ;
            //Could add an assert to say that we cannot redeclare in the same scope with the same var (actually it work ex : let x = 1 in let x = 1 in x)            
            newEnv.put(((Let)exp).id.toString(), type_let) ;
            // System.out.println("RRRECC >>>"+ ((Let) expr).e2 );

            generateEquations (newEnv, ((Let) exp).e2 , type);

        }
    }
   
    private HashMap<String, Type> copyMap(HashMap<String, Type> to_copy ) {
        HashMap<String, Type> make_copy = new HashMap<String, Type>(); //new object
        // Copying the equ ref
        make_copy.putAll(to_copy); 
        return make_copy ;
    }

   /**
      * Solves a system of type equationList via unification algo.
      *
      * @return true if the equation is solvable and balanced, false otherwise.
   */
    public boolean unification() { //solving the equations in equationList

    HashMap<String, Type> envVAr = new HashMap <String, Type>() ;
        boolean type_balance = false  ;

        while ( ! type_balance ) {
            type_balance = true ; // default
            // Iterate through each type equation
            for ( Equation equ : equationList ) {
                //For (Type, Type) Case
               if (equ.t1 instanceof TVar && !(equ.t2 instanceof TVar)) {
                // Replace all occurrences of equ.t1 in the environment and equation list with equ.t2
                    replaceTypeVariable((TVar) equ.t1, equ.t2);
                } else if (!(equ.t1 instanceof TVar) && equ.t2 instanceof TVar) {
                    // Replace all occurrences of equ.t2 in the environment and equation list with equ.t1
                    replaceTypeVariable((TVar) equ.t2, equ.t1);
                }
                if ( equ.t1.getClass().getName() != "compiler.common.Type$TVar"  && equ.t2.getClass().getName() != "compiler.common.Type$TVar") {
                    // System.out.println("Case Type, Type");
                    //Return false if its not compatible type (int, float..), else, do nothing
                    if (equ.t1.getClass().getName().toString() != equ.t2.getClass().getName().toString() ) {
                        // System.out.println("Type NOT OKAY");
                        return false ;
                    }
                }

                //For (Var, Var) Case
                if( equ.t1.getClass().getName() == "compiler.common.Type$TVar"  && equ.t2.getClass().getName() == "compiler.common.Type$TVar") {
                    // System.out.println("Case Var, Var");
                    if (envVAr.containsKey(equ.t1.toString())) {
                       equ.t1 = envVAr.get(equ.t1.toString()) ;
                       type_balance = false ;

                   }
                   if (envVAr.containsKey(equ.t2.toString())) {
                       equ.t2 = envVAr.get(equ.t2.toString()) ;
                       type_balance = false ;
                   }
                }

                 //For (Type, Var) Case
                if ( equ.t1.getClass().getName() != "compiler.common.Type$TVar"  && equ.t2.getClass().getName() == "compiler.common.Type$TVar") {
                     // Checking if already type the Var
                     if ( ! envVAr.containsKey( equ.t2.toString() )) {
                        // System.out.println("case Type, Var");
                         envVAr.put(equ.t2.toString()  ,  equ.t1 ) ; //Copy by reference
                         equ.t2 =  equ.t1 ;
                         type_balance = false ;
                     } else {
                          equ.t2 = envVAr.get(equ.t2.toString());
                     }
                }

                //Case (Var, Type)
                if ( equ.t1.getClass().getName() == "compiler.common.Type$TVar"  && equ.t2.getClass().getName() != "compiler.common.Type$TVar") {
                    // Checking if already type the Var
                    // System.out.println("Case Var, Type");
                    if ( ! envVAr.containsKey( equ.t1.toString()) ) {
                        envVAr.put(equ.t1.toString()  ,  equ.t2 ) ; //Copy by reference
                        equ.t1 =  equ.t2 ;  //Cast the Tvar to the corresponding type
                        type_balance = false ;
                    } else {
                        equ.t1 = envVAr.get(equ.t1.toString()) ;
                    }
                } 
            }

        }
        return true ; // If its balanced type, and no error case found while solving eq.
    
    }
    
    // This function changes 0? -> Int to x -> Int to get final types of vairables to be used in closure
    private void replaceTypeVariable(TVar typeVar, Type concreteType) {
        // Replace in environment
        for (Map.Entry<String, Type> entry : env.entrySet()) {
            if (entry.getValue().equals(typeVar)) {
                entry.setValue(concreteType);
            }
        }
        // Replace in equation list
        for (Equation eq : equationList) {
            if (eq.t1.equals(typeVar)) {
                eq.t1 = concreteType;
            }
            if (eq.t2.equals(typeVar)) {
                eq.t2 = concreteType;
            }
        }
    }
 
    public void printFinalTypes() {
        System.out.println("Final Types:");
        // Print types of variables from the environment
        for (Map.Entry<String, Type> entry : env.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
    public void printingEquations() {
        System.out.println("Total Equations : "+ equationList.size());
        for (Equation equ : this.equationList) {
            System.out.println("(" + equ.t1.getClass().getName() + "  :  "+ equ.t2.getClass().getName() +")" ) ;
            System.out.println("InString : (" + equ.t1.toString() + "  :  "+ equ.t2.toString() +")" ) ;
        }
    }

}



