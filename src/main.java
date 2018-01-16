import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* Derived my solution from the Sample Code and Algorithm sections
 * https://en.wikipedia.org/wiki/Differential_evolution
 * 
 * Ackley's Function
 * https://github.com/KarloKnezevic/EDAF/blob/master/src/hr/fer/zemris/edaf/fitness/tfon/TestFunctionsForOptimizationNeeds.java
 * 
 * This program finds a solution to ackley's function using a hill climbing algorithm and a differential evolution algorithm.
 * 
 * In order to run this program, you must invoke the java compiler from the command line.
 * 1) go to the directory that holds "main.java" 
 * 2) enter 
 * 	"javac main.java"
 * 3) enter 
 * 	"java main"
 *	
 */


public class main {
	
	//starts the program
	public static void main(String[] args) throws IOException {
		
	    BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
	    
	     
	    
	
		System.out.println("Welcome to Ackley Solver");
		System.out.println("------------------------");
		System.out.println("  By: Michael Blackwell");
		System.out.println(" ");
		
		//String fileName = args[0];
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Hill Climbing application to Ackley's Function");
		System.out.println("----------------------------------------------");
		System.out.println(" ");
		System.out.println(" ");
		
		//HILL CLIMBING
		//start the clock
		long startTime = System.currentTimeMillis();
		
		
		double[] v = {1,1};
		double[] last;
		int count;
		int iterations;
		
		for(int i = 0; i < 100; i++) {
			iterations = 0;
			count = 0;
			//start with a random x/y [-4,4]
			v[0] = (Math.random() * 8) - 4;
			v[1] = (Math.random() * 8) - 4;
			while(count < 100){
				iterations++;
				writer.write(i + "," + iterations + "," + v[0] + "," + v[1] + "," + f(v) + System.getProperty("line.separator"));
				last = v.clone();
				v = climb(v);
				
				if(v[0] == last[0] && v[1] == last[1]) {
					count++;
				}
			}
			System.out.println("run " + (i + 1));
			System.out.println("iterations: " + iterations);
			System.out.println("Final x: " + v[0] + " y: " + v[1] + " f(x,y): " + f(v));
			//System.out.println(v[0] + "," + v[1] + "," + f(v));
			
		}
		writer.write(System.getProperty("line.separator"));
		writer.write(System.getProperty("line.separator"));
		System.out.println("time taken for all 100 runs of the Hill climbing algorithm (ms): " + (System.currentTimeMillis() - startTime));
		
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Differential Evolution application to Ackley's Function");
		System.out.println("-------------------------------------------------------");
		//DIFFERENTIAL EVOLUTION
		//start the clock
		startTime = System.currentTimeMillis();
		
		//boundry
		double mx = 4;
		double my = 4;
		Data data = new Data(100, mx, my);
		count = 0;
		double[] best;
		
		iterations = 0;
		
		double lastScore = 100;
		double bestScore = 100;
		
		double[] a;
		double[] b;
		double[] c;
		double[] x;
		
		int xindex;
	
		//evolution parameters
		double crossover = 0.5;
		double diffWeight = 1.0;
		int N = 2; //dimensions
		
		double[] newpos = {0,0};
		
		
		//data.BubbleSort();
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Data after " + iterations + " iterations");
		data.print();
		
		while(count < 100) {
			iterations++;
			if(iterations < 100) {
				
				writer.write(data.toString(iterations));
			}
			bestScore = f(data.init.get(0));
			for(int i = 0; i < 100; i++) {
				if(f(data.init.get(i)) < lastScore) {
					bestScore = f(data.init.get(i));
				}
				
			}
			
			
			//Save the three best candidates
			
			for(int j = 0; j < 100; j++) {
				xindex = (int)(Math.random() * 100);
				x = data.init.get(xindex);

				//pick three different random points from population that are not x
				do{
					a = data.init.get((int)(Math.random() * 100));
				}while(a==x);
				do{
					b = data.init.get((int)(Math.random() * 100));
				}while(b==x || b==a);
				do{
					c = data.init.get((int)(Math.random() * 100));
				}while(c==x || c==a || c==b); 
				
				
				
				//pick a random index
				int R = (int)(Math.random() * 1);
				
				newpos = x.clone();
				
				//Compute the agent's new position
				if(0 == R || Math.random() < crossover) {
					newpos[0] = a[0] + diffWeight * (b[0] - c[0]);
				}
				if(1 == R || Math.random() < crossover) {
					
					newpos[1] = a[1] + diffWeight * (b[1] - c[1]);
				}
				
				
				if(f(x) > f(newpos)) {
					
					data.init.set(xindex, newpos);
				}
			}
			
			
			
			//data.BubbleSort();
			//data.print();
			
			if(bestScore == lastScore) {
				count++;
			}
			
			lastScore = bestScore;
			
		}

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Data after " + iterations + " iterations");
		data.print();
		
		
		System.out.println("time taken for differential evolution algortithm to repeat the same best score 100 times (ms): " + (System.currentTimeMillis() - startTime));
		
		writer.close();
	}
	
	//Ackley's Function
	public static double f(double[] variable) {
		final int dim = variable.length;

		final double a = 20;
		final double b = 0.2;
		final double c = 2 * Math.PI;

		double sum1 = 0;
		double sum2 = 0;
		for (int i = 0; i < dim; i++) {
			sum1 += variable[i] * variable[i];
			sum2 += Math.cos(c * variable[i]);
		}

		sum1 = -b * Math.sqrt((1.0 / dim) * sum1);
		sum2 = (1.0 / dim) * sum2;

		return ((-a * Math.exp(sum1)) - Math.exp(sum2)) + a + Math.exp(1);
	}
	
	//implementaion of the climbing algorithm given in the assignment
	public static double[] climb(double[] v) {
		double[] ret = {0,0};
		//x
		ret[0] = (Math.random() - 0.5)*0.1 + v[0];
		
		//y
		ret[1] = (Math.random() - 0.5)*0.1 + v[1];
		
		//only move if its better than before
		if(f(v) > f(ret)) {
			return ret;
		}
		else {
			return v;
		}
		
	}
	
	public static void saveMaxXY() {
		
	}
}
