/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <
 * Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 *  https://catalog.upenn.edu/pennbook/code-of-academic-integrity/
 * >
 * Signed,
 * Author: Yeager Ye
 * Penn email: <yeager18@seas.upenn.edu>
 * Date: 2025-08-30
 */

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
        this.data = new String[INITIAL_CAPACITY];
        this.size = 0;
        this.startIndex = 0;
    }

    public CircularArrayList(String[] arr) {
        if (arr == null || arr.length == 0) {
            this.data = new String[INITIAL_CAPACITY];
            this.size = 0;
            this.startIndex = 0;
            return;
        }
        int capacity = Math.max(INITIAL_CAPACITY, arr.length);
        this.data = new String[capacity];
        this.startIndex = 0;
        this.size = arr.length;
        for (int i = 0; i < arr.length; i++) {
            this.data[i] = arr[i];
        }
    }

    public String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data[realIndex(index)];
    }

    private void increaseCapacity() {
        int newCapacity = (data == null || data.length == 0) ? INITIAL_CAPACITY : data.length * 2;
        String[] newData = new String[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[realIndex(i)];
        }
        data = newData;
        startIndex = 0;
    }

    private void shrinkCapacity() {
        if (data == null) {
            return;
        }
        int currentCapacity = data.length;
        if (currentCapacity <= INITIAL_CAPACITY) {
            return;
        }
        if (size <= currentCapacity / 4) {
            int newCapacity = Math.max(INITIAL_CAPACITY, currentCapacity / 2);
            if (newCapacity < size) {
                newCapacity = size;
            }
            String[] newData = new String[newCapacity];
            for (int i = 0; i < size; i++) {
                newData[i] = data[realIndex(i)];
            }
            data = newData;
            startIndex = 0;
        }
    }

    /*
     * Adds an element to the back of the circular array list.
     */
    public void add(String value) {
        if (data == null || data.length == 0) {
            data = new String[INITIAL_CAPACITY];
        }
        if (size == data.length) {
            increaseCapacity();
        }
        int idx = realIndex(size);
        data[idx] = value;
        size++;
    }

    /*
     * Adds an element to the front of the circular array list.
     */
    public void addFront(String value) {
        if (data == null || data.length == 0) {
            data = new String[INITIAL_CAPACITY];
        }
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
        if (data == null || data.length == 0) {
            data = new String[INITIAL_CAPACITY];
        }
        if (size == data.length) {
            increaseCapacity();
        }
        if (index < size / 2) {
            // Shift left the first part [0..index-1]
            for (int k = 0; k < index; k++) {
                int from = (startIndex + k) % data.length;
                int to = (startIndex + k - 1 + data.length) % data.length;
                data[to] = data[from];
            }
            startIndex = (startIndex - 1 + data.length) % data.length;
        } else {
            // Shift right the second part [index..size-1]
            for (int k = size - 1; k >= index; k--) {
                int from = (startIndex + k) % data.length;
                int to = (startIndex + k + 1) % data.length;
                data[to] = data[from];
            }
        }
        data[realIndex(index)] = element;
        size++;
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
        int physicalIndex = realIndex(index);
        String removed = data[physicalIndex];
        if (index < size / 2) {
            // Shift right [0..index-1]
            for (int k = index; k > 0; k--) {
                int from = realIndex(k - 1);
                int to = realIndex(k);
                data[to] = data[from];
            }
            // Clear old start and move start forward
            data[startIndex] = null;
            startIndex = (startIndex + 1) % data.length;
        } else {
            // Shift left [index+1..size-1]
            for (int k = index; k < size - 1; k++) {
                int from = realIndex(k + 1);
                int to = realIndex(k);
                data[to] = data[from];
            }
            // Clear the last element
            int lastIdx = realIndex(size - 1);
            data[lastIdx] = null;
        }
        size--;
        shrinkCapacity();
        return removed;
    }

    /*
     * Removes first occurrence of element that matches the argument.
     * Return true if element removed, and false otherwise.
     */
    public boolean remove(String obj) {
        for (int i = 0; i < size; i++) {
            String curr = data[realIndex(i)];
            if (obj == null ? curr == null : obj.equals(curr)) {
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
            throw new java.util.NoSuchElementException("List is empty");
        }
        String value = data[startIndex];
        data[startIndex] = null;
        startIndex = (startIndex + 1) % data.length;
        size--;
        shrinkCapacity();
        return value;
    }

    /*
     * Removes element at the end of the circular array list.
     * Return the element removed.
     * Throws NoSuchElementException if no elements present.
     */
    public String removeBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("List is empty");
        }
        int idx = realIndex(size - 1);
        String value = data[idx];
        data[idx] = null;
        size--;
        shrinkCapacity();
        return value;
    }


    // Note - for your debugging. We will not test it.
    public void print() {
        if (data == null) {
            System.out.println("[]");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[realIndex(i)]);
        }
        sb.append("]");
        System.out.println(sb.toString());
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
        int idx = realIndex(index);
        String old = data[idx];
        data[idx] = obj;
        return old;
    }

    /*
     * Return true if the element is present.
     */
    public boolean contains(String obj) {
        for (int i = 0; i < size; i++) {
            String curr = data[realIndex(i)];
            if (obj == null ? curr == null : obj.equals(curr)) {
                return true;
            }
        }
        return false;
    }

    private int realIndex(int logicalIndex) {
        int n = data.length;
        int idx = startIndex + logicalIndex;
        if (idx >= n) {
            idx %= n;
        }
        return idx;
    }
}
