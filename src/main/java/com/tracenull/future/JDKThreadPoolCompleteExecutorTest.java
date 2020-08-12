package com.tracenull.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class JDKThreadPoolCompleteExecutorTest {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-女神：我开始化妆了，好了我叫你。");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "化妆完毕了。";
        });

        completableFuture.whenComplete((returnStr, exception) -> {
            if (exception == null) {
                System.out.println(Thread.currentThread().getName() + returnStr);
            } else {
                System.out.println(Thread.currentThread().getName() + "女神放你鸽子了。");
                exception.printStackTrace();
            }
        });

        System.out.println(Thread.currentThread().getName() + "-等女神化妆的时候可以干点自己的事情。");
        Thread.currentThread().join();
    }
}
