package com.irm.bendan.util;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author wxl
 * @version 1.0
 * @description:
 * @date 2021/12/22 19:38
 */
public class test {
    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);

        System.out.println(myStack.top());
        System.out.println(myStack.pop());


    }
}
class MyStack {

    Queue<Integer> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.offer(x);
    }

    public int pop() {
        int size = queue.size();
        size--;
        while(size-- > 0){
            queue.offer(queue.peek());
            queue.poll();
        }
        return queue.poll();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
