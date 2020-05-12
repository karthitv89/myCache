package com.karthik.cache;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */

public class LeastRecentUsedCacheTest
{
    private static LeastRecentUsedCache cacheApplication;
    /**
     * Rigorous Test :-)
     */

    private class Person {
        private String fName;
        private String lName;

        Person(String fName, String lName) {
            this.fName = fName;
            this.lName = lName;
        }

        public String getfName() {
            return fName;
        }

        public void setfName(String fName) {
            this.fName = fName;
        }
    }
    @BeforeClass
    public static void initialize() {
        cacheApplication = new LeastRecentUsedCache(5);
    }

    public LeastRecentUsedCacheTest () {
    }

    @Before
    public void loadData() {
        String str1 = "str1";
        String str2 = "str2";
        String list1 = "list1";
        String list2 = "list2";
        String map1 = "map1";
        String set1 = "set1";
        String obj1 = "obj1";

        List<String> l1 = Arrays.asList(new String[]{"A","B","C","A"});
        HashSet<String> s1 = new HashSet<String>(Arrays.asList(new String[]{"A","B","C","A", "E", "D"}));
        List<String> l2 = Arrays.asList(new String[]{"A","B","C","A", "D", "A", "E"});

        Person p1 = new Person("K", "T");
        Person p2 = new Person("S", "K");
        List<Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);

        cacheApplication.put(str1, "Karthik");
        cacheApplication.put(str2, "Suka");
        cacheApplication.put(list1, l1);
        cacheApplication.put(list2, personList);
        cacheApplication.put(set1, s1);
        cacheApplication.put(list2, l2);

    }
    @Test
    public void get() {

        List<String> stringList = (List<String>) cacheApplication.get("list2");
        assertEquals(7, stringList.size());
    }
    @Test
    public void put()
    {
        System.out.println("current cache size limit:" + cacheApplication.getCacheSizeLimit());
        System.out.println("current cache size:" + cacheApplication.getCacheSizeLimit());

        assertEquals(5, cacheApplication.getCacheSize());
    }
    @Test
    public void remove() {
        cacheApplication.remove("list1");
        assertEquals( 4, cacheApplication.getCacheSize());
    }

    @Test
    public void removeAll() {
        cacheApplication.removeAll();
        assertEquals( 0, cacheApplication.getCacheSize());
    }

}
