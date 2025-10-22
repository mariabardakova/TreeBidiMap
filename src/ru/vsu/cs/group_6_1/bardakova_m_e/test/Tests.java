package ru.vsu.cs.group_6_1.bardakova_m_e.test;

import org.junit.jupiter.api.Test;
import ru.vsu.cs.group_6_1.bardakova_m_e.tree.TreeMap;
import ru.vsu.cs.group_6_1.bardakova_m_e.tree.TreeMapBidi;

import static org.junit.jupiter.api.Assertions.*;

public class Tests{

    @Test
    void treeMap_EmptyMap() {
        TreeMap<String, Integer> map = new TreeMap<>();
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    void treeMap_PutAndGet() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 10);
        map.put("b", 20);
        map.put("c", 30);

        assertEquals(10, map.get("a"));
        assertEquals(20, map.get("b"));
        assertEquals(30, map.get("c"));
        assertNull(map.get("d"));
    }

    @Test
    void treeMap_ContainsKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 10);
        map.put("b", 20);

        assertTrue(map.containsKey("b"));
        assertFalse(map.containsKey("g"));
    }

    @Test
    void treeMap_UpdateValue() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 10);
        map.put("a", 100);

        assertEquals(100, map.get("a"));
        assertEquals(1, map.size());
    }

    @Test
    void treeMap_Remove() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 10);
        map.put("b", 20);

        Integer removed = map.remove("b");
        assertEquals(20, removed);
        assertNull(map.get("b"));
        assertFalse(map.containsKey("b"));
        assertEquals(1, map.size());
    }

    @Test
    void treeMap_RemoveNonExistent() {
        TreeMap<String, Integer> map = new TreeMap<>();
        assertNull(map.remove("n"));
    }

    @Test
    void bidiMap_EmptyMap() {
        TreeMapBidi<String, Integer> map = new TreeMapBidi<>();
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    void bidiMap_BasicOperations() {
        TreeMapBidi<String, Integer> map = new TreeMapBidi<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("g", 3);

        assertEquals(1, map.get("a"));
        assertEquals("b", map.getKeyByValue(2));
        assertTrue(map.containsValue(3));
        assertFalse(map.containsValue(99));
    }

    @Test
    void bidiMap_RemoveByKey() {
        TreeMapBidi<String, Integer> map = new TreeMapBidi<>();
        map.put("a", 1);
        map.put("b", 2);
        map.remove("a");

        assertNull(map.get("a"));
        assertFalse(map.containsValue(1));
        assertEquals(1, map.size());
    }

    @Test
    void bidiMap_RemoveByValue() {
        TreeMapBidi<String, Integer> map = new TreeMapBidi<>();
        map.put("a", 1);
        map.put("b", 2);
        map.removeByValue(2);

        assertNull(map.get("b"));
        assertEquals(1, map.size());
    }

    @Test
    void bidiMap_DuplicateValue() {
        TreeMapBidi<String, Integer> map = new TreeMapBidi<>();
        map.put("a", 100);
        map.put("b", 200);
        assertEquals(2, map.size());
        map.put("g", 100);

        assertFalse(map.containsKey("a"), "Старый ключ 'a' должен быть удалён");
        assertTrue(map.containsKey("g"), "Новый ключ 'g' должен быть добавлен");
        assertEquals("g", map.getKeyByValue(100));
        assertEquals(2, map.size());
    }

    @Test
    void bidiMap_RemoveNonExistentValue() {
        TreeMapBidi<String, Integer> map = new TreeMapBidi<>();
        assertNull(map.removeByValue(999));
    }

    @Test
    void bidiMap_ValueConflictResolution() {
        TreeMapBidi<String, Integer> map = new TreeMapBidi<>();
        map.put("a", 100);
        map.put("b", 200);
        map.put("g", 100);

        assertEquals("g", map.getKeyByValue(100));
        assertNull(map.get("a"));
        assertEquals(200, map.get("b"));
    }
}