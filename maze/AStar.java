package maze;

public class AStar<T> implements Agenda<T>{
  private Heap<T> h;

  public AStar(){
    h = new Heap<T>();
  }

  public boolean isEmpty(){
    return h.isEmpty();
  }

  public int size(){
    return h.size();
  }

  public void add(T newItem){
    h.insert(newItem);
  }

  public T remove(){
    return h.remove();
  }

  public T peek(){
    return h.peek();
  }
}