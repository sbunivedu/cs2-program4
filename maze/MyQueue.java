package maze;
import java.util.LinkedList;

public class MyQueue<T> implements Agenda<T>{
  private LinkedList<T> queue;

  public MyQueue(){
    queue = new LinkedList<T>();
  }

  public boolean isEmpty(){
    return queue.isEmpty();
  }

  public int size(){
    return queue.size();
  }

  public void add(T newItem){
    queue.add(newItem);
  }

  public T remove(){
    return queue.removeFirst();
  }

  public T peek(){
    return queue.peek();
  }
}