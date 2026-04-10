package janggi.domain.board.setup;

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


    public BoardSetUp getBoardSetUp() {
        return boardSetUp;
    }
}
