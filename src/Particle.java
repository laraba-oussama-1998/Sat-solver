
import org.omg.PortableServer.POA;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Particle {
     protected ArrayList<Integer> position;
     protected ArrayList<Integer> Pbest;
     protected int volacity;

     public Particle(ArrayList<Integer> position,int variable){

         this.position=new ArrayList<>(position);
         this.Pbest=new ArrayList<>(position);
         do{
             this.volacity= (int) (Math.random()*100);
         }while(this.volacity>variable && this.volacity<1);

     }
     public Particle(Particle p){

        this.position=new ArrayList<>(p.getPosition());
        this.Pbest=new ArrayList<>(p.getPbest());
        this.volacity= p.getVolacity();

    }


    public ArrayList<Integer> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Integer> position) {
        this.position = position;
    }

    public ArrayList<Integer> getPbest() {
        return Pbest;
    }

    public void setPbest(ArrayList<Integer> pbest) {
        Pbest = pbest;
    }

    public int getVolacity() {
        return volacity;
    }

    public void setVolacity(int volacity) {
        this.volacity = volacity;
    }
}
