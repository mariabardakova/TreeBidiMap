package ru.vsu.cs.group_6_1.bardakova_m_e.tree;

public interface BidiMap<K, V> extends Map<K, V> {
    K getKeyByValue(V value);
    V removeByValue(V value);
    boolean containsValue(V value);
}