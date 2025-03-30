package util;

@FunctionalInterface
public interface LoopBody {
    /**
     * 루프의 한 번 실행 후 계속 실행할지 여부를 반환합니다.
     *
     * @return true면 루프를 계속, false면 종료
     */
    boolean execute();
}
