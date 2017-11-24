package graphTheory;
import java.util.ArrayList;
/*@author Eric*/


public class Stack {
    protected ArrayList list;

public Stack()
{
    this.list = new ArrayList();
}
public void push(Object o)
{
    list.add(0, o);
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
public Object pop()
{
    if(isEmpty())
        return null;
    Object removeMe = list.get(0);
    list.remove(removeMe);
    return removeMe;
}

//This was to test the stack of the program.. 

/*public static void main(String args[]){
    Stack stack = new Stack();
    String killstring = "done";
    String userInput = " ";
    String user = " ";
    while(user.compareTo(killstring) != 0){
        user = JOptionPane.showInputDialog("Enter another String...");
        userInput += user;
        stack.push(user);
        user.toLowerCase();
    }
    String output = "";
    String current = " ";
    while(!stack.isEmpty()){
        current = (String)stack.pop();
        output += current;
    }
    JOptionPane.showMessageDialog(null, "Pop results: \n" + output);
}*/
}