import java.util.Arrays;
import java.util.Iterator;
// source: https://medium.com/@wolfbang/implement-a-custom-arraylist-in-java-ee8f73dfdda2

// Custom ArrayList implentation that doesnt allow for duplicate types
interface TypeList<E> extends Iterable<E>{
    boolean isEmpty();
    int size();
    boolean add(E element);
    boolean add(int index, E element);
    void remove(E element);
    E get(int index);
    int indexOf(E element);
    void set(int index, E element);
    boolean contains(E element);
    void clear();
    E[] toArray();
}

public class TypeArray<E> implements TypeList<E> {
    private E[] elements;
    private int size;

    public TypeArray(){
        this(10);
    }

    public TypeArray(int initSize){
        if(initSize < 0){
            throw new IllegalArgumentException("Illegal Size: " + initSize);
        }
        this.elements = (E[]) new Object[initSize];
    }

    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public int size(){
        return this.size;
    }

    private void ensureCapcity(int needCapacity){
        if(needCapacity > elements.length){
            Object[] oldElements = this.elements;
            int newSize = this.size * 2 + 1;
            this.elements = (E[]) new Object[newSize];
            this.elements = (E[]) Arrays.copyOf(oldElements, newSize);
        }
    }

    @Override
    public boolean add(E element){
        try{
            checkDuplicates(element);
        } catch (DuplicateCreationException ex){
            ex.getMessage();
            ex.printStackTrace();
            System.exit(1);
            return false;
        }
        ensureCapcity(this.size+1);
        elements[this.size++] = element;
        return true;
    }

    private void checkRange(int index){
        if(index < 0 || index >= (this.size + 1)){
            throw new IllegalArgumentException("Illegal Index: " + index);
        }
    }

    @Override
    public boolean add(int index, E element){
        try{
            checkDuplicates(element);
        } catch (DuplicateCreationException ex){
            ex.getMessage();
            ex.printStackTrace();
            System.exit(1);
            return false;
        }
        checkRange(index);
        ensureCapcity(this.size + 1);
        System.arraycopy(this.elements, index, elements, index + 1, size - index);
        elements[index] = element;
        this.size++;
        return true;
    }

    @Override
    public void remove(E element){
        if(element == null){
            for(int i=0;i<this.size;i++){
                if(this.elements[i] == null){
                    fastRemove(i);
                    return;
                }
            }
        } else {
            for(int i=0;i<this.size;i++){
                if(element.equals(this.elements[i])){
                    fastRemove(i);
                    return;
                }
            }
        }
    }

    private void checkDuplicates(E element) throws DuplicateCreationException{
        //CusLib.DebugOutputLn("Checking for Dupes, Array Size: " + this.size);
        for(int i=0;i<this.size;i++){
            if(element.equals(elements[i])){
                throw new DuplicateCreationException("Duplicate Item!, Class: " + element.getClass().getSimpleName() + ", Adding Prevented");
            }
        }
    }

    private void fastRemove(int index){
        int movedNumber = this.size - index - 1;
        if (movedNumber > 0){
            System.arraycopy(this.elements, index + 1, this.elements, index, movedNumber);
        }
        this.elements[--this.size] = null;
    }

    @Override
    public E get(int index){
        E e = this.elements[index];
        return e;
    }

    @Override 
    public int indexOf(E element){
        if(element == null){
            for(int i=0; i<this.elements.length;i++){
                if(this.elements[i] == null){
                    return i;
                }
            }
        } else {
            for(int i=0;i<this.elements.length;i++){
                if(element.equals(this.elements[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void set(int index, E element){
        checkRange(index);
        ensureCapcity(this.size + 1);
        this.elements[index] = element;
    }

    @Override
    public boolean contains(E element){
        if(indexOf(element) >= 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear(){
        for(int i=0;i<this.size;i++){
            this.elements[i] = null;
        }
        this.size = 0;
    }

    @Override
    public E[] toArray(){
        return this.elements;
    }

    @Override
    public Iterator<E> iterator(){
        return new TypeIterator<>();
    }

    @Override
    public String toString(){
        return "TypeArray" +
                "elements=" + Arrays.toString(elements) + 
                ", size=" + size + "}";
    }

    private class TypeIterator<E> implements Iterator<E>{
        private int current = 0;
    
        @Override
        public boolean hasNext(){
            return this.current < size();
        }
    
        @Override
        public E next(){
            E value = (E) elements[current++];
            return value;
        }
    }
}

