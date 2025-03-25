package game.domain.piece;

import static game.domain.board.Direction.DOWN;
import static game.domain.board.Direction.DOWN_LEFT;
import static game.domain.board.Direction.DOWN_RIGHT;
import static game.domain.board.Direction.LEFT;
import static game.domain.board.Direction.RIGHT;
import static game.domain.board.Direction.UP;
import static game.domain.board.Direction.UP_LEFT;
import static game.domain.board.Direction.UP_RIGHT;

import game.domain.board.BoardLocation;
import game.domain.board.BoardVector;
import game.domain.board.Direction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    private static final Map<BoardVector, List<Direction>> DIRECTIONS = Map.of(
            new BoardVector(2, 3), List.of(DOWN, DOWN_RIGHT),
            new BoardVector(2, -3), List.of(UP, UP_LEFT),
            new BoardVector(-2, 3), List.of(DOWN, UP_RIGHT),
            new BoardVector(-2, -3), List.of(UP, UP_LEFT),
            new BoardVector(3, 2), List.of(RIGHT, DOWN_RIGHT),
            new BoardVector(3, -2), List.of(RIGHT, DOWN_LEFT),
            new BoardVector(-3, 2), List.of(LEFT, UP_RIGHT),
            new BoardVector(-3, -2), List.of(LEFT, UP_LEFT)
    );

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if (!DIRECTIONS.containsKey(boardVector)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }
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
        return PieceType.ELEPHANT;
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
