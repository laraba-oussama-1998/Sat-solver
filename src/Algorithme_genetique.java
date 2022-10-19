


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Algorithme_genetique {



    protected Matrice_contingence m_c;
    protected Population population ;
    protected ArrayList<Fitness> fit;
    protected ArrayList<Integer> meilleure_solution;
    protected int score_meilleure_solution=0;
    protected int meilleure_generation=0; // c a d l iteration qui a la meilleure solution
    protected boolean sat=false;
    protected int number_iteration=0;
    protected String str="";


    public Algorithme_genetique(Matrice_contingence m_c){
        this.m_c=m_c;
        this.population=new Population(Population.generer_poputlation(m_c.getNum_variables(),1000)) ;

        
        while (!sat && number_iteration < 100) {
          
            this.fit =new ArrayList<>(this.population.getPop().size());

            for(int i=0;i<this.population.getPop().size();i++){this.fit.add(new Fitness(0,i));}

            for(int i=0;i<this.population.getPop().size();i++){
               
                this.fit.get(i).setScore(fitness(this.population.getPop().get(i),this.m_c));
    
            }

            this.population.setPop(this.tri_population(this.population,this.fit));
         
            this.population.setPop(selection(this.population));
          
            
            this.population.setPop(croissment(this.population));

           
            this.population.setPop(mutation(this.population));

            
            meilleure_soltion();
            
            number_iteration++;
            this.str+="Boucle "+number_iteration+" et le nombre de clauses satisfaite= "+this.score_meilleure_solution+"\n";

        }






    }


    //methode pour affiche la population
    public void affiche_population(Population population){
        System.out.println("affichage de la population : il y a "+this.getPopulation().getPop().size()+"");
        for(int i=0; i<population.getPop().size();i++) {

            for(int j=0; j<population.getPop().get(i).size();j++) {

             System.out.print(" "+population.getPop().get(i).get(j));
            }
            System.out.println("");
        }
    }




        // methode fitness pour calcule le score des solution de population

    public static int fitness(ArrayList <Integer> solution,Matrice_contingence m_c){
        int result=0;
        int [][] mc=new int [m_c.getNum_clauses()][m_c.getNum_variables()];
        for(int i=0;i<mc.length;i++){
            for(int j=0;j<mc[i].length;j++){
                mc[i][j]=m_c.getMatrice()[i][j];
            }
        }
        int [] res=new int[m_c.getNum_clauses()];
    
        for(int i=0;i<res.length;i++)res[i]=0;

     

 
              boolean signal;
                for(int i=0;i<m_c.getNum_clauses();i++){
                    signal=false;int j=0;
                    while(j<solution.size() && !signal){
                    if(mc[i][j]==solution.get(j)){signal=true;res[i]=1;}
                        j++;
                }
        }
     
        for(int i=0;i<m_c.getNum_clauses();i++){
            if(res[i]==1)result++;
        }
  

        return result;
    }





    public ArrayList<ArrayList<Integer>> tri_population(Population population, ArrayList<Fitness> fit){
        Fitness temp;
        ArrayList <ArrayList <Integer> > pop_tri = new ArrayList<>(population.getPop().size());
        for (int i=0;i<population.getPop().size();i++){

            int j=i,max_index=i;
            while(j<population.getPop().size()){
                if(this.fit.get(max_index).getScore()<this.fit.get(j).getScore())max_index=j;
                j++;
            }
            if(max_index!=i){temp=new Fitness(this.fit.get(i).getScore(),this.fit.get(i).getPosition());this.fit.get(i).setScore(this.fit.get(max_index).getScore())
            ;this.fit.get(i).setPosition(this.fit.get(max_index).getPosition());this.fit.get(max_index).setScore(temp.getScore())
            ;this.fit.get(max_index).setPosition(temp.getPosition());}

        }
        for(int i=0;i<population.getPop().size();i++){
            pop_tri.add(population.getPop().get(this.fit.get(i).getPosition()));
           // System.out.println(this.fit.get(i).getScore());
            if(this.fit.get(i).getScore()==this.m_c.getNum_clauses()) { //System.out.println(" solution "+(i+1)+" est correcte ");
            this.sat=true;

            }
            else;  //System.out.println(" ** solution "+(i+1)+" est non pas correcte est le score = "+this.fit.get(i).getScore()+" position : "+(this.fit.get(i).getPosition()+1)+" ** ");
        }
        population.setPop(pop_tri);

        return population.getPop();
    }


   // methode de selection la meilleure generation

    public static  ArrayList<ArrayList<Integer>> selection(Population population){

        ArrayList<ArrayList<Integer>> pop_apres_selection=new ArrayList<>(population.getPop().size()-1);
        for (int i=0;i<((population.getPop().size()+1)/2);i++){
            pop_apres_selection.add(population.getPop().get(i));
        }

        return pop_apres_selection;
    }





    public  ArrayList<ArrayList<Integer>> croissment(Population population){

        /*
        // int size_pop pour la taille de population intiale car il est toujour aggrandir avec
        // la methode croissment_pere_mere
        int size_pop=population.getPop().size();
        if(size_pop%2==0) {
            // le case ou le la taille de population est pair (mere et pere)
            for (int i = 0; i < size_pop; i = i + 2) {
                population.setPop(croissment_pere_mere(population.getPop(),((i/2)+1), population.getPop().get(i)
                        , population.getPop().get(i + 1),size_pop));

            }
        }else {
            // le case ou le la taille de population est impair
            // (exist un pere sans couplage donc on va genere autre solution aleatoir)
            for (int i = 0; i < size_pop-1; i = i + 2) {
                population.setPop(croissment_pere_mere(population.getPop(),((i/2)+1), population.getPop().get(i)
                        , population.getPop().get(i + 1),size_pop));
            }
            ArrayList<Integer> solution;
            do{
                solution=Random_solution.random_solution(this.m_c.getNum_variables());
            }while (population.getPop().contains(solution));
            population.getPop().add(solution);
        }

         */

        int size_pop=population.getPop().size();
        int position_mere,position_pere;
        ArrayList<Integer> fils;

        for (int i = 0; i < size_pop; i++) {

           // do{
                position_mere=Math.random()<0.70 ? new Random().nextInt(population.getPop().size()/15)
                        : population.getPop().size()/15+new Random().nextInt(population.getPop().size()
                        -population.getPop().size()/15);
                position_pere=Math.random()<0.70 ? new Random().nextInt(population.getPop().size()/15)
                        : population.getPop().size()/15+new Random().nextInt(population.getPop().size()
                        -population.getPop().size()/15);
                fils=croissment_pere_mere(population.getPop(),position_mere,position_pere);

           // }while(population.getPop().contains(fils));

            population.getPop().add(fils);
        }

        return population.getPop();
    }

    public static ArrayList <Integer>  croissment_pere_mere(ArrayList <ArrayList <Integer> > pop,int position_mere,
                                                            int position_pere){


        ArrayList <Integer> fils1;
        fils1= new ArrayList <>(pop.get(0).size());
        for (int i = 0; i < pop.get(position_mere).size()/2; i++) {
            fils1.add(pop.get(position_mere).get(i));
        }
        for (int i = pop.get(position_mere).size()/2; i < pop.get(position_mere).size(); i++) {
            fils1.add(pop.get(position_pere).get(i));
        }
        return fils1;
/*
        //System.out.println(" on est dans la methode croissment_pere_mere ");
        ArrayList <Integer> fils1;
        ArrayList <Integer> fils2;
        boolean steril=false,pere_couple=false,mere_couple=false;
        int num_essaye_couplage=0;
        //  pere.set(0,0);pere.set(1,0);pere.set(2,0);pere.set(3,0);pere.set(4,0);
        // remplissage fils 1 par croissement uniforme
        do {
            num_essaye_couplage++;
            fils1= new ArrayList <>(pere.size());
            for (int i = 0; i < pere.size()/2; i++) {
                fils1.add(pere.get(i));
            }
            for (int i = mere.size()/2; i < mere.size(); i++) {
                fils1.add(mere.get(i));
            }
            // le case ou un des parents est sterile (num_essaye_couplage)

       }while(pop.contains(fils1) && num_essaye_couplage<2000000);
        if(num_essaye_couplage<2000000) {
            // case couplage passe bien
            pop.add(fils1);
            // remplissage fils 2 par croissement uniforme
            do {

                fils2 = new ArrayList<>(pere.size());
                for (int i = 0; i < pere.size()/2; i++) {
                    fils1.add(pere.get(i));
                }
                for (int i = mere.size()/2; i < mere.size(); i++) {
                    fils1.add(mere.get(i));
                }

            } while (pop.contains(fils2));
            pop.add(fils2);
        }else{
            // case couplage est non pas passe bien
            // essaye couplage avec les autres pere de la population
            int i=0,essaye_couplage_pere=0,essaye_couplage_mere=0;
            while(i<taille_population_parent && (pere_couple && mere_couple)){
                essaye_couplage_pere=0;
                essaye_couplage_mere=0;
                System.out.println(i +"  ************ taille_population_parent "+taille_population_parent);
                if(!pop.get(i).equals(pere) && !pop.get(i).equals(mere)){
                    //couplage le pere
                    if(!pere_couple) {
                        do {
                            essaye_couplage_pere++;
                            fils1 = new ArrayList<>(pere.size());
                            for (int j = 0; j < pere.size(); j++) {
                                if (Math.random() > 0.5) fils1.add(pop.get(i).get(j));
                                else fils1.add(pere.get(j));
                            }
                        } while (pop.contains(fils1) && essaye_couplage_pere<2000000);
                        if (!pop.contains(fils1)){pop.add(fils1);pere_couple=true;}
                    }

                    //couplage le mere
                    if(!mere_couple) {
                        do {
                            essaye_couplage_mere++;
                            fils2 = new ArrayList<>(pere.size());
                            for (int j = 0; j < pere.size(); j++) {
                                if (Math.random() > 0.5) fils2.add(pop.get(i).get(j));
                                else fils2.add(mere.get(j));
                            }
                        } while (pop.contains(fils2)  && essaye_couplage_mere<2000000);
                        if (!pop.contains(fils2)){pop.add(fils2);mere_couple=true;}
                    }

                }
                i++;
            }

        }
        return pop;

 */

    }




    public static ArrayList <ArrayList <Integer> > mutation(Population population){
        int gene_qui_mute;
        for (int i=0;i<population.getPop().size();i++){

            gene_qui_mute= new Random().nextInt(population.getPop().get(0).size());
            if(population.getPop().get(i).get(gene_qui_mute)==-1)population.getPop().get(i).set(gene_qui_mute,1);
            else population.getPop().get(i).set(gene_qui_mute,-1);
        }
        return population.getPop();
    }


    public void   meilleure_soltion(){
        // System.out.println(this.population.getPop().size());
       // for(int i=0;i<this.population.getPop().size();i++){this.fit.get(i).setPosition(i);
       // System.out.println(i);
          //  }
        this.fit.clear();

        for(int i=0;i<this.population.getPop().size();i++){this.fit.add(new Fitness(0,i));}
        for(int i=0;i<this.population.getPop().size();i++){
          //  System.out.println(" solution numero "+i);
            this.fit.get(i).setScore(fitness(this.population.getPop().get(i),this.m_c));
           // System.out.println("******************* ******************** *************** ***********************");
        }
        this.population.setPop(this.tri_population(this.population,this.fit));
        if(this.fit.get(0).getScore()>this.score_meilleure_solution){
      this.meilleure_solution=new ArrayList<>(this.population.getPop().get(0));
        this.score_meilleure_solution=this.fit.get(0).getScore();
        this.meilleure_generation=this.number_iteration;
        }
    }

    public ArrayList<Integer> getMeilleure_solution() {
        return meilleure_solution;
    }

    public ArrayList<Fitness> getFit() {
        return fit;
    }

    public boolean isSat() {
        return sat;
    }

    public int getNumber_iteration() {
        return number_iteration;
    }

    public Matrice_contingence getM_c() {
        return m_c;
    }

    public Population getPopulation() {
        return population;
    }

    public int getMeilleure_generation() {
        return meilleure_generation;
    }

    public int getScore_meilleure_solution() {
        return score_meilleure_solution;
    }

    //setters

    public void setMeilleure_generation(int meilleure_generation) {
        this.meilleure_generation = meilleure_generation;
    }

    public void setMeilleure_solution(ArrayList<Integer> meilleure_solution) {
        this.meilleure_solution = meilleure_solution;
    }

    public void setScore_meilleure_solution(int score_meilleure_solution) {
        this.score_meilleure_solution = score_meilleure_solution;
    }

    public void setFit(ArrayList<Fitness> fit) {
        this.fit = fit;
    }

    public void setM_c(Matrice_contingence m_c) {
        this.m_c = m_c;
    }

    public void setNumber_iteration(int number_iteration) {
        this.number_iteration = number_iteration;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }
}
  /*
        System.out.println(pop.size());
        // fitness de la population selectione test
        for(int i=0;i<pop.size();i++){

            if(fit.get(i).getScore()==11)  System.out.println(" solution "+(i+1)+" est correcte ");
            else  System.out.println(" ** solution "+(i+1)+" est non pas correcte est le score = "+fit.get(i).getScore()+" ** ");
        }
*/