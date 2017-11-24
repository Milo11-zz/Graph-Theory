package graphTheory;
/* @author Eric */
public class LocationVertex extends Vertex{
    protected int xLocation;
    protected int yLocation;
    protected double currEstimate;
    //added lab 5 part 2 of 4
    protected double totalCost;
    
    LocationVertex(String name){
        super(name);
        xLocation = 0;
        yLocation = 0;
        distanceTraveled = 0;
        currEstimate = 0;
        setGoal(false);
        setVisited(false);
    }
    LocationVertex(String name, int xLocation,int yLocation){
        super(name);
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        distanceTraveled = 0;
        currEstimate = 0;
        setVisited(false);
        setGoal(false);
        totalCost = 0.0;
    }
    @Override
    public String toString(){
    String returnString =name + "(" + xLocation + "," + yLocation + ") estimate:" + currEstimate + "\n";
        for(Edge e:edges){
            returnString += "\t"+e+"\n";
        }
        return returnString;
    }
    double getTotalCost()
    {
        return totalCost;
    }
    void setTotalCost(double totalCost)
    {
        this.totalCost = totalCost;
    }
    int getxLocation(){
        return xLocation;
    }
    int getyLocation(){
        return yLocation;
    }
    double getCurrEstimate(){
        return currEstimate;
    }
    void setCurrEstimate(double currEstimate){
        this.currEstimate = currEstimate;
    }
}
