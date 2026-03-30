package janggi.view;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.board.setup.InElephantSetUp;
import janggi.domain.board.setup.LeftElephantSetUp;
import janggi.domain.board.setup.OutElephantSetUp;
import janggi.domain.board.setup.RightElephantSetUp;

public enum BoardSetUpFormat {
    LEFT_SET_UP(1, "왼상차림 (상마상마)", new LeftElephantSetUp()),
    RIGHT_SET_UP(2, "오른상차림 (마상마상)", new RightElephantSetUp()),
    IN_SET_UP(3, "안상차림 (마상상마)", new InElephantSetUp()),
    OUT_SET_UP(4, "바깥상차림 (상마마상)", new OutElephantSetUp()),
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
