package graphTheory;
/* @author Eric */
import java.io.Serializable;
public class Edge implements Serializable{
    protected double weight = 1;
    protected Vertex v1;
    protected Vertex v2;
    
    public Edge(Vertex v1, Vertex v2){
        this.v1 = v1;
        this.v2 = v2;
    }
    public Edge(Vertex v1, Vertex v2, double weight){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }
    public double getWeight(){
        return weight;
    }
    public void setWeight(double newWeight){
        weight = newWeight;
    }
    public void setDistance(double dis){
        setWeight(dis);
    }
    public Vertex getV1(){
        return v1;
    }
    public Vertex getV2(){
        return v2;
    }
    public String toString(){
        return "(" + v1.getName()+" to " + v2.getName()+") weight:"+weight;
    }
    
}
