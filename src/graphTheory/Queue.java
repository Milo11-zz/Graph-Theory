package graphTheory;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/* @author Eric */
public class Queue 
{
    protected ArrayList list;
    
    public Queue()
    {
        list = new ArrayList();
    }
    public void enqueue(Object o)
    {
        list.add(o);
    }
    public int size()
    {
        return list.size();
    }
    public boolean isEmpty()
    {
        if(size() == 0)
            return true;
       return false;
    }
    public Object dequeue()
    {
        if(!isEmpty()){
            Object removeMe = list.get(0);
            list.remove(0);
            return removeMe;
        }
        return null;
    }
    
    //This was to test the queue. 
    /*
    public static void main(String args[]){
    Queue queue = new Queue();
    String killstring = "done";
    String user = " ";
    while(user.compareTo(killstring) != 0){
        user = JOptionPane.showInputDialog("Enter another String...");
        queue.enqueue(user);
    }
    String output = "";
    String current = " ";
    while(!queue.isEmpty()){
        current = (String)queue.dequeue();
        output += current;
    }
    JOptionPane.showMessageDialog(null, "Dequeue results: \n" + output);
}*/
}
