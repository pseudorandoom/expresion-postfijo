package deprecated;

public class Node<T> extends Element<T> {

    public Node(T value){
        setValue(value);
    }

    @Override
    public boolean isLeaf(){
        return true;
    }

    @Override
    public String toString(){
        return getValue() == null? "null" : getValue().toString();
    }

    @Override
    public int compareTo(Element<T> o) {
        throw new AssertionError("Leaf nodes should not be compared");
    }

    public void setLeft(Element<T> elem){
        throw new AssertionError("Shoud not add elements to a leaf node");
    }

    public void setRight(Element<T> elem){
        throw new AssertionError("Shoud not add elements to a leaf node");
    }
}
