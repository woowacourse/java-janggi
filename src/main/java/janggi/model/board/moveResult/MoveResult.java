package janggi.model.board.moveResult;

import janggi.model.board.position.Position;

public class MoveResult {

    private final PositionPath path;
    private final Position from;
    private final Position to;

    public MoveResult(PositionPath path, Position from, Position to) {
        this.path = path;
        this.from = from;
        this.to = to;
    }

    public MoveResult cancatenate(MoveResult other) {
        if (!this.to.equals(other.from)) {
            throw new IllegalArgumentException("두 경로를 연결할 수 없습니다.");
        }

        PositionPath first = this.path
                .addFirstAndLast(this.from, this.to);

        PositionPath second = other.path
                .addFirstAndLast(other.from, other.to);

        PositionPath result = first.concatenate(second);

        return new MoveResult(
                result.removeFirstAndLast(),
                result.getFirst(),
                result.getLast()
        );
    }

    public PositionPath getPath() {
        return path;
    }

    public Position getTo() {
        return to;
    }
}
