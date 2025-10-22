package ru.vsu.cs.group_6_1.bardakova_m_e.tree;
import java.util.*;

public class RBTree<K extends Comparable<K>, V> {

    static class Node<K extends Comparable<K>, V> {
        K key;
        V value;
        Node<K, V> left, right, parent;
        boolean color;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.color = true;
        }
    }

    private final Node<K, V> NIL;

    private Node<K, V> root;

    private int size = 0;

    public RBTree() {
        NIL = new Node<>(null, null);
        NIL.color = false;
        NIL.left = NIL.right = NIL.parent = NIL;
        root = NIL;
    }

    public void insert(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        newNode.left = NIL;
        newNode.right = NIL;

        Node<K, V> y = NIL;
        Node<K, V> x = root;

        while (x != NIL) {
            y = x;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                x.value = value;
                return;
            }
        }
        newNode.parent = y;
        if (y == NIL) {
            root = newNode;
        } else if (key.compareTo(y.key) < 0) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }

        size++;
        fixInsert(newNode);
    }

    public V search(K key) {
        Node<K, V> node = searchRec(root, key);
        return (node == NIL) ? null : node.value;
    }

    public V delete(K key) {
        Node<K, V> z = searchRec(root, key);
        if (z == NIL) {
            return null;
        }

        V oldValue = z.value;
        deleteNode(z);
        size--;
        return oldValue;
    }

    private Node<K, V> searchRec(Node<K, V> node, K key) {
        if (node == NIL || key.compareTo(node.key) == 0) {
            return node;
        }
        if (key.compareTo(node.key) < 0) {
            return searchRec(node.left, key);
        } else {
            return searchRec(node.right, key);
        }
    }

    private void rotateLeft(Node<K, V> x) {
        Node<K, V> y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node<K, V> y) {
        Node<K, V> x = y.left;
        y.left = x.right;
        if (x.right != NIL) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == NIL) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
    }

    private void fixInsert(Node<K, V> z) {
        while (z.parent.color) {
            if (z.parent == z.parent.parent.left) {
                Node<K, V> y = z.parent.parent.right;
                if (y.color) {
                    z.parent.color = false;
                    y.color = false;
                    z.parent.parent.color = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        rotateLeft(z);
                    }
                    z.parent.color = false;
                    z.parent.parent.color = true;
                    rotateRight(z.parent.parent);
                }
            } else {
                Node<K, V> y = z.parent.parent.left;
                if (y.color) {
                    z.parent.color = false;
                    y.color = false;
                    z.parent.parent.color = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = false;
                    z.parent.parent.color = true;
                    rotateLeft(z.parent.parent);
                }
            }
        }
        root.color = false;
    }

    private void deleteNode(Node<K, V> z) {
        Node<K, V> y = z;
        Node<K, V> x;
        boolean yOriginalColor = y.color;

        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (!yOriginalColor) {
            fixDelete(x);
        }
    }

    private void transplant(Node<K, V> u, Node<K, V> v) {
        if (u.parent == NIL) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private Node<K, V> minimum(Node<K, V> node) {
        while (node.left != NIL) {
            node = node.left;
        }
        return node;
    }

    private void fixDelete(Node<K, V> x) {
        while (x != root && !x.color) {
            if (x == x.parent.left) {
                Node<K, V> w = x.parent.right;
                if (w.color) {
                    w.color = false;
                    x.parent.color = true;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (!w.left.color && !w.right.color) {
                    w.color = true;
                    x = x.parent;
                } else {
                    if (!w.right.color) {
                        w.left.color = false;
                        w.color = true;
                        rotateRight(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = false;
                    w.right.color = false;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                Node<K, V> w = x.parent.left;
                if (w.color) {
                    w.color = false;
                    x.parent.color = true;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (!w.right.color && !w.left.color) {
                    w.color = true;
                    x = x.parent;
                } else {
                    if (!w.left.color) {
                        w.right.color = false;
                        w.color = true;
                        rotateLeft(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = false;
                    w.left.color = false;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = false;
    }

    public Node<K, V> getRoot() {
        return root;
    }

    public Node<K, V> getNIL() {
        return NIL;
    }

    public int size() {
        return size;
    }

}