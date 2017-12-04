package maze;

public interface Agenda<T>{
  public boolean isEmpty();
  public int size();
  public void add(T newItem);
  public T remove();
  public T peek();
}