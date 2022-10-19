

import java.util.ArrayList;

public class TabouList {

	
	public ArrayList <Solution> taboulist=new  ArrayList <Solution>();
	
	
	public void ajouteAlaListeTabou(Solution solution) {
		
			this.gettaboulist().add(solution);
	
		
	}

	public ArrayList<Solution> gettaboulist() {
		return taboulist;
	}

	public void settaboulist(ArrayList<Solution> tL) {
		this.taboulist = tL;
	}

	public void affichageTaboutList() {
		for (int i = 0; i < taboulist.size(); i++) {
			System.out.println("element 0");
			 taboulist.get(i).AffichageSolution();
			 
		}
	}
	
	
}
