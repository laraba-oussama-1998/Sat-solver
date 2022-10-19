


import java.util.ArrayList;
import java.lang.*;
import java.util.LinkedList;

public class Random_solution {

    static ArrayList <Integer> solution ;
    public static ArrayList<Integer> random_solution(int num_variables){
        solution=new ArrayList<Integer>(num_variables);
       for (int i=0;i<num_variables;i++) {
           solution.add(Math.random() > 0.5 ? 1:-1);
       }
        return solution;
    }

}

