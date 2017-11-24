/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphTheory;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class GraphAlgorithms {
    public Vertex current;
    protected ArrayList<GraphAlgorithmListener> listeners 
            = new ArrayList<GraphAlgorithmListener>();
    
    public void addGraphAlgorithmListener(GraphAlgorithmListener g) {
        listeners.add(g);
    }
    
    public void fireEvent(Vertex parent, Vertex current) {
        for (GraphAlgorithmListener listener:listeners) {
            listener.nodeVisited(new NodeEvent(parent,current));
        }
    }
    
    private String getPath(Vertex end) {
        Stack stack = new Stack();
        Vertex current = end;
        while (current!=null) {
            stack.push(current);
            current=current.getParent();
        }
        String output = "Path:";
        while (!stack.isEmpty()) {
            current = (Vertex)stack.pop();
            output+=current.getName();
            if (!stack.isEmpty())
                output+="->";
        }
        return output;
    }
    
    public String depthFirstSearch(Graph g, Vertex start) {
        g.reset();    //TASK (ALG - Mark all nodes as not visited) - call g's reset method
        String output = "";
        Stack stack = new Stack();  //TASK (ALG - Initialize Stack) - create a new Stack named stack
                                    //TASK (ALG - Set the goal node) - done through GUI, so ignore this step
        start.setVisited(true);//TASK (ALG - Mark the start node as visited)
        stack.push(start);     //TASK (ALG - And add the start node to the stack) 
        
        while(!stack.isEmpty()){     //TASK (ALG - While (Stack Not Empty) Do:) - create a while loop to accomplish this
            //TASK (ALG - currentNode = result of popping stack) - create a new Vertex named
                                              //currentNode and assign it the result of popping the stack (cast required)
            current = (Vertex)stack.pop();
      
			
            RandomIterator iterator = new RandomIterator(current.getEdges());  //Randomize the order of selected edges - done for you
			//(ALG - For each node adjacent to currentNode named nextNode) - done for you
            while (iterator.hasNext()) {
                Edge e = (Edge)iterator.getNext();
                Vertex nextNode = e.getV1();
                if (current.equals(nextNode))
                    nextNode = e.getV2();
                if(!nextNode.isVisited()){
                    nextNode.setVisited(true);
                                        //TASK (ALG - if nextNode is not visited)
					//TASK (ALG - mark nextNode as visted)
                    //Set the parent of nextNode to be currentNode - Done for you
                    nextNode.setParent(current);
                    //TASK (ALG - if nextNode is goal) - check here
                    if(nextNode.isGoal())
						//(ALG - return success) - done for you
                        return output + getPath(nextNode)+"\nSUCCESS!";
						//TASK (ALG - else add nextNode to stack)
                    else
                        stack.push(nextNode);
                }
            }
        	//(ALG - return with failure) - Done for you
            System.out.print("loop");
 
        }
        return output+"FAILURE!";
    }
    public String breadthFirstSearch(Graph g, Vertex start) {
        g.reset();    //TASK (ALG - Mark all nodes as not visited) - call g's reset method
        String output = "";
        Queue stack = new Queue();  //TASK (ALG - Initialize Stack) - create a new Stack named stack
                                    //TASK (ALG - Set the goal node) - done through GUI, so ignore this step
        start.setVisited(true);//TASK (ALG - Mark the start node as visited)
        stack.enqueue(start);     //TASK (ALG - And add the start node to the stack) 
        
        while(!stack.isEmpty()){     //TASK (ALG - While (Stack Not Empty) Do:) - create a while loop to accomplish this
            //TASK (ALG - currentNode = result of popping stack) - create a new Vertex named
                                              //currentNode and assign it the result of popping the stack (cast required)
            current = (Vertex)stack.dequeue();	
            RandomIterator iterator = new RandomIterator(current.getEdges());  //Randomize the order of selected edges - done for you
			//(ALG - For each node adjacent to currentNode named nextNode) - done for you
            while (iterator.hasNext()) {
                Edge e = (Edge)iterator.getNext();
                Vertex nextNode = e.getV1();
                if (current.equals(nextNode))
                    nextNode = e.getV2();
                if(!nextNode.isVisited()){
                    nextNode.setVisited(true);
                //TASK (ALG - if nextNode is not visited)
					//TASK (ALG - mark nextNode as visted)
                    //Set the parent of nextNode to be currentNode - Done for you
                    nextNode.setParent(current);
                    //TASK (ALG - if nextNode is goal) - check here
                    if(nextNode.isGoal())
						//(ALG - return success) - done for you
                        return output + getPath(nextNode)+"\nSUCCESS!";
						//TASK (ALG - else add nextNode to stack)
                    else
                        stack.enqueue(nextNode);
                }
            }
        }
        return output+"FAILURE!";
    }
    public String dijkstra(Graph g, Vertex start) {
        String output = "";
        g.reset(); //mark all nodes as not visited 
        Heap heap = new Heap();  //make a heal instead of a stack or a queue
        float clock = 0;
        start.setVisited(true); //set the start location to visisited
        heap.push(start, 0);     //pushing the start node to the heap with a priority of zero
        //heap.popSmall();  //error       
       while(!heap.isEmpty()){
           current = (Vertex)heap.popSmall();
        //setting current to the the small priority 
           if(current.isGoal())
               return output + getPath(current) + "\n\nSUCCESS!";
        for(Edge e: current.getEdges()){
            Vertex nextNode = e.getV1();
            if(current.equals(nextNode))
                nextNode = e.getV2();
            clock = (float)e.getWeight();  // I had to place an (float) to get an error off
         
            if(!nextNode.isVisited()){
                nextNode.setDistanceTraveled(clock+current.getDistanceTraveled());
                nextNode.setVisited(true);
                nextNode.setParent(current);
                heap.push(nextNode, nextNode.getDistanceTraveled());
            }
            else if (clock+current.getDistanceTraveled() < nextNode.getDistanceTraveled()){
                nextNode.setDistanceTraveled(clock+current.getDistanceTraveled());
                nextNode.setParent(current);
                heap.push(nextNode, nextNode.getDistanceTraveled());
                //heap.push(e, current.getDistanceTraveled());
                //heap.push(e, clock);
            }
         }
      }
       return output + "FAILURE!"; 
    }
    public String aStar(Graph g ,Vertex start ,Heuristic h)      
    { 

        g.reset();
        String output = "";
        Heap openList = new Heap();
        ArrayList<LocationVertex> closedList = new ArrayList<LocationVertex>();
        LocationVertex GoalNode = new LocationVertex("Goal");
        float distanceFromStart = 0;
        double curDistance;
  
        LocationVertex currentNode = new LocationVertex("currentNode");
        for(int i = 0; i < g.getVertices().size();i++)
        {
            if(g.getVertices().get(i).isGoal())
            {
                GoalNode = (LocationVertex)g.getVertices().get(i);
                System.out.println("Goal Node: " + GoalNode.getName());
            }
        }
        ((LocationVertex)start).setCurrEstimate((float)h.evaluate(g, start, GoalNode));
        ((LocationVertex)start).setTotalCost(distanceFromStart + ((LocationVertex)start).getCurrEstimate());
        openList.push(start, (float)((LocationVertex)start).getTotalCost());
        
        while(!openList.isEmpty())
        {
            currentNode = (LocationVertex)openList.popSmall();
            if(currentNode.isGoal())
                return output + getPath(currentNode) + " \n\nSUCCESS!";
            for(Edge e: currentNode.getEdges())
            {
                LocationVertex nextNode = (LocationVertex)e.getV1();
                if(currentNode.equals(nextNode))
                    nextNode = (LocationVertex)e.getV2();
                curDistance = e.getWeight();
                if(openList.contains((LocationVertex)nextNode) == false && closedList.contains((LocationVertex)nextNode) == false)
                {
                    nextNode.distanceTraveled = (float)curDistance + distanceFromStart;
                    nextNode.setCurrEstimate(h.evaluate(g, nextNode, GoalNode));
                    nextNode.setTotalCost(nextNode.distanceTraveled + nextNode.getCurrEstimate());
                    nextNode.parent = current;
                    openList.push(nextNode, (float)nextNode.totalCost);
                }
                else if(curDistance + currentNode.distanceTraveled < nextNode.distanceTraveled)
                {
                    nextNode.distanceTraveled = (float)curDistance + currentNode.distanceTraveled;
                    nextNode.parent = currentNode;
                    nextNode.totalCost = nextNode.distanceTraveled + nextNode.getCurrEstimate();
                    if(openList.contains((LocationVertex)nextNode))
                        openList.remove((LocationVertex)nextNode);
                    if(closedList.contains((LocationVertex)nextNode))
                        closedList.remove((LocationVertex)nextNode);
                    openList.push(nextNode, (float)nextNode.totalCost);
                }
            }
        }
        return output + "FAILURE!";
    }
}