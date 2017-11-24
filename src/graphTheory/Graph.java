package graphTheory;
/*@author Eric */
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Graph implements Serializable{
    protected ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    protected String name;

    public ArrayList<Vertex> getVertices(){
        return vertices;
    }
    public String getName(){
        return name;
    }
    public Graph(String name){
        this.name = name;
    }
    public Graph(String name,ArrayList<Vertex> v){
        this.name = name;
        vertices.add(new Vertex(name));
    }
    public int getSize(){
        return vertices.size();
    }
    public boolean contains(Vertex v){
        return vertices.contains(v);
    }
    public void addVertex(Vertex v){
        if(!contains(v))
            vertices.add(v);
        else 
            JOptionPane.showMessageDialog(null, "The Vertex " + v +" is not in the list");
    }
    public void addEdge(Vertex vOne,Vertex vTwo){
        addEdge(vOne,vTwo,1);
    }
    public void addEdge(Vertex vOne,Vertex vTwo,float weight){
        if (contains(vOne)&&contains(vTwo)) {
            Vertex v1 = nameToVertex(vOne.getName());
            Vertex v2 = nameToVertex(vTwo.getName());
            v1.addEdgeTo(v2, weight);
        }
    }
    public Vertex nameToVertex(String stringName){
        for (Vertex v:vertices) {
            if (v.getName().equals(stringName))
                return v;
        }
        return null;
    }
    public String toString(){
        String temp = "Graph"+name+"\n";
        for(Vertex v:vertices){
            temp += v.toString()+"\n";
        }
        return temp;
    }
    public void reset(){
        for(Vertex v:vertices){
            v.setVisited(false);
            v.setDistanceTraveled(-1);
            if(v instanceof LocationVertex)
            {
                v.distanceTraveled = 0;
            }
            
        }
    }

}

