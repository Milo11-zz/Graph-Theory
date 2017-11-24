package graphTheory;
/*@author Eric */
public class ZeroHeuristic implements Heuristic{
    
    public double evaluate(Graph graph, Vertex current, Vertex goal) {
        return 0.0;
    }

}
