package domain.piece;

import domain.BoardLocation;
import domain.BoardVector;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        return differenceX == 0 || differenceY == 0;
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        List<BoardLocation> path = new ArrayList<>();
        BoardVector boardVector = destination.minus(current);

        if (boardVector.isDxZero()) {
            int dy = boardVector.dy();
            for (int i = 1; i < dy; i++) {
                path.add(current.moveY(i));
            }
            return path;
        }

        int dx = boardVector.dx();
        for (int i = 1; i < dx; i++) {
            path.add(current.moveX(i));
        }
        return path;
    }

    @Override
    public boolean canArrive(List<Piece> pathPiece) {
        if (pathPiece.size() != 1){
            return false;
        }
        return this.isNotSameType(pathPiece.getFirst());
    }

    @Override
    public boolean canDestination(Piece destinationPiece) {
        if (this.isEqualTeam(destinationPiece)){
            return false;
        }
        return this.isNotSameType(destinationPiece);
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
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
