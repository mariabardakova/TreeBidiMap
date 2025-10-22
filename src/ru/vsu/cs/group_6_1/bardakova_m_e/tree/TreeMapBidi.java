package ru.vsu.cs.group_6_1.bardakova_m_e.tree;

public class TreeMapBidi<K extends Comparable<K>, V extends Comparable<V>>
        implements BidiMap<K, V> {

    public TreeMap<K, V> forward = new TreeMap<>();
    public TreeMap<V, K> backward = new TreeMap<>();

    @Override
    public void put(K key, V value) {
        if (forward.containsKey(key)) {
            V oldVal = forward.get(key);
            forward.remove(key);
            backward.remove(oldVal);
        }
        if (backward.containsKey(value)) {
            K oldKey = backward.get(value);
            if (forward.containsKey(oldKey)) {
                forward.remove(oldKey);
            }
            backward.remove(value);
        }

        forward.put(key, value);
        backward.put(value, key);
    }

    @Override
    public V get(K key) {
        return forward.get(key);
    }

    @Override
    public V remove(K key) {
        V value = forward.remove(key);
        if (value != null) {
            backward.remove(value);
        }
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        return forward.containsKey(key);
    }

    @Override
    public int size() {
        return forward.size();
    }

    @Override
    public boolean isEmpty() {
        return forward.isEmpty();
    }

    @Override
    public K getKeyByValue(V value) {
        return backward.get(value);
    }

    @Override
    public V removeByValue(V value) {
        K key = backward.remove(value);
        if (key != null) {
            return forward.remove(key);
        }
        return null;
    }

    @Override
    public boolean containsValue(V value) {
        return backward.containsKey(value);
    }
}