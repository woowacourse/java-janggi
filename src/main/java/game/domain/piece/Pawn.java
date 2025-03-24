package game.domain.piece;

import game.domain.board.BoardLocation;
import game.domain.board.BoardVector;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        boolean isOrthogonalMove = boardVector.dx() == 0 || boardVector.dy() == 0;
        boolean isOneStepMove = boardVector.getAbsDx() == 1 || boardVector.getAbsDy() == 1;

        if ((this.team == Team.HAN) && isOrthogonalMove && isOneStepMove && !destination.isUp(current)) {
            return;
        }
        if ((this.team == Team.CHO) && isOrthogonalMove && isOneStepMove && !destination.isDown(current)) {
            return;
        }

        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of();
    }

    @Override
    public void validateArrival(List<Piece> pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateKillable(Piece destinationPiece) {
        if (this.isEqualTeam(destinationPiece)){
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목적지로 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
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
