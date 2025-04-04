package domain.piece;

import domain.board.MoveDirection;
import domain.board.BoardLocation;
import domain.board.BoardVector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    private static final Map<BoardVector, List<MoveDirection>> DIRECTIONS = Map.of(
            new BoardVector(2, 3), List.of(MoveDirection.DOWN, MoveDirection.DOWN_RIGHT),
            new BoardVector(2, -3), List.of(MoveDirection.UP, MoveDirection.UP_LEFT),
            new BoardVector(-2, 3), List.of(MoveDirection.DOWN, MoveDirection.UP_RIGHT),
            new BoardVector(-2, -3), List.of(MoveDirection.UP, MoveDirection.UP_LEFT),
            new BoardVector(3, 2), List.of(MoveDirection.RIGHT, MoveDirection.DOWN_RIGHT),
            new BoardVector(3, -2), List.of(MoveDirection.RIGHT, MoveDirection.DOWN_LEFT),
            new BoardVector(-3, 2), List.of(MoveDirection.LEFT, MoveDirection.UP_RIGHT),
            new BoardVector(-3, -2), List.of(MoveDirection.LEFT, MoveDirection.UP_LEFT)
    );

    public Elephant(Team team) {
        super(team, new Score(3));
    }

    @Override
    protected void validateArrival(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if (!DIRECTIONS.containsKey(boardVector)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }
    }

    @Override
    protected List<BoardLocation> extractIntermediatePath(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);

        List<MoveDirection> moveDirections = DIRECTIONS.get(boardVector);
        List<BoardLocation> paths = new ArrayList<>();

        for (MoveDirection moveDirection : moveDirections) {
            current = current.moveDirection(moveDirection);
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
