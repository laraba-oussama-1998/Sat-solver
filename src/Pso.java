

import java.util.ArrayList;
import java.util.Collection;


public class Pso {
    protected boolean mardtni=true;
    protected Matrice_contingence m_c;
    protected Population population ;
    protected ArrayList<Fitness> fit;
    protected ArrayList<Particle> particles;
    protected ArrayList<Integer> Gbest;
    protected int gbestfitness=0;
    protected int gbestfitnesspos=0;
    protected double w,r1,r2;
    protected Double c1=1.8,c2=1.9;
    public Pso (Matrice_contingence m_c){

       
        this.w=0.2;
        this.m_c=m_c;
        this.population=new Population(Population.generer_poputlation(m_c.getNum_variables(),500)) ;
        this.particles=new ArrayList<>(this.population.getPop().size());
         for(int i=0;i<this.population.getPop().size();i++)
             this.particles.add(new Particle(this.population.getPop().get(i),this.m_c.num_variables));


        this.fit =new ArrayList<>(this.population.getPop().size());

        for(int i=0;i<this.particles.size();i++){fit.add(new Fitness(0,i));}

        for(int i=0;i<this.particles.size();i++){
            this.fit.get(i).setScore(fitness(this.particles.get(i).position,this.m_c));
        
            if(this.fit.get(i).score>this.gbestfitness){
                this.gbestfitness=this.fit.get(i).score;
                this.gbestfitnesspos=i;
            }
        }
        this.Gbest = new ArrayList<Integer>(this.particles.get(this.gbestfitnesspos).Pbest);

        

    }

    public void position_movement(){
        ArrayList<Integer> pos = new ArrayList<>(this.getGbest().size());
        for(int i=0;i<this.particles.size();i++) {
            pos.clear();

            do {
                this.r1=Math.random();
                this.r2=Math.random();
                this.particles.get(i).setVolacity((int) (this.w*this.particles.get(i).getVolacity()
                   + c1*r1*(fitness(this.particles.get(i).getPbest(),this.m_c)-fitness(this.particles.get(i).getPosition(),this.m_c))
                   + c2*r2*(fitness(this.Gbest,this.m_c)-fitness(this.particles.get(i).getPosition(),this.m_c))));



                if((fitness(this.Gbest,this.m_c)-fitness(this.particles.get(i).getPosition(),this.m_c))<0)mardtni=false;


            } while (this.particles.get(i).getVolacity() >= this.m_c.num_variables || this.particles.get(i).getVolacity() < 0);

            ArrayList<Integer> position_move = new ArrayList<>(this.particles.get(i).getVolacity());
            int pose;

            for (int j = 0; j < this.particles.get(i).getVolacity(); j++) {
                do {

                    do {
                        pose = (int) (Math.random() * 100);

                    } while (pose >= this.m_c.getNum_variables() || pose < 0);

                } while (position_move.contains(pose));

                position_move.add(pose);
            }
           pos = new ArrayList<>( this.particles.get(i).getPosition());
          for (Integer ps : position_move)
            pos.set(ps,(this.particles.get(i).position.get(ps) == 1 ? -1 : 1));
           
                this.getPopulation().pop.add(pos);
                this.particles.get(i).setPosition(new ArrayList<>(pos));

               
        }

       
        this.gbestfitnesspos=0;
        this.gbestfitness=0;

        for(int i=0;i<this.particles.size();i++){
            if(fitness(this.particles.get(i).position,this.m_c)>=fitness(this.particles.get(i).Pbest,this.m_c)){
            this.fit.get(i).setScore(fitness(this.particles.get(i).position,this.m_c));
            this.particles.get(i).setPbest(new ArrayList<>(this.particles.get(i).position));

            }
         
            if(this.fit.get(i).score>=this.gbestfitness){
                this.gbestfitness=this.fit.get(i).score;
                this.gbestfitnesspos=i;
            }
        }
        this.Gbest = new ArrayList<Integer>(this.particles.get(this.gbestfitnesspos).Pbest);


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


    public Matrice_contingence getM_c() {
        return m_c;
    }

    public void setM_c(Matrice_contingence m_c) {
        this.m_c = m_c;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public ArrayList<Fitness> getFit() {
        return fit;
    }

    public void setFit(ArrayList<Fitness> fit) {
        this.fit = fit;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particles = particles;
    }

    public ArrayList<Integer> getGbest() {
        return Gbest;
    }

    public void setGbest(ArrayList<Integer> gbest) {
        Gbest = gbest;
    }

    public int getGbestfitness() {
        return gbestfitness;
    }

    public void setGbestfitness(int gbestfitness) {
        this.gbestfitness = gbestfitness;
    }

    public int getGbestfitnesspos() {
        return gbestfitnesspos;
    }

    public void setGbestfitnesspos(int gbestfitnesspos) {
        this.gbestfitnesspos = gbestfitnesspos;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public boolean isMardtni() {
        return mardtni;
    }

    public void setMardtni(boolean mardtni) {
        this.mardtni = mardtni;
    }


}

