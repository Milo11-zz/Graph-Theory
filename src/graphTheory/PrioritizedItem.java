/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphTheory;

/**
 *
 * @author Eric
 */
public class PrioritizedItem {
    protected Object value;
    protected float priority;
    

public Object getValue(){
    return value;
}
public float getPriority(){
    return priority;
}
public PrioritizedItem(Object o,float f){
    value = o;
    priority = f;
}
}