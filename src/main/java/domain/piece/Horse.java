package domain.piece;

import domain.BoardLocation;
import domain.BoardVector;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

    public Horse(Team team) {
        super(team);
    }

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        return (differenceX == 1 && differenceY == 2) || (differenceX == 2 && differenceY == 1);
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        List<BoardLocation> path = new ArrayList<>();
        for(Direction direction : Direction.values()) {
            for (Diagonal diagonal : Diagonal.values()) {
                if (boardVector.equals(new BoardVector(direction.x() + diagonal.x(), direction.y() + diagonal.y()))) {
                    BoardLocation next = current.move(direction.x(), direction.y());
                    path.add(next);
                }
            }
        }
        return path;
    }

    @Override
    public boolean canArrive(List<Piece> pathPiece) {
        return pathPiece.isEmpty();
    }

    @Override
    public boolean canDestination(Piece destinationPiece) {
        return !this.isEqualTeam(destinationPiece);
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
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
