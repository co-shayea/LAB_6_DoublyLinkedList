import java.util.Objects;

public class DoublyLinkedList <E> {
    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, null, null);
        header.setNext(trailer);
    }

    //    public int size(){
//        return size;
//    }
    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (isEmpty()) return null;
        return header.getNext().getElement();
    }

    public E last() {
        if (isEmpty()) return null;
        return trailer.getPrev().getElement();
    }

    private void addBetween(E e, Node<E> p, Node<E> n) {
        Node<E> newest = new Node<>(e, p, n);
        p.setNext(newest);
        n.setPrev(newest);
        size++;
    }

    public void addFirst(E ee) {
        addBetween(ee, header, header.next);
    }

    public void addlast(E ee) {
        addBetween(ee, trailer.prev, trailer);
    }

    private E remove(Node<E> x) {
        Node<E> p = x.prev;
        Node<E> n = x.next;
        p.setNext(n);
        n.setPrev(p);
        size--;
        return x.element;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        return remove(header.next);
    }

    public E removeLast() {
        if (isEmpty()) return null;
        return remove(trailer.prev);
    }

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>****************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//      Q1.  Describe a method for finding the middle node of a doubly linked list
// with header and trailer sentinels by “link hopping,” and without relying on
// explicit knowledge of the size of the list. In the case of an even number of nodes,
// report the node slightly left of center as the “middle.”

    public Node<E> findMiddleNode() {
        Node<E> slow = header;
        Node<E> fast = header;

        while (fast != trailer && fast.next != trailer) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
// الشرح
//
//    ابدأ بمؤشرين slow و fast يشيران إلى عقدة الحارس (header sentinel).
//    التكرار:
//        حرك slow بمقدار خطوة واحدة للأمام في كل تكرار.
//        حرك fast بمقدار خطوتين للأمام في كل تكرار.
//    شرط التوقف:
// عندما يصل fast إلى عقدة الحارس (trailer sentinel) أو إلى العقدة التالية له، فإن slow سيشير إلى العقدة الوسطى (أو العقدة اليسرى قليلاً من الوسط في حالة وجود عدد زوجي من العقد)

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>****************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//    Q2.    Give an implementation of the size( ) method for the DoublyLinkedList class,
//    assuming that we did not maintain size as an instance variable

    public int size() {
        if (header == null) {
            return 0; // القائمة فارغة
        }

        int counting = 1;
        Node<E> currentElement = header;

        // استمر في التكرار حتى تصل إلى العقدة الأخيرة (tail)
        while (currentElement != trailer) {
            currentElement = currentElement.next;
            counting++;
        }

        return counting;
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>****************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//    Q3.   Implement the equals( ) method for the DoublyLinkedList class.


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoublyLinkedList<E> that = (DoublyLinkedList<E>) o;

        // تحقق من القوائم الفارغة
        if (header == null && that.header == null) {
            return true; // كلتا القائمتين فارغتين
        }

        // تحقق إذا كانت أي قائمة فارغة والأخرى ليست كذلك
        if (header == null || that.header == null) {
            return false;
        }

        Node<E> current1 = header;
        Node<E> current2 = that.header;

        // استمر في التكرار حتى تصل إلى إحدى نهايتي القائمتين
        while (current1 != null && current2 != null) {
            if (!current1.element.equals(current2.element)) {
                return false; // عدم تطابق البيانات
            }
            current1 = current1.next;
            current2 = current2.next;
        }

        // تحقق من طول القائمتين (يجب أن يكونا متساويين)
        return current1 == null && current2 == null;
    }

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>****************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //    Q4.   Give an algorithm for concatenating two doubly linked lists L and M,
//    with header and trailer sentinel nodes, into a single list L′
//
//    خوارزمية لتجميع قائمتين مرتبطتين ثنائيتين مع عقدتي حارس رأس وذيل:
//
//    تحقق من الحالات الخاصة:
//        إذا كانت كلتا القائمتين L و M فارغتين، فلا يوجد شيء لتجميعه، أعد L فارغة.
//        إذا كانت إحداهما فقط فارغة، فانسخ القائمة غير الفارغة إلى L وأعد M فارغة.
//
//    ابحث عن عقدة الحارس للذيل في القائمة L (عقدة الحارس الأخيرة):
//        استخدم حلقة للتنقل عبر L حتى تصل إلى عقدة الحارس للذيل، والتي تشير إلى العقدة الأخيرة الفعلية في القائمة.
//
//    قم بتوصيل رأس القائمة M بعقدة الحارس للذيل في L:
//        ضبط next لعقدة الحارس للذيل في L لتشير إلى رأس القائمة M.
//
//    قم بتوصيل عقدة الحارس للذيل في M بعقدة الحارس للرأس في L:
//        ضبط next لعقدة الحارس للذيل في M لتشير إلى عقدة الحارس للرأس في L.
//
//    قم بتحديث عقدة الحارس للذيل في القائمة المجمعة:
//        ضبط next لعقدة الحارس للذيل في M (الذي يشير الآن إلى عقدة الحارس للرأس في L) لتشير إلى عقدة الحارس للذيل في القائمة المجمعة.
//
//مثال:
//
//لنفترض أن L = {1, 2, 3} و M = {4, 5, 6}.
//
//    L ليست فارغة و M ليست فارغة.
//    عقدة الحارس للذيل في L تشير إلى العقدة التي تحتوي على القيمة 3.
//    يتم ضبط next لعقدة الحارس للذيل في L لتشير إلى رأس القائمة M (العقدة التي تحتوي على القيمة 4).
//    يتم ضبط next لعقدة الحارس للذيل في M (العقدة التي تحتوي على القيمة 6) لتشير إلى عقدة الحارس للرأس في L (العقدة التي تحتوي على القيمة null).
//    يتم ضبط next لعقدة الحارس للذيل في M (الذي يشير الآن إلى عقدة الحارس للرأس في L) لتشير إلى نفسها، مما يغلق الدورة للقائمة المجمعة.
//
//بعد إجراء هذه الخطوات، تصبح القائمة المجمعة L′: {1, 2, 3, 4, 5, 6}
//
    public void concatenate(DoublyLinkedList<E> other) {
        if (isEmpty() && other.isEmpty()) {
            return; // لا يوجد شيء لتجميعه
        }

        if (isEmpty()) {
            header = other.header;
            trailer = other.trailer;
            return;
        }

        if (other.isEmpty()) {
            return; // لا يوجد شيء لتجميعه
        }

        // ابحث عن عقدة الحارس للذيل في L
        Node<E> current = header;
        while (current.next != trailer) {
            current = current.next;
        }

        // قم بتوصيل رأس M بعقدة الحارس للذيل في L
        current.next = other.header;

        // قم بتوصيل ذيل M برأس L
        other.trailer.next = header;

        // قم بتحديث ذيل L
        trailer = other.trailer;
    }

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>****************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//    Q5.  Our implementation of a doubly linked list relies on two sentinel nodes,
//    header and trailer, but a single sentinel node that guards both ends of the list should suffice.
//    Reimplement the DoublyLinkedList class using only one sentinel node.

//    الحل في كلاس جديد اسمه DoublyLinkedListٍSecond

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>****************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//    Q6.   Implement a circular version of a doubly linked list, without any sentinels,
//    that supports all the public behaviors of the original as well as two new update methods, rotate( ) and rotateBackward.

//    الحل في كلاس جديد اسمه CircularDoublyLinkedList

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>****************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//    Q7.   Implement the clone( ) method for the DoublyLinkedList class.

    @Override
    public DoublyLinkedList<E> clone() throws CloneNotSupportedException {
        DoublyLinkedList<E> other = new DoublyLinkedList<>(); // إنشاء قائمة جديدة فارغة

        // تحقق من القائمة الفارغة
        if (header == null) {
            return other; // أعد القائمة الجديدة فارغة
        }

        // استخدم مؤشر للتنقل عبر القائمة الأصلية
        Node<E> current = header;

        // استمر في التكرار حتى تصل إلى نهاية القائمة
        while (current != null) {
            // أضف نسخة من البيانات الحالية إلى القائمة الجديدة
            other.addlast(current.element);
            current = current.next;
        }

        return other;
    }
//        return super.clone();

    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
}







