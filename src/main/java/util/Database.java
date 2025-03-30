package util;

import java.util.function.Supplier;

public class Database {

    public static void doDatabaseWork(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            System.out.println("데이터베이스 작업에 실패했습니다: " + e.getMessage());
        }
    }

    public static <T> T doDatabaseWorkWithReturn(Supplier<T> runnable) {
        try {
            return runnable.get();
        } catch (Exception e) {
            System.out.println("데이터베이스 작업에 실패했습니다: " + e.getMessage());
        }
        return null;
    }
}
