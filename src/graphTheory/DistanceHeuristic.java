package graphTheory;
/*@author Eric */

public class DistanceHeuristic implements Heuristic {
    
    public double evaluate(Graph graph, Vertex current, Vertex goal) {
        //lcurrent in the (x,y) one
        LocationVertex lcurrent = (LocationVertex)current;
        //lgoal in the (x,y) two
        LocationVertex lGoal = (LocationVertex) goal;
        double tempX = lGoal.xLocation - lcurrent.xLocation;
        double tempY = lGoal.yLocation - lcurrent.yLocation;
        tempX = tempX * tempX;
        tempY = tempY * tempY;
        double dis = Math.sqrt((tempX + tempY));
        return dis;
    }
    /*Testing class*/
    
    /*public static void main(String [] args) {
        LocationVertex x = new LocationVertex("First", 4, 12);
        LocationVertex y = new LocationVertex("Second",10,15);
        
        Heuristic h = new DistanceHeuristic();
        System.out.println(h.evaluate(null, x, y));
    }*/
    
}
