
package com.bradboughn.rain.util;

import java.util.Iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> implements Iterable<T>
{

    private Node head, tail;
    private int size;
    
    public DoublyLinkedList()
    {
        head = null;
        tail = null;
        size = 0;
    }
    
    public void addToBeginning(T obj)
    {
        Node temp = new Node(obj);
        if (isEmpty())
        {
            head = temp;
            tail = head;
        } 
        else
        {
            temp.next = head;
            head.previous = temp;
            head = temp;
        }
        size++;
    }
    
    public void add(T obj)
    {
        Node temp = new Node(obj);
        if (head == null)
        {
            head = temp;
            tail = head;
        }
        else
        {
            tail.next = temp;
            temp.previous = tail;
            tail = temp;
        }
        size++;
    }
    
    public void add(T obj, int index)
    {
        Node temp = new Node(obj);
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        else if (index == 0)
        {
            addToBeginning(obj);
        }
        else if (index == size -1)
        {
            add(obj);
        }
        else
        {
            Node current = head;
            //Start at head, and iterate thru, till Node directly BEFORE target index
            for (int i = 0; i < index; i++)
            {
                current = current.next;
            }
            //Current now is at target index
            Node previous = current.previous;
            previous.next = temp;
            temp.previous = previous;
            temp.next = current;
            current.previous = temp;
            size++;
        }
    }
    
    public T get(int index)
    {
        //could add optimized search method:
        //if index is past halfway point of list, search backwards for it, otherwise search forwards
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        else
        {
            Node current = head;
            for (int i = 0; i < index; i++)
            {
                current = current.next;
            }
            return current.data;
        }
    }
    
    public void remove(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        else if (index == 0)
        {
            head = head.next;
            head.previous = null;
            size--;
        }
        else if (index == size -1)
        {
            tail = tail.previous;
            tail.next = null;
            size--;
        }
        else 
        {
            Node current = head;
            for (int i = 0; i < index; i++)
            {
                current = current.next;
            }
            Node previous = current.previous;
            Node next = current.next;
            previous.next = next;
            next.previous = previous;
            size--;
        }
    }
    
    public void clear()
    {
        Node current = head;
        head = null;
        tail = null;
        while (current != null)
        {
            Node next = current.next;
            current.next = null;
            current.previous = null;
            
            current = next;
            size--;
        }
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public void print()
    {
        if (isEmpty()) return;
        
        Node temp = head;
        while (temp != null)
        {
            System.out.print(temp.data + "-->");
            temp = temp.next;
        }
        System.out.print("null");
    }
    
    public int size()
    {
        return size;
    }
    
    public Iterator<T> iterator()
    {
        return new ListIterator();
    }
    
    //Beginning of inner class : Node
    public class Node 
    {
        private T data;
        private Node previous, next;

        public Node(T value)
        {
            previous = null;
            next = null;
            this.data = value;
        }

        public T getValue()
        {
            return data;
        }
    }
    //End of inner class : Node

    //Beginning of inner class : ListIterator
    public class ListIterator implements Iterator<T>
    {
        public Node current = null;
        @Override
        public boolean hasNext()
        {
            if (current == null && head != null) return true; //current is null, but head is not: is true
            else if (current != null) return current.next != null; //if current has a next Node: is true
            return false;
        }

        @Override
        public T next()
        {
            if (current == null && head != null) //first time through, where current = null, need to assign it
            {
                current = head;
                return head.data;
            }
            else if (current != null) //current has Node data now
            {
                current = current.next;
                return current.data;
            }
            
            throw new NoSuchElementException();
        }
    }
    //End of inner class : ListIterator

    
}


