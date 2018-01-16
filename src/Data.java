import java.util.ArrayList;
import java.util.Collections;

public class Data {
	
	static ArrayList<double[]> init;
	
	static ArrayList<Double> score;
	
	private double[] numbers;
    private int number;
	
	public Data(int n, double mx, double my) {
		init = new ArrayList<double[]>();
		
		double[] v = {0,0};
		
		for(int i = 0; i < n; i++) {
			v[0] = (Math.random() * 8) - 4;
			v[1] = (Math.random() * 8) - 4;
			
			init.add(v.clone());
		}
		
	}
	
	public static void BubbleSort()
	{
	     int j;
	     boolean flag = true;   // set flag to true to begin first pass
	     double[] temp;   //holding variable
	     double tempf;   //holding variable

	     while ( flag )
	     {
	            flag= false;    //set flag to false awaiting a possible swap
	            for( j=0;  j < init.size() -1;  j++ )
	            {
	                   if ( main.f(init.get(j)) > main.f(init.get(j + 1)))   // change to > for ascending sort
	                   {
	                        temp = init.get(j);                //swap elements
	                        init.set(j, init.get(j+1));
	                        init.set(j + 1, temp);
	                           
	                        flag = true;              //shows a swap occurred  
	                  } 
	            } 
	      } 
	} 
	
	public void print() {
		for(int i = 0; i < 100; i++) {
			System.out.println( "Agent " + (i + 1) + ": x: " + init.get(i)[0] + " y: " + init.get(i)[1] + " score: " + main.f(init.get(i)));
		}
	}
	public String toString(int it) {
		String s = "";
		for(int i = 0; i < 100; i++) {
			s += it + "," + init.get(i)[0] + "," + init.get(i)[1] + "," + main.f(init.get(i)) + System.getProperty("line.separator");
		}
		return s;
	}
}
