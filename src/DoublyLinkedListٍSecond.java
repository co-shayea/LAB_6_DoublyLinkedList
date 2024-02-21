public class DoublyLinkedListٍSecond <E>{
    private Node<E> sentinel;

    // ... other methods

    public DoublyLinkedListٍSecond() {
        sentinel = new Node<>(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    public void addFirst(E data) {
        Node<E> newNode = new Node<>(data);
        newNode.next = sentinel.next;
        newNode.prev = sentinel;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
    }

    public void addLast(E data) {
        Node<E> newNode = new Node<>(data);
        newNode.next = sentinel;
        newNode.prev = sentinel.prev;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
    }
    public void remove(Node<E> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // ... other methods

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        public Node(E data) {
            this.data = data;
        }
    }
}
