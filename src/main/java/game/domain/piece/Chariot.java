package game.domain.piece;

import game.domain.board.Axis;
import game.domain.board.BoardLocation;
import game.domain.board.BoardVector;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if (boardVector.dx() == 0 || boardVector.dy() == 0) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);

        int dy = boardVector.dy();
        int dx = boardVector.dx();

        Axis quadrant = Axis.findQuadrant(dx, dy);
        return quadrant.createAllPath(current, dx, dy);
    }

    @Override
    public void validateArrival(List<Piece> pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateKillable(Piece destinationPiece) {
        if (this.isEqualTeam(destinationPiece)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목적지로 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass());
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
