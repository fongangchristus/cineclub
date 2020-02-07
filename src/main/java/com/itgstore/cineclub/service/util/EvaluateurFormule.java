/**
 * 
 */
package com.itgstore.cineclub.service.util;

import java.util.HashMap;
import java.util.Map;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

/**
 * @author : p.djomga
 * @date : 22 nov. 2018
 * Classe permettant d'évaluer des formules : Mathématiques en y ajout des opérateurs et fonctions comme : si, <, >, &
 */
public class EvaluateurFormule {
	
	private static void printTrace(String msg) {
		//System.out.println(msg);		
	}
	
	public static double Evaluate(String formule, double x, double y) {
		
		Map<String , Double> params = new HashMap<>();
		params.put("x", x);
		params.put("y", y);
		
		return Evaluate(formule, params);		
	}
	
	/**
	 * cette methode retourne le montant de la commission
	 * @param formule
	 * @param montant
	 * @return
	 */
	public static double Evaluate(String formule, double montant) {
		
		Map<String , Double> params = new HashMap<>();
		params.put("montant", montant);
		params.put("pourcent", montant*0.01);
		return Evaluate(formule, params);
		
	}
	
	/**
	 * cette methode retourne le pourcentage de la commission
	 * @param formule
	 * @param montant
	 * @return
	 */
    public static double EvaluatePourcent(String formule, double montant) {
		
		Map<String , Double> params = new HashMap<>();
		params.put("montant", montant);
		params.put("pourcent", 1.0);
		return Evaluate(formule, params);
		
	}
	
	
	
	/**
	 * 
	 * @param formule
	 * @param params
	 * @return
	 */
	
	public static double Evaluate(String formule, Map<String, Double> params) {
		
		// Définition de la fonction Si
		Function si = new Function("si", 3) {
		    @Override
		    public double apply(double... args) {		    	
		    	// evaluer le premier caractere		    	
		    	// Si >=1 retourne l'evaluation du du deuxieme param
		    	// sinon on retourne le 3e param
		    	if(args[0] >=1) return args[1];
		    	else return args[2];		    	
		    }
		};
		
		// Définition de la fonction supérieur >
		Operator sup = new Operator(">", 2, true, Operator.PRECEDENCE_POWER + 1) {

		    @Override
		    public double apply(double... args) {
		    	printTrace("EvaluateurFormule.Evaluate(...).new Operator() {...}.apply()  > " + args);
		    	if(args[0]>args[1]) return 1;
		    	else return 0;
		        
		    }
		};
		
		// Définition de l'opérateur inférieur <
		Operator inf = new Operator("<", 2, true, Operator.PRECEDENCE_POWER + 1) {

		    @Override
		    public double apply(double... args) {
		    	printTrace("EvaluateurFormule.Evaluate(...).new Operator() {...}.apply()  < " + args);
		    	if(args[0]<args[1]) return 1;
		    	else return 0;
		        
		    }
		};
		
		
		// Définition de l'opérateur inférieur =
		   Operator equal = new Operator("=", 2, true, Operator.PRECEDENCE_POWER + 1) {

			  @Override
			  public double apply(double... args) {
				 printTrace("EvaluateurFormule.Evaluate(...).new Operator() {...}.apply()  = " + args);
				 if(args[0]==args[1]) return 1;
				    else return 0;      
				    }
				};
		
		
		// Définition de l'opérateur et & 
		Operator et = new Operator("&", 2, true, Operator.PRECEDENCE_POWER + 1) {
		    @Override
		    public double apply(double... args) {
		    	
		    	printTrace("EvaluateurFormule.Evaluate(...).new Operator() {...}.apply()  & " + args);
		    	if(args[0]>=1 && args[1]>= 1) return 1;
		    	else return 0;		        
		    }
		};
			
		// Evaluation de la formule	
		Expression e = new ExpressionBuilder(formule)
				.function(si)
				.operator(sup, inf, et, equal)		
				.variables(params.keySet())
		        .build()
		        .setVariables(params);
		
		double result = e.evaluate();
		
		System.out.println("FormuleTest.main() formule = " + formule);
		System.out.println("FormuleTest.main() params = " + params);
		System.out.println("FormuleTest.main() result = " + result);
		
		return result;
		
	}
	
	
	/*public static void main(String [] args) {
		String formule1 = " 100"; //Taux fixe granulaire
		String formule2 = "si( (montant>0) & (montant<100), pourcent, si((montant>101) & (montant<200), 2pourcent, 3pourcent))";
		System.out.println(Evaluate(formule1, 300));
	}*/
	

}
