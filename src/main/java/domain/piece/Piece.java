package domain.piece;

import domain.Board;
import domain.Color;
import domain.Position;
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

    protected abstract Set<Position> getMovablePositions();

    public abstract String getDisplayName();

    public boolean isSamePosition(final Position position) {
        return this.position.equals(position);
    }

    public boolean isSameTeam(final Piece otherPiece) {
        return this.color == otherPiece.color;
    }

    public void move(final Position position) {
        if (!getMovablePositions().contains(position)) {
            throw new IllegalArgumentException();
        }
        if (!board.isSameTeam(this, position)) {
            board.remove(position);
        }
        this.position = position;
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
}
