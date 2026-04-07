package janggi.entity;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.board.setup.InElephantSetUp;
import janggi.domain.board.setup.LeftElephantSetUp;
import janggi.domain.board.setup.OutElephantSetUp;
import janggi.domain.board.setup.RightElephantSetUp;

public enum SetUpEntity {
    IN_ELEPHANT(new InElephantSetUp()),
    OUT_ELEPHANT(new OutElephantSetUp()),
    LEFT_ELEPHANT(new LeftElephantSetUp()),
    RIGHT_ELEPHANT(new RightElephantSetUp()),

    ;
    private final BoardSetUp boardSetUp;

    SetUpEntity(BoardSetUp boardSetUp) {
        this.boardSetUp = boardSetUp;
    }

    public static SetUpEntity from(BoardSetUp boardSetUp) {
        for (SetUpEntity value : values()) {
            if (boardSetUp.equals(value.boardSetUp)) {
                return value;
            }
        }
        throw new IllegalStateException("SetUpEntity 에 존재하지 BoardSetUp 입니다.");
    }

    public BoardSetUp getBoardSetUp() {
        return boardSetUp;
    }
}
