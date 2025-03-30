package util;

public final class Loop {

    // 100번 이상의 시도는 비정상으로 간주함
    private static final int MAX_TRY_COUNT = 100_000;

    private Loop() {
    }

    public static void run(final LoopBody body) {
        int tryCount = 0;

        while (body.execute()) {
            if (++tryCount >= MAX_TRY_COUNT) {
                throw new IllegalStateException("[ERROR] 최대 시도 횟수를 초과했습니다.");
            }
        }
    }
}
