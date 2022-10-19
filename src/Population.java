


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Population {

    protected ArrayList<ArrayList<Integer>> pop;

    Population(ArrayList<ArrayList<Integer>> pop){this.pop=pop;}

    public static ArrayList<ArrayList<Integer>> generer_poputlation(int num_variables, int taille_max){
        ArrayList< ArrayList <Integer> > population=new ArrayList<>(taille_max);
        ArrayList<Integer> solution ;

        Random_solution rdm_sol =new Random_solution();
        for(int i=0; i<taille_max;i++) {

            do {
                solution = rdm_sol.random_solution(num_variables);
            }while (population.contains(solution));
            population.add(solution);

        }
            return population;
    }

    public void setPop(ArrayList<ArrayList<Integer>> pop) {
        this.pop = pop;
    }

    public ArrayList<ArrayList<Integer>> getPop() {
        return pop;
    }

}
