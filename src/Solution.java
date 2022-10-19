

import java.util.ArrayList;
import java.util.BitSet;



public class Solution {
  
	public ArrayList <Integer> sref=new  ArrayList <Integer>();
	
	
	
	public Solution(ArrayList<Integer> sref) {
		
		this.sref = sref;
	}
    
	public Solution() {
	
	
	}
	
	

	//generer une solution de reference 
    public  ArrayList<Integer> random_solution(int num_variables){
        
        for (int i=0;i<num_variables;i++) {
     	   this.sref.add(Math.random() > 0.5 ? 1:-1);
         
        }
         return sref;
     }
	
	public ArrayList<Integer> getSref() {
		return sref;
	}
	public void setSref(ArrayList<Integer> sref) {
		this.sref = sref;
	}

    
    public void AffichageSolution() {
    	for (int i = 0; i <this.getSref().size(); i++) {
			System.out.println(this.getSref().get(i)+"\t");
		}
    }
}
