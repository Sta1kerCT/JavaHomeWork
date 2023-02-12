import java.util.Arrays;


public class IntList {
    private int[] arr;
    private int size = 0;


    public IntList() {
        this.arr = new int[4];
    }


    public void add(int a) {
        if (size < arr.length) {
            arr[size++] = a;
        } else {
            arr = Arrays.copyOf(arr, arr.length * 2);
            arr[size++] = a;
        }
    } 


    public void set(int i, int value) {
        arr[i] = value;
    }

    
    public int size() {
        return size;
    }


    public int get(int a) {
        if (-1 < a && a < size) {
            return(arr[a]);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void remove() {
        size--;
    }
}