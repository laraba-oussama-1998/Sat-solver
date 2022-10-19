import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {




        @FXML
        private ChoiceBox<String> Benchmark;

        @FXML
        private ChoiceBox<String> Instance;

        @FXML
        private ChoiceBox<String> Methode;

        @FXML
        private Button Start;

        @FXML
        private TextArea Dataset;

        @FXML
        private TextArea Recherche;

        @FXML
        private TextArea Resultat;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Dataset.setEditable(false);
        this.Recherche.setEditable(false);
        this.Resultat.setEditable(false);
        File repertoire = new File("Benchmark");
        File[] files=repertoire.listFiles();

        Methode.getItems().add("Genetique");
        Methode.getItems().add("PSO");
        Methode.getItems().add("BSO");
        Methode.setValue("Genetique");
        for(int i = 0; i < files.length; i++){
            Benchmark.getItems().add(files[i].getName());
        }

        Benchmark.getSelectionModel().select(1);


        File repertoire_instance = new File("Benchmark/"+Benchmark.getValue());
        File[] files_instance=repertoire_instance.listFiles();

        for(int i = 0; i < files_instance.length; i++){

            Instance.getItems().add(files_instance[i].getName());
        }

        Instance.getSelectionModel().selectFirst();

        Benchmark.setOnAction(benchmark_selected -> {
            try {
                benchmark_changed();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Instance.setOnAction(benchmark_selected -> {
            try {
                instance_changed();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Matrice_contingence m_c = null;
        try {
            m_c = generate_matrice("Benchmark/"+Benchmark.getValue()+"/"+Instance.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.Dataset.appendText(m_c.affichage());


    }

        @FXML
        void handleclicks(ActionEvent event) throws IOException {


            if (event.getSource() == Start ) {
                boolean choisie=false;

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText(Methode.getValue()+" start processing svp attender les resultat!!!");
                alert.showAndWait();

                do{

                    if(Methode.getValue()=="Genetique"){

                        Genetique("Benchmark/"+Benchmark.getValue()+"/"+Instance.getValue());
                        System.out.println("\n\nFin Algorithme génétique");
                        choisie=true;
                    }
                    else if(Methode.getValue()=="PSO"){
                        ParticulSwarmOptimisation("Benchmark/"+Benchmark.getValue()+"/"+Instance.getValue());
                        System.out.println("\n\nFin PSO");
                        choisie=true;

                    }
                    else if(Methode.getValue()=="BSO"){
                        BeeSwarmOptimisation("Benchmark/"+Benchmark.getValue()+"/"+Instance.getValue());
                        System.out.println("\n\nFin BSO");
                        choisie=true;
                    }



                }while(choisie ==false );

            }

        }


public void benchmark_changed() throws IOException {

    File repertoire_instance = new File("Benchmark/"+Benchmark.getValue());
    File[] files_instance=repertoire_instance.listFiles();
    Instance.getItems().clear();
    for(int i = 0; i < files_instance.length; i++){
        Instance.getItems().add(files_instance[i].getName());
     System.out.println(i);
    }
    Instance.getSelectionModel().selectFirst();
    Matrice_contingence m_c = generate_matrice("Benchmark/"+Benchmark.getValue()+"/"+Instance.getValue());
    this.Dataset.clear();
    this.Dataset.appendText(m_c.affichage());
}



    public void instance_changed() throws IOException {

       Matrice_contingence m_c = generate_matrice("Benchmark/"+Benchmark.getValue()+"/"+Instance.getValue());
       this.Dataset.clear();

       this.Dataset.appendText(m_c.affichage());
    }

















    public void Genetique(String path) throws IOException {

        long Tempsdeb, TempsFin;
        double seconde;
        Tempsdeb=System.currentTimeMillis();
        this.Resultat.clear();
        this.Recherche.clear();

        System.out.println("Implementation des algorithmes génétiques");

        Matrice_contingence m_c=generate_matrice(path);

       // this.Dataset.appendText(m_c.affichage());
        Algorithme_genetique algorithme_genetique=new Algorithme_genetique(m_c);
        this.Recherche.appendText(algorithme_genetique.str);

        String res = "";
        if(algorithme_genetique.isSat()){
            res+="Solution trouve \n";


            algorithme_genetique.getPopulation().setPop(algorithme_genetique.tri_population(
                    algorithme_genetique.getPopulation(),algorithme_genetique.getFit()));

            for (int i = 0; i < algorithme_genetique.getPopulation().getPop().get(0).size(); i++) {
                if(algorithme_genetique.getPopulation().getPop().get(0).get(i).equals(-1)){
                    algorithme_genetique.getPopulation().getPop().get(0).set(i, 0);
                }
            }



            System.out.println(" la solution est "+algorithme_genetique.getPopulation().getPop().get(0)+"");
            res+="la solution est Dans la boucle ="+(algorithme_genetique.getMeilleure_generation()+1+" ");

        }else {
            res+="Solution non trouve \n";



            for (int i = 0; i  <algorithme_genetique.getMeilleure_solution().size(); i++) {
                if(algorithme_genetique.getMeilleure_solution().get(i).equals(-1)){
                    algorithme_genetique.getMeilleure_solution().set(i, 0);
                }
            }


            res+="la meilleure solution dans la boucle = " ;



            for(int j=0; j<algorithme_genetique.getMeilleure_solution().size();j++) {

                System.out.print(" "+algorithme_genetique.getMeilleure_solution().get(j));
            }


            res+=(algorithme_genetique.getMeilleure_generation()+1)+
                            " cette solution a pu satisfaire "+algorithme_genetique.getScore_meilleure_solution()+"\n"
                    ;
        }

        res+="Taux de satisfiablite: "+(float)(algorithme_genetique.getScore_meilleure_solution() * 100)/m_c.getNum_clauses()+"%\n";

        TempsFin=System.currentTimeMillis();
        seconde=(TempsFin- Tempsdeb)/1000F ;
        res+="et le temps d'execution = "+seconde+" seconde";





      this.Resultat.appendText(res);






    }





    public void ParticulSwarmOptimisation(String path) throws IOException{

        long Tempsdeb, TempsFin;
        double seconde;
        Tempsdeb=System.currentTimeMillis();
        int pos=0;
        int Bsol=0;
        int a=0;
        this.Recherche.clear();
        this.Resultat.clear();
        System.out.println("Implementation PSO");


        Matrice_contingence m_c=generate_matrice(path);
        //this.Recherche.appendText(m_c.affichage());
        Pso pso=new Pso(m_c);
        String rech ="";
        for(int i=0;i<100;i++){
            pso.position_movement();

            rech+="boucle "+(i+1)+" nombre de clauses satisfaites ="+pso.getGbestfitness()+"\n";

            if(pso.getGbestfitness()==m_c.getNum_clauses()){
                pos=(i+1);
                break;

            }

            if(pso.getGbestfitness()>Bsol){
                Bsol=pso.getGbestfitness();
                a=i;
            }



        }
        this.Recherche.appendText(rech
        );
        for (int i = 0; i <pso.Gbest.size(); i++) {
            if(pso.Gbest.get(i).equals(-1)){
                pso.Gbest.set(i, 0);
            }
        }

        String res="";


        if(pso.getGbestfitness()==m_c.getNum_clauses()){
            res+="une bonne solution a ete trouve dans la boucle "+pos;
            System.out.println(pso.Gbest);
        }
        else{
            res+="une solution qui satisfait "+pso.getGbestfitness()+" a ete trouve dans la boucle "+(a+1)+"\n";

            System.out.println(pso.Gbest);
        }


        res+="Taux de satisfiablite: "+(float)(pso.getGbestfitness() * 100)/m_c.getNum_clauses()+"%\n";

        TempsFin=System.currentTimeMillis();
        seconde=(TempsFin- Tempsdeb)/1000F ;
        res+="Le temps d'execution = "+seconde+" seconde";

        this.Resultat.appendText(res);












    }











    public void BeeSwarmOptimisation(String path) throws IOException{

        System.out.println("Implemention de l'algorithme BSO");

        long Tempsdeb, TempsFin;
        double seconde;

        int max=0;
        int a1=0;
        int b=0;
        int ite=0;

        int nAbeille=15;
        int flip;


        int pos;
        this.Resultat.clear();
        this.Recherche.clear();
        Tempsdeb=System.currentTimeMillis();


        Matrice_contingence m_c=generate_matrice(path);
        Solution s=new Solution();
        s.random_solution(m_c.getNum_variables());

        EspaceDeRecherche E=new EspaceDeRecherche();
        TabouList TL=new  TabouList();
        ArrayList<Integer> meilleurPosition = new   ArrayList <Integer>();
        ArrayList <Solution> meilleurSolution = new   ArrayList <Solution>();
        Random random = new Random();
        String rech="";

        while(ite<100) {


            flip = random.nextInt(30) + 1 ;
            System.out.println("le flip ="+flip);
            TL.ajouteAlaListeTabou(s);
            ArrayList <Solution> EspaceR = new   ArrayList <Solution>();

            Solution meilleurSolutionInt = new  Solution();

            EspaceR= E.genererVosin(s, flip, nAbeille);
            for (Solution solution : EspaceR) {
                if(! TL.taboulist.contains(solution)){
                    TL.ajouteAlaListeTabou(solution);
                }
                else{
                    EspaceR.remove(solution);
                }

            }

            for (Solution solution : EspaceR)  {
                ArrayList <Solution> Voisin = new   ArrayList <Solution>();
                Voisin=E.genererVosin(solution, flip, 50);
                for (Solution solu : Voisin)  {
                    if(! TL.taboulist.contains(solu)){
                        TL.ajouteAlaListeTabou(solu);
                    }
                    else{
                        Voisin.remove(solu);
                    }
                }



                pos=EvaluerMeilleurSolutionVoisins(Voisin, m_c);  //la positon du meilleur localement
                meilleurPosition.add(fitness(Voisin.get(pos).getSref() ,m_c)   );  //le score du meilleur localemnet
                meilleurSolution.add(Voisin.get(pos)); //la meilleur solution localement

                Voisin.clear();
            }

            meilleurSolutionInt=meilleurSolution.get(positionMAX(meilleurPosition));     //la meilleur des meilleur M
            max= Collections.max(meilleurPosition);                                       //le score de M


            meilleurPosition.clear();
            meilleurSolution.clear();

            ite++;
            rech+="boucle "+ite+" peut satisfaire "+fitness(meilleurSolutionInt.getSref() ,m_c)+"\n";
            if(max== m_c.getNum_clauses()) {
                break;
            }
            else {
                if(max > a1) {
                    a1=max;
                    b=ite;

                }
            }

            s.setSref(new ArrayList<Integer>(meilleurSolutionInt.getSref()));



        }

        this.Recherche.appendText(rech);


         String res="";
        if(max==m_c.getNum_clauses()) {
            res+="l'algorithme BSO a pu satisfaire  "+max+" clauses dans la boucle "+ite+"\n";
        }
        else {

            res+="l'algorithme BSO a pu satisfaire  "+max+" clauses dans la boucle "+b+"\n";
        }

        TempsFin=System.currentTimeMillis();
        seconde=(TempsFin- Tempsdeb)/1000F ;
        res+="Taux de satisfiablite: "+(float)(max * 100)/m_c.getNum_clauses()+"%\n";
        res+="et le temps d'execution = "+seconde+" seconde";

        this.Resultat.appendText(res);






    }
































    public static Matrice_contingence generate_matrice(String path) throws IOException {


        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = reader.readLine();
        while (line.charAt(0) != 'p') {
            line = reader.readLine();
        }
        String first [] =line.split("\\s+");
        Matrice_contingence m_c=new Matrice_contingence(Integer.parseInt(first[2]),Integer.parseInt(first[3]),reader);
        return m_c;
    }





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




    public static  int EvaluerMeilleurSolutionVoisins(ArrayList <Solution> voisin ,Matrice_contingence m_c){
        int a=0;
        int maxfitness=0;
        int position=0;
        int i=0;
        for (Solution solution : voisin)  {
            a =fitness(solution.getSref(),m_c);
            if(a>maxfitness) {
                maxfitness=a;
                position=i;
            }
            i++;
        }
        return position;
    }



    public static int positionMAX(ArrayList <Integer> list) {
        int max=list.get(0);
        int i=0,j=0;
        for (Integer x : list) {

            if (x>max) {
                max=x;
                j=i;
            }

            i++;
        }
        return j;
    }












}
