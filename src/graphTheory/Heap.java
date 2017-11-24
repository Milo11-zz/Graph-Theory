package graphTheory;

/*   @author Eric  */
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Heap {
    protected ArrayList<PrioritizedItem> list;
    
    
    public Heap(){
        list = new ArrayList();
    }
    public void push(Object o, float f){
        PrioritizedItem temp = new PrioritizedItem(o, f);
        list.add(temp);
    }
    public int size(){
        return list.size();
    }
    public boolean isEmpty(){
        return size()==0;
    }
    public float getLowestPriority(){
        PrioritizedItem temp = list.get(0);
        for(int i = 1; i < size(); i++){
            if(temp.getPriority() > list.get(i).getPriority())
                temp = list.get(i);
        }
        return temp.getPriority();
    }
    public float getHighestPriority(){
        PrioritizedItem temp = list.get(0);
        for(int i = 1; i < size(); i++){
            if(temp.getPriority() < list.get(i).getPriority())
                temp = list.get(i);
        }
        return temp.getPriority();
    }
    public Object popSmall(){
        float pop = getLowestPriority();
        PrioritizedItem removed = new PrioritizedItem("error", 0);
        int i;
        for(i = 0; i < size();i++){
            if(pop == list.get(i).getPriority()){
                removed = list.get(i);
                list.remove(list.get(i));
            }
 
        }
        return removed.getValue();
    }        
    public Object popBig(){
    float pop = getHighestPriority();
    PrioritizedItem removed = new PrioritizedItem("error",0);
        int i;
        for(i = 0; i < size();i++){
            if(pop == list.get(i).getPriority()){
                removed = list.get(i);
                list.remove(list.get(i));
            }
        }
        return removed.getValue();
    }
    public boolean contains(Object object)
    {
        for(int i= 0; i < size();i++)
            if(list.get(i) == ((PrioritizedItem)object))
                return true;
        return false;
    }
    public boolean remove(Object object)
    {
        return (contains(object) && size() == 1);
    }
  //  public static void main(String args[]){
  //    Heap heap = new Heap();
   //   String temp = "";
    //  String output = "";
    //  while(temp.compareTo("done") != 0){
    //      String test = JOptionPane.showInputDialog("String : ");
    //      int intText = Integer.parseInt(JOptionPane.showInputDialog("Int"));
      //    PrioritizedItem item = new PrioritizedItem(test, intText);
        //  heap.list.add(item);
          ///temp = test;
//      }
  //    int bla = Integer.parseInt(JOptionPane.showInputDialog("1)popSmall 2)popBig"));
    //  if(bla == 2){
      //while(!heap.isEmpty()){
        //  String current = (String)heap.popBig();
          //output += current+"\n";
//      }
  //    JOptionPane.showMessageDialog(null, "the ouput is : " + output);
    //  }
      //else{
//      while(!heap.isEmpty()){
  //        String current = (String)heap.popSmall();
    //      output += current+"\n";
      //}
//      JOptionPane.showMessageDialog(null, "the ouput is : " + output);
  //    }
      
}
