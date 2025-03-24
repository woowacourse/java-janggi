package game.domain.piece;

import game.domain.board.BoardLocation;
import game.domain.board.BoardVector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if (boardVector.dx() == 0 || boardVector.dy() == 0){
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        List<BoardLocation> path = new ArrayList<>();
        BoardVector boardVector = BoardVector.between(current, destination);

        int dy = boardVector.dy();
        int dx = boardVector.dx();
        if (dx == 0 && dy > 0) {
            for (int i = 1; i < dy; i++) {
                path.add(current.moveY(i));
            }
            return path;
        }

        if (dx == 0 && dy < 0) {
            for (int i = -1; i > dy; i--) {
                path.add(current.moveY(i));
            }
            return path;
        }

        if (dx > 0 && dy == 0) {
            for (int i = 1; i < dx; i++) {
                path.add(current.moveX(i));
            }
            return path;
        }

        if (dx < 0 && dy == 0) {
            for (int i = -1; i > dx; i--) {
                path.add(current.moveX(i));
            }
            return path;
        }
        return Collections.emptyList();
    }

    @Override
    public void validateArrival(List<Piece> pathPiece) {
        if (pathPiece.size() != 1 || isSameType(pathPiece.getFirst())){
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateKillable(Piece destinationPiece) {
        if (this.isEqualTeam(destinationPiece) || isSameType(destinationPiece)){
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
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
