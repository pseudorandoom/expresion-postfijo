package deprecated;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BinaryTree<T> extends Element<T> {
    private static final Map<String, Integer> comparables;
    static{
        Map<String, Integer> temporalMap = new HashMap<String, Integer>(5);
        temporalMap.put("^", 3);
        temporalMap.put("*", 2);
        temporalMap.put("/", 2);
        temporalMap.put("+", 1);
        temporalMap.put("-", 1);
        comparables = Collections.unmodifiableMap(temporalMap);
    }

    public BinaryTree(T value){
        setValue(value);
    }

    private Element<T> left;
    private Element<T> right;

    public void setLeft(Element<T> left){
        this.left = left;
    }

    public void setRight(Element<T> right){
        this.right = right;
    }

    @Override
    public boolean isLeaf(){
        return false;
    }

    @Override
    public String toString(){
        return "{ " + valueString() + " : [" +
                leftString() + "," +
                rightString() + "] } ";
    }

    @Override
    public String postfix(){
        return (left == null ? "null" : left.postfix()) + "_" +
                (right == null ? "null" : right.postfix()) + " " +
                valueString() + " ";
    }

    @Override
    public int compareTo(Element<T> o) {
        return comparables.get(getValue()).compareTo(comparables.get(o.getValue()));
    }

    private String leftString(){
        return (left == null ? "null":left.toString());
    }

    private String rightString(){
        return (right == null ? "null":right.toString());
    }
}
