package deprecated;

public abstract class Element<T> implements Comparable<Element<T>> {
    private T value;

    public abstract void setLeft(Element<T> left);
    public abstract void setRight(Element<T> right);

    public String postfix(){
        return value == null? "null" : getValue().toString();
    }
    public T getValue(){
        return value;
    }
    public void setValue(T value){
        this.value =  value;
    }
    public abstract boolean isLeaf();

    public boolean isNode(){
        return !isLeaf();
    }
    public String valueString(){
        return value == null ? "null" : value.toString();
    }
}
