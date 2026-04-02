package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public class Cannon extends ActivePiece {

    public Cannon(Team team) {
        super(team, PieceType.PHO);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return source.isSameCol(target) || source.isSameRow(target);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        if (source.isSameCol(target)) {
            return source.makeRowStraightRoute(target);
        }

        return source.makeColStraightRoute(target);
    }

    @Override
    public void validateRoute(List<Piece> piecesOnRoute, Piece destinationPiece) {
        int count = 0;
        for (Piece piece : piecesOnRoute) {
            if (piece.isCannon()) {
                throw new IllegalArgumentException("포는 포를 넘지 못합니다.");
            }
            if (piece.isNotEmpty()) {
                count++;
            }
        }

        if (count != 1) {
            throw new IllegalArgumentException("포가 넘을 수 있는 기물의 개수는 하나입니다.");
        }

        if (destinationPiece.isCannon()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
        if (destinationPiece.isAlly(this)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
