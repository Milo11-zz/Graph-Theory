/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphTheory;

/**
 *
 * @author user
 */
public class NodeEvent {
    protected Vertex visitedNode;
    protected Vertex parent;
    
    public NodeEvent(Vertex parent,Vertex visitedNode) {
        this.visitedNode = visitedNode;
        this.parent = parent;
    }
    
    public Vertex getVisitedNode() {
        return visitedNode;
    }
    
    public Vertex getParent() {
        return parent;
    }
}
