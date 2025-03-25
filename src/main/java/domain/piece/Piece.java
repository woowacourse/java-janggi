package domain.piece;

import domain.board.Position;
import domain.rule.MoveRule;
import java.util.List;

public abstract class Piece {

    public static final int NO_MOVE = 0;

    final PieceType type;
    final PieceColor color;
    final MoveRule moveRule;

    protected Piece(PieceType type, PieceColor color, MoveRule moveRule) {
        this.type = type;
        this.color = color;
        this.moveRule = moveRule;
    }

    public boolean canMove(Piece destination, List<Piece> piecesInRoute) {
        return moveRule.isValidateMoveRule(this, destination, piecesInRoute);
    }

    public int countObstacles(List<Piece> piecesInRoute) {
        return (int) piecesInRoute.stream()
                .filter(piece -> piece.color != PieceColor.NONE)
                .count();
    }

    public boolean hasSamePieceType(List<Piece> piecesInRoute) {
        return piecesInRoute.stream()
                .anyMatch(piece -> piece.type == type);
    }

    public boolean isSamePieceType(Piece other) {
        return this.type == other.type;
    }

    public boolean isSamePieceType(PieceType type) {
        return this.type == type;
    }

    public boolean isOtherTeam(Piece other) {
        return this.color != other.color;
    }

    public boolean isOtherTeam(PieceColor color) {
        return this.color != color;
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract boolean isValidMovement(Position source, Position destination);

    public abstract List<Position> findAllRoute(Position source, Position destination);
}
