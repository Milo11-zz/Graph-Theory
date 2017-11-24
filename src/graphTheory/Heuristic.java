package graphTheory;

/*@author Eric */
public interface Heuristic {
    double evaluate(Graph graph,Vertex current,Vertex goal);
}
