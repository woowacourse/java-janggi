package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team, PieceType.PHO);
    }

    @Override
    public boolean canBeTargetedByCannon() {
        return false;
    }

    @Override
    public boolean canBeJumpedByCannon() {
        return false;
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return source.isSameColumn(target) || source.isSameRow(target);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        if (source.isSameColumn(target)) {
            return source.makeRowStraightRoute(target);
        }
        return source.makeColumnStraightRoute(target);
    }

    @Override
    public void validateRoute(List<Piece> piecesOnRoute, Piece destinationPiece) {
        validateDestinationPiece(destinationPiece);
        validateJumpPiece(piecesOnRoute);
    }

    private void validateDestinationPiece(Piece destinationPiece) {
        if (!destinationPiece.canBeTargetedByCannon()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
        if (destinationPiece.isAlly(this)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }

    private void validateJumpPiece(List<Piece> piecesOnRoute) {
        int jumpPiecesCount = countJumpPieces(piecesOnRoute);
        if (jumpPiecesCount != 1) {
            throw new IllegalArgumentException("포가 넘을 수 있는 기물의 개수는 하나입니다.");
        }
    }

    private int countJumpPieces(List<Piece> piecesOnRoute) {
        int count = 0;
        for (Piece piece : piecesOnRoute) {
            validateNotPho(piece);
            count += addIfNotEmpty(piece);
        }
        return count;
    }

    private void validateNotPho(Piece piece) {
        if (!piece.canBeJumpedByCannon()) {
            throw new IllegalArgumentException("포는 포를 넘지 못합니다.");
        }
    }

    private int addIfNotEmpty(Piece piece) {
        if (piece.isNotEmpty()) {
            return 1;
        }
        return 0;
    }
}
