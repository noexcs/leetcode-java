package org.noexcs.basic.algo;

import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


class Buffer {

    ReentrantLock lock;

    Condition providerWait;
    Condition consumerWait;

    int capacity;
    ArrayDeque<Integer> container;

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        providerWait = this.lock.newCondition();
        consumerWait = this.lock.newCondition();
        container = new ArrayDeque<>();
    }

    public void submit(int val) {
        lock.lock();
        try {
            while (container.size() >= capacity) {
                providerWait.await();
            }
            container.addLast(val);
            System.out.println("已生成：" + val);
            consumerWait.signal();
        } catch (InterruptedException e) {
            System.out.println("提交被打断");
        } finally {
            lock.unlock();
        }
    }

    public int consume() {
        lock.lock();
        try {
            // 缓存区为空等待
            while (container.isEmpty()) {
                consumerWait.await();
            }
            // 取值
            int val = container.pollFirst();
            System.out.println("消费到：" + val);
            // 提醒provider
            providerWait.signal();
            return val;
        } catch (InterruptedException e) {
            System.out.println("消费被打断");
            return -1;
        } finally {
            lock.unlock();
        }
    }
}


class Provider {


    private Buffer buffer = null;

    public Provider(Buffer buffer) {
        this.buffer = buffer;
    }

    public void provide() {
        for (int i = 1; i <= 30; i++) {
            buffer.submit(i);
        }
    }
}

class Consumer {
    private Buffer buffer = null;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void consume() {
        while (true) {
            int v = buffer.consume();
        }
    }
}


public class ConsumerProvider {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(10);
        Provider p = new Provider(buffer);
        Consumer c = new Consumer(buffer);

        Thread t1 = new Thread(() -> {
            p.provide();
        });

        Thread t2 = new Thread(() -> {
            c.consume();
        });

        t1.start();
        t2.start();

        new Scanner(System.in).next();
    }
}
