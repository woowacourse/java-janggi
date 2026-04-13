package domain.board.context;

public class BoardIdContext {

    private BoardIdContext() {
    }

    private static final ThreadLocal<Long> BOARD_ID_THREAD_LOCAL = new ThreadLocal<>();

    public static void setBoardId(Long boardId) {
        BOARD_ID_THREAD_LOCAL.set(boardId);
    }

    public static Long getBoardId() {
        return BOARD_ID_THREAD_LOCAL.get();
    }

    public static void clear() {
        BOARD_ID_THREAD_LOCAL.remove();
    }

}
