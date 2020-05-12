package com.karthik.cache;

import java.util.HashMap;

public class LeastRecentUsedCache {
    private int cacheSizeLimit;
    private HashMap<String,Node> map;
    private Node startNode;
    private Node endNode;


    private class Node {
        Object value;
        String key;
        Node leftNode;
        Node rightNode;

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", key='" + key + '}' ;
        }
    }

    public LeastRecentUsedCache(int cacheSizeLimit) {
        this.cacheSizeLimit = cacheSizeLimit;
        map = new HashMap<>(cacheSizeLimit);
    }

    public synchronized int getCacheSizeLimit() {
        return cacheSizeLimit;
    }

    public synchronized int getCacheSize() {
        return map.size();
    }
    public Object get(String key) {
        synchronized (map) {
            if (map.containsKey(key)) // Key Already Exist, just update the
            {
                Node node = map.get(key);
                removeNode(node);
                addFirst(node);
                return node.value;
            }
            return null;
        }
    }

    public synchronized void removeAll() {
        deleteNode(startNode);
        map.clear();
    }

    private Node deleteNode(Node node) {
        if(node != null) {
            if (node.rightNode != null) {
                node.leftNode = null;
                node.key = null;
                node.value = null;
                return node.rightNode;
            }
            node.leftNode = null;
        }
        return null;
    }
    public void put(String key, Object value) {
        synchronized (map) {
            if (map.containsKey(key)) // Key exists, just update the value and move it to top
            {
                Node node = map.get(key);
                node.value = value;
                removeNode(node);
                addFirst(node);
            } else {
                Node freshNode = new Node();
                freshNode.leftNode = null;
                freshNode.rightNode = null;
                freshNode.value = value;
                freshNode.key = key;
                if (map.size() >= cacheSizeLimit) // We have reached maximum size so need to make room for new element.
                {
                    map.remove(endNode.key);
                    removeNode(endNode);
                    addFirst(freshNode);

                } else {
                    addFirst(freshNode);
                }

                map.put(key, freshNode);
            }
        }
    }
    public boolean remove(String key) {
        synchronized (map) {
            if (map.containsKey(key)) // Key exists, just update the value and move it to top
            {
                removeNode(map.get(key));
                map.remove(key);
                return  true;
            } else {
                return false;
            }
        }
    }

    private void addFirst(Node node) {
        node.rightNode = startNode; // Adding in the first place by adding in front of startNode
        node.leftNode = null; // MAke leftNode null to make this node as startNode
        if (startNode != null) { // if node is not first entry, put node as leftNode in the current startNode.
            startNode.leftNode = node;
        }
        startNode = node; // Assign node as the startNode
        if (endNode == null) { //For firstNode
            endNode = startNode;
        }
    }



    private void removeNode(Node node) {

        if (node.leftNode != null) { // If node is not the firstNode
            node.leftNode.rightNode = node.rightNode; // Link the next node with prev node, to remove this node
        } else { // if it is the firstNode
            startNode = node.rightNode;
        }

        if (node.rightNode != null) { // if node is not the last node
            node.rightNode.leftNode = node.leftNode;  // Link the next node with prev node, to remove this node
        } else { // if it is the lastNode
            endNode = node.leftNode;
        }
    }
}
