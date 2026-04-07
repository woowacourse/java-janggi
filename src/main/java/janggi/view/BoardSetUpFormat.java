package janggi.view;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.board.setup.InElephantSetUp;

public enum BoardSetUpFormat {
    LEFT_ELEPHANT(1, "왼상차림 (상마상마)", new InElephantSetUp()),
    RIGHT_ELEPHANT(2, "오른상차림 (마상마상)", new InElephantSetUp()),
    IN_ELEPHANT(3, "안상차림 (마상상마)", new InElephantSetUp()),
    OUT_ELEPHANT(4, "바깥상차림 (상마마상)", new InElephantSetUp()),
    ;

    private final int number;
    private final String format;
    private final BoardSetUp boardSetUp;

    BoardSetUpFormat(int number, String format, BoardSetUp boardSetUp) {
        this.number = number;
        this.format = format;
        this.boardSetUp = boardSetUp;
    }

    public static BoardSetUp getBoardSetUp(int number) {
        for (BoardSetUpFormat value : values()) {
            if (value.number == number) {
                return value.getBoardSetUp();
            }
        }
        throw new IllegalArgumentException("존재하지 않는 번호입니다.");
    }

    public static BoardSetUpFormat from(BoardSetUp boardSetUp) {
        for (BoardSetUpFormat value : values()) {
            if (boardSetUp == value.boardSetUp) {
                return value;
            }
        }
        throw new IllegalStateException("SetUpEntity 에 존재하지 BoardSetUp 입니다.");
    }

    public int getNumber() {
        return number;
    }

    public String getFormat() {
        return format;
    }

    public BoardSetUp getBoardSetUp() {
        return boardSetUp;
    }
}
