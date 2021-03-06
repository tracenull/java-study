package com.tracenull.j20200914;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * https://juejin.im/post/6844903665241686029
 */
public class WeakReferenceTest {
    // 弱引用队列
    private final static ReferenceQueue<GCTarget> REFERENCE_QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        LinkedList<GCTargetWeakReference> gcTargetList = new LinkedList<>();
        // 创建弱引用的对象，依次加入链表中
        for (int i = 0; i < 5; i++) {
            GCTarget gcTarget = new GCTarget(String.valueOf(i));
            GCTargetWeakReference weakReference = new GCTargetWeakReference(gcTarget, REFERENCE_QUEUE);

            gcTargetList.add(weakReference);
            System.out.println("Just created GCTargetWeakReference obj: " +
                    gcTargetList.getLast());
        }

        // 通知gc进行垃圾回收
        System.gc();
        try {
            // 休息几分钟，等待上面的垃圾回收线程运行完成
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 检查关联的引用队列是否为空
        Reference<? extends GCTarget> reference;
        while ((reference = REFERENCE_QUEUE.poll()) != null) {
            if (reference instanceof GCTargetWeakReference) {
                System.out.println("In queue,id is" + ((GCTargetWeakReference) (reference)).id);
            }
        }
    }
}

class GCTarget {
    // 对象id
    public String id;
    // 占用内存空间
    byte[] buffer = new byte[1024];

    public GCTarget(String id) {
        this.id = id;
    }

    @Override
    protected void finalize() throws Throwable {
        // 执行垃圾回收时打印显示对象ID
        System.out.println("Finalizing GCTarget, id is : " + id);
    }
}

class GCTargetWeakReference extends WeakReference<GCTarget> {
    // 弱引用的ID
    public String id;

    public GCTargetWeakReference(GCTarget gcTarget, ReferenceQueue<? super GCTarget> queue) {
        super(gcTarget, queue);
        this.id = gcTarget.id;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing GCTargetWeakReference " + id);
    }
}
