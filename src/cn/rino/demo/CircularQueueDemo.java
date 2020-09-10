package cn.rino.demo;

import java.util.Scanner;

/**
 * @description 使用数组实现环形队列Demo
 * @date 2020/9/10 15:12
 */
public class CircularQueueDemo {
    public static void main(String[] args) {
        // 测试
        System.out.println("测试数组模拟环形队列");
        // 创建一个队列
        CircularQueue arrayQueue = new CircularQueue(4);
        char key = ' '; // 接受用户输入
        Scanner scanner = new Scanner(System.in); // 接收
        boolean loop = true;
        // 输出一个菜单
        System.out.println("s(show):显示队列");
        System.out.println("e(exit):退出程序");
        System.out.println("a(add):添加数据");
        System.out.println("g(get):从队列中取出数据");
        while (loop) {
            System.out.println("请输入操作符：");
            key = scanner.next().charAt(0); // 接受一个字符
            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数字");
                    int val = scanner.nextInt();
                    arrayQueue.addQueue(val);
                    break;
                case 'g': // 取出数据
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的是%d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e': // 退出
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }
}

class CircularQueue {
    int front; // 队头
    int rear; // 队尾
    int maxSize; // 最大数量
    int[] array; // 数组

    /**
     * 初始化队列
     *
     * @param maxSize
     */
    public CircularQueue(int maxSize) {
        this.maxSize = maxSize;
        this.front = 0;
        this.rear = 0;
        array = new int[maxSize];
    }

    /**
     * 判断队列是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return front == rear;
    }

    /**
     * 判断队列是否满 保证从队头开始到队尾等于最大数量则为满
     * rear+1：原因是如果当front和rear都为0时即满足isFull条件也满足isEmpty条件
     * 所以+1是为了防止条件冲突，预留一个空位
     *
     * @return
     */
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    /**
     * 加入队列 从队尾开始添加 rear往后移动
     *
     * @param value
     */
    public void addQueue(int value) {
        if (isFull()) {
            System.out.println("队列已满！");
            return;
        }
        array[rear] = value;
        rear = (rear + 1) % maxSize;
        System.out.println("添加成功");
    }

    /**
     * 取出队列 从队头开始取 font往后移动
     */
    public int getQueue() throws Exception {
        if (isEmpty()) {
            throw new RuntimeException("队列为空！");
        }
        int value = array[front];
        front = (front + 1) % maxSize;
        return value;
    }

    /**
     * 计算队列中的有效数据个数
     * 拆分理解 (rear - front + maxSize) % maxSize = rear % maxSize + front % maxSize + maxSize % maxSize
     * rear%maxSize：取实际在数组内有效下标 front同理 (rear-front)%maxSize +
     * 1则为列尾和列首的距离，+1是因为(rear-front)%maxSize是它们间的距离,实际+1才是有效个数
     *
     * @return
     */
    public int getSize() {
        return (rear - front + maxSize) % maxSize;
    }

    /**
     * 输出队列
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空！");
            return;
        }
        for (int i = front; i < front + getSize(); i++) {
            // 取值时需要下标%maxSize,是因为如果发生先全添加后取出再添加不取出时，front会大于数组最大下标，导致异常
            System.out.printf("array[%d]：%d\n", i % maxSize, array[i % maxSize]);
        }
    }
}
