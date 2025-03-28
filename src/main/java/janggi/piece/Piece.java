package janggi.piece;

import janggi.starategy.MoveStrategy;
import janggi.value.Position;
import java.util.List;

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

    public boolean ableToMove(Position destination, List<Piece> enemy, List<Piece> allies) {
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
}
