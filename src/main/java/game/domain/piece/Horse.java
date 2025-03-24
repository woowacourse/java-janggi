package game.domain.piece;

import static game.domain.board.Direction.*;

import game.domain.board.BoardLocation;
import game.domain.board.BoardVector;
import game.domain.board.Direction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    private static final Map<BoardVector, List<Direction>> DIRECTIONS = Map.of(
            new BoardVector(1, 2), List.of(DOWN, DOWN_RIGHT),
            new BoardVector(1, -2), List.of(UP, UP_LEFT),
            new BoardVector(-1, 2), List.of(DOWN, UP_RIGHT),
            new BoardVector(-1, -2), List.of(UP, UP_LEFT),
            new BoardVector(2, 1), List.of(RIGHT, DOWN_RIGHT),
            new BoardVector(2, -1), List.of(RIGHT, DOWN_LEFT),
            new BoardVector(-2, 1), List.of(LEFT, UP_RIGHT),
            new BoardVector(-2, -1), List.of(LEFT, UP_LEFT)
    );

    public Horse(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if ((boardVector.dx() == 1 && boardVector.dy() == 2) || (boardVector.dx() == 2 && boardVector.dy() == 1)) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);

        List<Direction> directions = DIRECTIONS.get(boardVector);
        List<BoardLocation> paths = new ArrayList<>();

        for (Direction direction : directions) {
            current = current.moveDirection(direction);
            paths.add(current);
        }

        return paths;
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
