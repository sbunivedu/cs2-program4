package maze;

import java.util.Arrays;
import maze.exceptions.EmptyCollectionException;

// Max Heap
public class Heap<T>{
  private static final int DEFAULT_CAPACITY = 200;
  private int insertPoint; // location for new node
  private T[] tree;

  public Heap(){
    tree = (T[]) new Object[DEFAULT_CAPACITY];
    insertPoint = 0;
  }

  public boolean isEmpty(){
    return tree[0] == null;
  }

  public int size(){
    return insertPoint;
  }

  public String toString(){
    String result = "";
    for (int i = 0; i < insertPoint; i++){
      result += tree[i]+" ";
    }
    return result;
  }

  public T peek(){
    if (tree == null){
      return null;
    }else{
      return tree[0];
    }
  }

  public void insert(T element){
    if (insertPoint+10 == tree.length){
      expandCapacity();
    }
    tree[insertPoint] = element;
    //nifty trick for finding parent's location
    int parent = (insertPoint-1)/2;
    int child = insertPoint;
    //increase to the next insertion location
    insertPoint++;

    boolean done = false;
    while ((parent >= 0) && !done){
      if(((Comparable) tree[parent]).compareTo(tree[child]) < 0){
        swap(parent,child);
        child = parent;
        parent = (parent-1)/2;
      }else{
        done = true;
      }
    }
  }

  private void swap(int parent, int child){
    T temp = tree[parent];
    tree[parent] = tree[child];
    tree[child] = temp;
  }

  private void expandCapacity(){
    tree = Arrays.copyOf(tree, tree.length * 2);
  }

  public T remove(){
    if(isEmpty()){
      throw new EmptyCollectionException("Heap");
    }
    T result = tree[0]; //the element from the root.

    if (insertPoint == 1){
      //there was only the root & it was plucked
      tree[0] = null;
      insertPoint --;
    }else{
      //replace the root with the last node inserted
      tree[0] = tree[insertPoint-1];
      tree[insertPoint-1] = null;
      insertPoint --;
      reHeapify(); //restore the heap property.
    }
    return result;
  }

  private void reHeapify(){
    // start from root
    int root = 0;
    int left = 1;
    int right = 2;
    int next = findSwapChild(left, right);
    while(next < insertPoint &&
      ((Comparable) tree[next]).compareTo(tree[root]) > 0){
      swap(root, next);
      root = next;
      left = 2*root+1;
      right = 2*root+2;
      next = findSwapChild(left, right);
    }
  }

  private int findSwapChild(int left, int right){
    int next = 0;
    if(left >= insertPoint && right >= insertPoint){
      next = insertPoint;
    }else if(right >= insertPoint){
      next = left;
    }else if(((Comparable) tree[left]).compareTo(tree[right]) > 0){
      // pick larger of the two
      next = left;
    }else{
      next = right;
    }
    return next;
  }
}
