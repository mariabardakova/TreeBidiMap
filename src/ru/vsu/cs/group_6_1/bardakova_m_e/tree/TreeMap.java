package ru.vsu.cs.group_6_1.bardakova_m_e.tree;

public class TreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private RBTree<K, V> tree = new RBTree<>();

    @Override
    public void put(K key, V value) {
        tree.insert(key, value);
    }

    @Override
    public V get(K key) {
        return tree.search(key);
    }

    @Override
    public V remove(K key) {
        return tree.delete(key);
    }

    @Override
    public boolean containsKey(K key) {
        return tree.search(key) != null;
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.getRoot() == tree.getNIL();
    }
}