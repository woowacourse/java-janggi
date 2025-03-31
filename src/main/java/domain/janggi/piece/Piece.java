package domain.janggi.piece;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Position;
import java.util.Set;

public abstract class Piece {

    protected Position position;
    protected final Color color;
    protected final Board board;

    protected Piece(final Position position, final Color color, final Board board) {
        this.position = position;
        this.color = color;
        this.board = board;
    }

    public void move(final Position position) {
        if (!getMovablePositions().contains(position)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        if (!board.anyMatchSameTeam(this, position)) {
            board.remove(position);
        }
        this.position = position;
    }

    public boolean isSameTeam(final Piece otherPiece) {
        return this.color == otherPiece.color;
    }

    public boolean isSamePosition(final Position position) {
        return this.position.equals(position);
    }

    public Position getPosition() {
        return position;
    }

    public Color getTeam() {
        return color;
    }

    public Board getBoard() {
        return board;
    }

    public abstract Set<Position> getMovablePositions();

    public abstract String getDisplayName();
}
