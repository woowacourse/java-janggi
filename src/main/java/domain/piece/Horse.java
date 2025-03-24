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
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if ((boardVector.dx() == 1 && boardVector.dy() == 2) || (boardVector.dx() == 2 && boardVector.dy() == 1)){
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
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
