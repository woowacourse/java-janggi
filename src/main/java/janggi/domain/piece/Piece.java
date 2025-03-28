package janggi.domain.piece;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.moveRule.MoveRule;
import java.util.List;

public class Piece {
    private final TeamColor color;
    private final PieceType type;
    private final MoveRule moveRule;

    public Piece(TeamColor color, PieceType type) {
        this.color = color;
        this.type = type;
        this.moveRule = type.getMoveRule();
    }

    public boolean isValidMovement(PiecePath path) {
        return moveRule.verifyMovement(path, this.color);
    };

    public boolean canMove(Piece sourcePiece, Piece destinationPiece, List<Piece> piecesInRoute) {
        return moveRule.verifyRoute(sourcePiece, destinationPiece, piecesInRoute);
    }

    public List<Position> findAllRoute(PiecePath path) {
        return moveRule.findAllRoute(path);
    }

    public long countPieceInRoute(List<Piece> piecesInRoute) {
        return piecesInRoute.stream()
                .filter(Piece::isNotEmptyPiece)
                .count();
    }

    public boolean isNotEmptyPiece() {
        return this.type != PieceType.NONE;
    }

    public boolean isSamePieceType(Piece other) {
        return this.type == other.type;
    }

    public boolean isPieceType(PieceType pieceType) {
        return this.type == pieceType;
    }

    public boolean isOtherTeam(Piece other) {
        return this.color != other.color;
    }

    public TeamColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public int getScore() {
        return type.getScore();
    }
}
