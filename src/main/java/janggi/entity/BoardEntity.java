package janggi.entity;

public record BoardEntity(long boardId, long janggiId) {

    public static BoardEntity from(long janggiId) {
        return new BoardEntity(0, janggiId);
    }

    public BoardEntity addBoardId(long boardId) {
        return new BoardEntity(boardId, janggiId);
    }
}
