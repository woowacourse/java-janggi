package janggi.piece;

import janggi.strategy.MoveStrategy;
import janggi.value.Position;
import java.util.List;
import java.util.Objects;

public final class Piece {

    private final PieceType type;
    private final Position position;

    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position = position;
    }

    public Piece move(Position destination, List<Piece> enemy, List<Piece> allies) {
        boolean isAble = ableToMove(destination, enemy, allies);
        if (!isAble) {
            throw new IllegalArgumentException("[ERROR] 이동이 불가능합니다.");
        }
        return new Piece(type, destination);
    }

    private boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
        MoveStrategy moveStrategy = type.getMoveStrategy();
        return moveStrategy.ableToMove(position, destination, enemy, allies);
    }

    public PieceType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public boolean checkPieceType(PieceType pieceType) {
        return type == pieceType;
    }

    public int getScore() {
        return type.getScore();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Piece piece = (Piece) object;
        return type == piece.type && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, position);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", position=" + position +
                '}';
    }
}
