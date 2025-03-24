package domain.piece;

import domain.BoardLocation;
import domain.BoardVector;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        if (differenceX == 0 || differenceY == 0){
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        List<BoardLocation> path = new ArrayList<>();
        BoardVector boardVector = BoardVector.between(current, destination);

        if (boardVector.isDxZero()) {
            int dy = boardVector.dy();
            if (dy > 0) {
                for (int i = 1; i < dy; i++) {
                    path.add(current.moveY(i));
                }
            }

            if (dy < 0) {
                for (int i = -1; i > dy; i--) {
                    path.add(current.moveY(i));
                }
            }

            return path;
        }

        int dx = boardVector.getAbsDx();
        if (dx > 0) {
            for (int i = 1; i < dx; i++) {
                path.add(current.moveX(i));
            }
        }

        if (dx < 0) {
            for (int i = -1; i > dx; i--) {
                path.add(current.moveX(i));
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
