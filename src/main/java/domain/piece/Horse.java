package domain.piece;

import static domain.board.MoveDirection.DOWN;
import static domain.board.MoveDirection.LEFT;
import static domain.board.MoveDirection.RIGHT;
import static domain.board.MoveDirection.UP;

import domain.board.BoardLocation;
import domain.board.BoardVector;
import domain.board.MoveDirection;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    private static final Map<BoardVector, MoveDirection> DIRECTIONS = Map.of(
            new BoardVector(1, 2), DOWN,
            new BoardVector(1, -2), UP,
            new BoardVector(-1, 2), DOWN,
            new BoardVector(-1, -2), UP,
            new BoardVector(2, 1), RIGHT,
            new BoardVector(2, -1), RIGHT,
            new BoardVector(-2, 1), LEFT,
            new BoardVector(-2, -1), LEFT
    );

    public Horse(Team team) {
        super(team, new Score(5));
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

        MoveDirection moveDirection = DIRECTIONS.get(boardVector);
        BoardLocation path = current.moveDirection(moveDirection);

        return List.of(path);
    }

    @Override
    protected void validateMovePath(List<Piece> pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }
}
