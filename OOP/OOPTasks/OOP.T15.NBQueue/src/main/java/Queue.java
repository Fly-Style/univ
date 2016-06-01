import java.util.concurrent.atomic.AtomicReference;



public class Queue<T> {
	
    private static class Node<E> {
        E value;
        volatile Node<E> next;

        Node(E value) {
            this.value = value;
        }
    }

    private AtomicReference<Node<T>> head, tail;
    
    	Queue() {
        //head and tail point to a dummy node
        Node<T> dummyNode = new Node<>(null);
        head = new AtomicReference<>(dummyNode);
        tail = new AtomicReference<>(dummyNode);
    }

    
    public void putObject(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> prevTailNode = tail.getAndSet(newNode);
        prevTailNode.next = newNode;
    }

    
    public T getObject() {
        Node<T> headNode, valueNode;

        // move head node to the next 
        // while next != null
        do {
            headNode = head.get();
            valueNode = headNode.next;

            // look if other threads modify
        } while (valueNode != null && !head.compareAndSet(headNode, valueNode));

        T value = (valueNode != null ? valueNode.value : null);

        // release the value pointed to by head, keeping the head node dummy
        if (valueNode != null)
            valueNode.value = null;

        return value;
    }
}