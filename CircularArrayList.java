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
    }

    public CircularArrayList(String[] arr) {
    }

    public String get(int index) {
        return null;
    }

    private void increaseCapacity() {
    }

    private void shrinkCapacity() {
    }

    /*
     * Adds an element to the back of the circular array list.
     */
    public void add(String value) {
    }

    /*
     * Adds an element to the front of the circular array list.
     */
    public void addFront(String value) {
    }

    /*
     * Adds an element to the specified index. Should shift elements to the right if needed.
     * Throws IndexOutOfBoundsException if index is invalid.
     */
    public void add(int index, String element) {
    }

    /*
     * Removes element at specified index.
     * Should shift elements to the left if needed.
     * Throws IndexOutOfBoundsException if index is invalid.
     * Return removed element.
     */
    public String remove(int index) {
        return null;
    }

    /*
     * Removes first occurrence of element that matches the argument.
     * Return true if element removed, and false otherwise.
     */
    public boolean remove(String obj) {
        return false;
    }

    /*
     * Removes element at the start of the circular array list.
     * Return the element removed.
     * Throws NoSuchElementException if no elements present.
     */
    public String removeFront() {
        return null;
    }

    /*
     * Removes element at the end of the circular array list.
     * Return the element removed.
     * Throws NoSuchElementException if no elements present.
     */
    public String removeBack() {
        return null;
    }


    // Note - for your debugging. We will not test it.
    public void print() {
    }

    /*
     * Replace existing element at specified index with the new value.
     * Return the element replaced, i.e. the old value.
     * Throws IndexOutOfBoundsException if index is out of range.
     */
    public String set(int index, String obj) {
        return null;
    }

    /*
     * Return true if the element is present.
     */
    public boolean contains(String obj) {
        return false;
    }
}
