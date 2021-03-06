package com.tracenull.j20210120;

import java.util.concurrent.TimeUnit;

/**
 * 为什么要有 AtomicReference ？
 * https://mp.weixin.qq.com/s/TbiC9n9bs6TOSjLL3CtpTg
 */
public class BankCardSyncTest {
    private static volatile BankCard bankCard = new BankCard("cxuan", 100);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (BankCardSyncTest.class) {
                    // 先读取全局的引用
                    final BankCard card = bankCard;
                    // 构造一个新的账户，存入一定数量的钱
                    BankCard newCard = new BankCard(card.getAccountName(), card.getMoney() + 100);
                    System.out.println(newCard);
                    // 最后把新的账户的引用赋给原账户
                    bankCard = newCard;

                    try {
                        TimeUnit.MICROSECONDS.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }
}
