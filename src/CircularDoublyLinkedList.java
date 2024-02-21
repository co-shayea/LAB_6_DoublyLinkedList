public class CircularDoublyLinkedList<E> {

    private Node<E> head;

    public CircularDoublyLinkedList() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void addFirst(E data) {
        Node<E> newNode = new Node<>(data);
        if (isEmpty()) {
            newNode.next = newNode;
            newNode.prev = newNode;
            head = newNode;
        } else {
            newNode.next = head;
            newNode.prev = head.prev;
            head.prev = newNode;
            newNode.prev.next = newNode;
        }
    }

    public void addLast(E data) {
        addFirst(data);
        if (!isEmpty()) {
            head = head.prev;
        }
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<E> removedNode = head;
        if (head == head.next) {
            head = null;
        } else {
            head = head.next;
            head.prev = removedNode.prev;
            removedNode.prev.next = head;
        }
        return removedNode.data;
    }

    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<E> removedNode = head.prev;
        if (head == head.next) {
            head = null;
        } else {
            head.prev = removedNode.prev;
            removedNode.prev.next = head;
        }
        return removedNode.data;
    }

    public void rotate() {
        if (isEmpty() || head == head.next) {
            return;
        }
        head = head.next;
    }

    public void rotateBackward() {
        if (isEmpty() || head == head.next) {
            return;
        }
        head = head.prev;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Empty list";
        }
        Node<E> current = head;
        StringBuilder result = new StringBuilder();
        do {
            result.append(current.data).append(" ");
            current = current.next;
        } while (current != head);
        return result.toString().trim();
    }

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        public Node(E data) {
            this.data = data;
        }
    }

//لقائمة دائرية، مما يعني أن مؤشر next للعقدة الأخيرة يشير مرة أخرى إلى الرأس.
//لا توجد عقد حارسة في هذا التنفيذ، يتم الحفاظ على الدائرية من خلال روابط العقد الفعلية.
}
