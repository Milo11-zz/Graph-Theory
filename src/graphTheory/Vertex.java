package graphTheory;
/*@author Eric */
import java.io.Serializable;
import java.util.ArrayList;
public class Vertex implements Serializable {
    protected String name = "Default";
    protected ArrayList<Edge> edges = new ArrayList<Edge>();
    protected boolean visited;
    protected boolean goal;
    protected Vertex parent;
    protected float distanceTraveled;
    
    //Does nothing 
    Vertex(){
    
    }
    //Gets a String from the user and set the name to what the user type in...
    Vertex(String name){
        this.name = name;
    }
    Vertex(String name, float distance){
        this.name = name;
        this.distanceTraveled = distance;
    }
    //return distanceTraveled
    public float getDistanceTraveled(){
        return distanceTraveled;
    }
    //set the distanceTraveled
    public void setDistanceTraveled(float distance){
        distanceTraveled = distance;
    }
    //Return name;
    public String getName(){
        return name;
    }
    //return edges
    public ArrayList<Edge> getEdges(){
        return edges;
    }
    public void addEdgeTo(Vertex one){
        addEdgeTo(one,1);
    }
    public void addEdgeTo(Vertex one, float weight){
        edges.add(new Edge(this,one,weight));
        one.edges.add(new Edge(one,this,weight));      
    }
    public String toString(){ 
        String returnString=name+"\n";
        for(Edge e:edges){
            returnString += "\t"+e+"\n";
        }
        return returnString;
    }
    public boolean equals(Object o){
        try{
            Vertex v = (Vertex) o;
            return v.name.equals(name);
        }
        catch(Exception e){
            return false;
        }
    }
    public boolean isVisited(){
        return visited;
    }
    public boolean isGoal(){
        return goal;
    }
    public void setVisited(boolean v){
        visited = v;
    }
    public void setGoal(boolean g){
        goal = g;
    }
    public Vertex getParent(){
        return parent;
    }
    public void setParent(Vertex v){
        parent = v;
    }
    
}

