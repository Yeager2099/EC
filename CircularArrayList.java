/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <
 * Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 * https://catalog.upenn.edu/pennbook/code-of-academic-integrity/
 * >
 * Signed,
 * Author: Yeager Ye
 * Penn email: <yeager18@seas.upenn.edu>
 * Date: 2025-08-30
 */

import java.util.NoSuchElementException;

public class CircularArrayList<E> {

    /*
     * Do not change this initial capacity; it is used by our test cases
     */
    private static final int INITIAL_CAPACITY = 4;

    /*
     * These are protected so that test cases can access them. Please do not change
     * the visibility of these fields!
     */
    protected String[] data;
    protected int size = 0;
    protected int startIndex = 0;

    public CircularArrayList() {
        data = new String[INITIAL_CAPACITY];
    }

    public CircularArrayList(String[] arr) {
        if (arr == null || arr.length == 0) {
            data = new String[INITIAL_CAPACITY];
        } else {
            data = new String[Math.max(INITIAL_CAPACITY, arr.length)];
            for (int i = 0; i < arr.length; i++) {
                data[i] = arr[i];
            }
            size = arr.length;
        }
    }

    public String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int actualIndex = (startIndex + index) % data.length;
        return data[actualIndex];
    }

    private void increaseCapacity() {
        String[] newData = new String[data.length * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = get(i);
        }
        data = newData;
        startIndex = 0;
    }

    private void shrinkCapacity() {
        if (size <= data.length / 4 && data.length > INITIAL_CAPACITY) {
            String[] newData = new String[Math.max(INITIAL_CAPACITY, data.length / 2)];
            for (int i = 0; i < size; i++) {
                newData[i] = get(i);
            }
            data = newData;
            startIndex = 0;
        }
    }

    /*
     * Adds an element to the back of the circular array list.
     */
    public void add(String value) {
        if (size == data.length) {
            increaseCapacity();
        }
        int endIndex = (startIndex + size) % data.length;
        data[endIndex] = value;
        size++;
    }

    /*
     * Adds an element to the front of the circular array list.
     */
    public void addFront(String value) {
        if (size == data.length) {
            increaseCapacity();
        }
        startIndex = (startIndex - 1 + data.length) % data.length;
        data[startIndex] = value;
        size++;
    }

    /*
     * Adds an element to the specified index. Should shift elements to the right if needed.
     * Throws IndexOutOfBoundsException if index is invalid.
     */
    public void add(int index, String element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (size == data.length) {
            increaseCapacity();
        }
        
        if (index == 0) {
            addFront(element);
        } else if (index == size) {
            add(element);
        } else {
            // Shift elements to the right
            for (int i = size - 1; i >= index; i--) {
                int currentIndex = (startIndex + i) % data.length;
                int nextIndex = (startIndex + i + 1) % data.length;
                data[nextIndex] = data[currentIndex];
            }
            
            int insertIndex = (startIndex + index) % data.length;
            data[insertIndex] = element;
            size++;
        }
    }

    /*
     * Removes element at specified index.
     * Should shift elements to the left if needed.
     * Throws IndexOutOfBoundsException if index is invalid.
     * Return removed element.
     */
    public String remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        String removed = get(index);
        
        if (index == 0) {
            removeFront();
        } else if (index == size - 1) {
            removeBack();
        } else {
            // Shift elements to the left
            for (int i = index; i < size - 1; i++) {
                int currentIndex = (startIndex + i) % data.length;
                int nextIndex = (startIndex + i + 1) % data.length;
                data[currentIndex] = data[nextIndex];
            }
            size--;
            shrinkCapacity();
        }
        
        return removed;
    }

    /*
     * Removes first occurrence of element that matches the argument.
     * Return true if element removed, and false otherwise.
     */
    public boolean remove(String obj) {
        for (int i = 0; i < size; i++) {
            if ((obj == null && get(i) == null) || (obj != null && obj.equals(get(i)))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    /*
     * Removes element at the start of the circular array list.
     * Return the element removed.
     * Throws NoSuchElementException if no elements present.
     */
    public String removeFront() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
        
        String removed = data[startIndex];
        data[startIndex] = null;
        startIndex = (startIndex + 1) % data.length;
        size--;
        shrinkCapacity();
        
        return removed;
    }

    /*
     * Removes element at the end of the circular array list.
     * Return the element removed.
     * Throws NoSuchElementException if no elements present.
     */
    public String removeBack() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
        
        int endIndex = (startIndex + size - 1) % data.length;
        String removed = data[endIndex];
        data[endIndex] = null;
        size--;
        shrinkCapacity();
        
        return removed;
    }

    // Note - for your debugging. We will not test it.
    public void print() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(get(i));
        }
        System.out.println("]");
    }

    /*
     * Replace existing element at specified index with the new value.
     * Return the element replaced, i.e. the old value.
     * Throws IndexOutOfBoundsException if index is out of range.
     */
    public String set(int index, String obj) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        int actualIndex = (startIndex + index) % data.length;
        String oldValue = data[actualIndex];
        data[actualIndex] = obj;
        
        return oldValue;
    }

    /*
     * Return true if the element is present.
     */
    public boolean contains(String obj) {
        for (int i = 0; i < size; i++) {
            if ((obj == null && get(i) == null) || (obj != null && obj.equals(get(i)))) {
                return true;
            }
        }
        return false;
    }
}
