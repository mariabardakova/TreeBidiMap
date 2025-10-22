package ru.vsu.cs.group_6_1.bardakova_m_e.tree;

public interface Map<K, V> {
    void put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean containsKey(K key);
    int size();
    boolean isEmpty();
}