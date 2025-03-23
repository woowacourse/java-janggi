package domain.piece;

import static domain.piece.PieceType.*;

import domain.BoardLocation;
import domain.Team;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Team team) {
        super(PAWN, team);
    }

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        if (this.team == Team.HAN) {
            int differenceX = current.distanceX(destination);
            int differenceY = current.distanceY(destination);
            boolean isOrthogonalMove = differenceX == 0 || differenceY == 0;
            boolean isOneStepMove = differenceX == 1 || differenceY == 1;
            boolean isMovingUp = destination.isUp(current);
            return isOrthogonalMove && isOneStepMove && !isMovingUp;
        }
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        boolean a = differenceX == 0 || differenceY == 0;
        boolean b = differenceX == 1 || differenceY == 1;
        boolean c = destination.isDown(current);
        return a && b && !c;
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of();
    }

    @Override
    public boolean canArrive(List<Piece> pathPiece) {
        return pathPiece.isEmpty();
    }

    @Override
    public boolean canDestination(Piece destinationPiece) {
        return !this.isEqualTeam(destinationPiece);
    }
}
