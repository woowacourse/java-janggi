package domain.piece;

import domain.board.Direction;
import domain.board.BoardLocation;
import domain.board.BoardVector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    private static final Map<BoardVector, List<Direction>> DIRECTIONS = Map.of(
            new BoardVector(2, 3), List.of(Direction.DOWN, Direction.DOWN_RIGHT),
            new BoardVector(2, -3), List.of(Direction.UP, Direction.UP_LEFT),
            new BoardVector(-2, 3), List.of(Direction.DOWN, Direction.UP_RIGHT),
            new BoardVector(-2, -3), List.of(Direction.UP, Direction.UP_LEFT),
            new BoardVector(3, 2), List.of(Direction.RIGHT, Direction.DOWN_RIGHT),
            new BoardVector(3, -2), List.of(Direction.RIGHT, Direction.DOWN_LEFT),
            new BoardVector(-3, 2), List.of(Direction.LEFT, Direction.UP_RIGHT),
            new BoardVector(-3, -2), List.of(Direction.LEFT, Direction.UP_LEFT)
    );

    public Elephant(Team team) {
        super(team);
    }

    @Override
    protected void validateArrival(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if (!DIRECTIONS.containsKey(boardVector)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }
    }

    @Override
    protected List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
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
    protected void validateMovePath(List<Piece> pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }
}
