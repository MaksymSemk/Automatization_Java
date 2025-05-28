
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

public class LinkedListTester {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> list = new LinkedList<>();

        while (true) {
            System.out.println("\n--- LinkedList Menu ---");
            System.out.println("1. Add element at index");
            System.out.println("2. Add element at first");
            System.out.println("3. Add element at last");
            System.out.println("4. Remove element at index");
            System.out.println("5. Remove first element");
            System.out.println("6. Remove last element");
            System.out.println("7. Get element at index");
            System.out.println("8. Get first element");
            System.out.println("9. Get last element");
            System.out.println("10. Get size of LinkedList");
            System.out.println("11. Clear LinkedList");
            System.out.println("12. Print all elements"); // NEW OPTION
            System.out.println("13. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (choice) {
                    case 1: // Add element at index
                        System.out.print("Enter index: ");
                        int addIndex = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter element: ");
                        String addElem = scanner.nextLine();
                        list.add(addIndex, addElem);
                        System.out.println("Element added.");
                        break;

                    case 2: // Add first element
                        System.out.print("Enter element: ");
                        String addFirstElem = scanner.nextLine();
                        list.addFirst(addFirstElem);
                        System.out.println("Element added at first.");
                        break;

                    case 3: // Add last element
                        System.out.print("Enter element: ");
                        String addLastElem = scanner.nextLine();
                        list.addLast(addLastElem);
                        System.out.println("Element added at last.");
                        break;

                    case 4: // Remove element at index
                        System.out.print("Enter index to remove: ");
                        int removeIndex = scanner.nextInt();
                        System.out.println("Removed: " + list.remove(removeIndex));
                        break;

                    case 5: // Remove first element
                        System.out.println("Removed first: " + list.removeFirst());
                        break;

                    case 6: // Remove last element
                        System.out.println("Removed last: " + list.removeLast());
                        break;

                    case 7: // Get element at index
                        System.out.print("Enter index: ");
                        int getIndex = scanner.nextInt();
                        System.out.println("Element at index " + getIndex + ": " + list.get(getIndex));
                        break;

                    case 8: // Get first element
                        System.out.println("First element: " + list.getFirst());
                        break;

                    case 9: // Get last element
                        System.out.println("Last element: " + list.getLast());
                        break;

                    case 10: // Get size of LinkedList
                        System.out.println("Size of LinkedList: " + list.size());
                        break;

                    case 11: // Clear LinkedList
                        list.clear();
                        System.out.println("LinkedList cleared.");
                        break;

                    case 12: // Print all elements
                        printAllElements(list);
                        break;

                    case 13: // Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printAllElements(LinkedList<String> list) {
        if (list.size() == 0) {
            System.out.println("LinkedList is empty.");
            return;
        }
        System.out.println("LinkedList elements:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("[" + i + "] " + list.get(i)+"->");
        }
    }
}
class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;

    private class Node<T>{

        T item=null;
        Node<T> previousNode=null;
        Node<T> nextNode=null;

        Node(T item, Node<T> previousNode, Node<T> nextNode){
            this.item=item;
            this.previousNode=previousNode;
            this.nextNode=nextNode;
        }

    }


    public LinkedList(){
        head=null;
        tail=null;
    }

    T get(int index) {
        if(index>=size() || index<0) throw new NoSuchElementException();
        Node<T> currNode=tail;
        for(int i=0; i<index; i++){
            currNode=currNode.nextNode;
        }
        return currNode.item;
    }// отримання елементу по індексу
    T getFirst(){
        return get(0);
    }// отримання першого елементу
    T getLast(){
        if(head==null) throw new NoSuchElementException();
        return head.item;
    }//отримання останнього елементу
    T remove(int index){
        if(index>=size() || index<0)  throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        if(index==size()-1){
            T item= head.item;
            head=head.previousNode;
            return item;
        }
        Node<T> currNode=tail;
        for(int i=0; i<index; i++){
            currNode=currNode.nextNode;
        }
        Node<T> prevNode=currNode.previousNode;
        Node<T> nextNode=currNode.nextNode;

        nextNode.previousNode=prevNode;
        prevNode.nextNode=nextNode;

        return currNode.item;
    }
    T removeFirst(){
        if(tail==null) throw new NoSuchElementException();
        T item= tail.item;
        if (tail.nextNode==null) {
            tail=null;
            head=null;
        }
        else {
            Node<T> nextNode=tail.nextNode;
            nextNode.previousNode=null;
            tail=nextNode;
        }
        return item;
    }
    T removeLast(){
        if(head==null) throw new NoSuchElementException();
        T item= head.item;
        if (head.previousNode==null) {
            tail=null;
            head=null;
        }
        else {
            Node<T> prevNode=head.previousNode;
            prevNode.nextNode=null;
            head=prevNode;
        }
        return item;
    }//  при видаленні повертаємо видалений елемент
    void add(int index, T elem){
        if(index>size() || index<0) throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        else {
            if(tail==null){
                tail=new Node<>(elem, null, null);
                head=tail;
                return;
            }
            if (index == 0) {
                Node<T> oldTail=tail;
                tail = new Node<>(elem, null, oldTail);
                oldTail.previousNode=tail;
                //if(oldTail.nextNode==null) head=oldTail;
                return;
            }
            if (index==size()){
                Node<T> oldHead=head;
                head = new Node<>(elem, oldHead, null);
                oldHead.nextNode=head;
                //if(oldHead.previousNode==null) tail=oldHead;
                return;
            }
            Node<T> currNode = tail;
            for (int i = 1; i < index; i++) {
                currNode = currNode.nextNode;
            }

            Node<T> prevNode=currNode;
            Node<T> nextNode=currNode.nextNode;

            Node<T> newNode = new Node<>(elem, prevNode, nextNode);
            prevNode.nextNode=newNode;
            if(nextNode!=null) nextNode.previousNode=newNode;
        }
    }
    void addFirst(T elem){
        add(0, elem);
    }
    void addLast(T elem){
        add(size(),elem);
    }

    void add(int index, Supplier<T> elemSupplier) {
        if(index>size() && index>-1) throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        T itemToCheck= elemSupplier.get();

        if(!itemToCheck.equals(get(index))) add(index, itemToCheck);

    }
    // метод який прийматиме саплаєр у вигляді лямбди, виконує перевірку чи існує елемент по індексу,
    // якщо існує то нічого не відбудеться, якщо не існує то elemSupplier виконається і результат буде додано
    // по вказаному індексу.
    int size() {
        if(head==null) return 0;
        int counter=1;
        Node<T> currNode=head;
        while(currNode.previousNode!=null){
            counter++;
            currNode=currNode.previousNode;
        }
        return counter;
    }// кількість елементів
    void clear() {
        head=null;
        tail=null;
    }// очистка списку

    private String outOfBoundsMsg(int index){
        return "Index " + index + " is out of bounds. LinkedList size: "
                + size();
    }
}