package domain.piece;

import domain.BoardLocation;
import domain.Team;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        if (this.team == Team.HAN) {
            int differenceX = current.distanceX(destination);
            int differenceY = current.distanceY(destination);
            boolean isOrthogonalMove = differenceX == 0 || differenceY == 0;
            boolean isOneStepMove = differenceX == 1 || differenceY == 1;
            boolean isMovingUp = destination.isUp(current);
            if (isOrthogonalMove && isOneStepMove && !isMovingUp){
                return;
            }
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        boolean isOrthogonalMove = differenceX == 0 || differenceY == 0;
        boolean isOneStepMove = differenceX == 1 || differenceY == 1;
        boolean isMovingDown = destination.isDown(current);
        if (isOrthogonalMove && isOneStepMove && !isMovingDown){
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
        if (pathPiece.isEmpty()){
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
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
