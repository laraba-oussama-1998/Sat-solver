

import java.util.ArrayList;

public class EspaceDeRecherche {

	
       
		
		
	    public EspaceDeRecherche() {
			
		}


		public  ArrayList<Solution> genererVosin(Solution s, int flip , int nombreAbeille){
			ArrayList <Solution> Espace=new  ArrayList <Solution>();
			
	        int taille=s.sref.size();
	        int posflip=0;
	        int i=0;	      
	        while(i < nombreAbeille) {
	    	   Solution s2=new Solution();
	    	  posflip=i;	        
	          s2.setSref( new ArrayList<Integer>(s.getSref()));
	            
	          while( posflip < taille ) {
					if((s.getSref().get(posflip).equals(-1))){
						  s2.getSref().set(posflip, 1);						  
					}
					else {
						s2.getSref().set(posflip, -1);
					}
						  
					posflip=posflip+ flip;
				}      
	            Espace.add(s2);
	            i++;			
	       }			
			return Espace;	
		}
	

}
