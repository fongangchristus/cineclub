/**
 * 
 */
package com.itgstore.cineclub.sample.utilitaire;

import java.util.HashMap;
import java.util.Map;

import com.itgstore.cineclub.service.util.EvaluateurFormule;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

/**
 * @author : p.djomga
 * @date : 22 nov. 2018
 *
 */
public class FormuleTest {
	
	public static void main(String[] args) {
		
		//String formulsi = "si(v>2,45,0)"; 
		
		//calculateIf();
		calculateEvaluateurFormule();
		//calulateSimple();
	}

	private static void calculateEvaluateurFormule() {
		//String formule = "(x>0) & (x<100)";
		String formule = "si( (x>0) & (x<100), y, si((x>101) & (x<200), 2y, 3y) )";
		
		//String formule = "si( (x>0) & (x<100), y, si((x>101) & (x<200), 2y, 3y) )";
		
		Map<String , Double> params = new HashMap<>();
		params.put("x", 250.0);
		params.put("y", 2.0);
		params.put("z", 5.0);
		
		EvaluateurFormule.Evaluate(formule, params);
		
	}
	
	private static void calculateIf() {
		
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
		
		Operator sup = new Operator(">", 2, true, Operator.PRECEDENCE_POWER + 1) {

		    @Override
		    public double apply(double... args) {
		    	if(args[0]>args[1]) return 1;
		    	else return 0;
		        
		    }
		};
		
		Operator inf = new Operator("<", 2, true, Operator.PRECEDENCE_POWER + 1) {

		    @Override
		    public double apply(double... args) {
		    	if(args[0]>args[1]) return 0;
		    	else return 0;
		        
		    }
		};
			
		
		
		
		String formul = "si(x>0.2,y,0)";
		Expression e = new ExpressionBuilder(formul)
				.function(si)
				.operator(sup)
		        .variables("x", "y")
		        .build()
		        .setVariable("x", 0.3)
		        .setVariable("y", 3.14);
		double result = e.evaluate();
		
		System.out.println("FormuleTest.main() formule = " + formul);
		System.out.println("FormuleTest.main() result = " + result);
	}
	
	private static void calulateSimple() {
		
		String formul = "3 * sin(y) - 2 / (x - 2)";
		Expression e = new ExpressionBuilder(formul)
		        .variables("x", "y")
		        .build()
		        .setVariable("x", 2.3)
		        .setVariable("y", 3.14);
		double result = e.evaluate();
		
		System.out.println("FormuleTest.main() formule = " + formul);
		System.out.println("FormuleTest.main() result = " + result);
		
	}
}
