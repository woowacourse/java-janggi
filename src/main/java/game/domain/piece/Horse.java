package game.domain.piece;

import static game.domain.board.Direction.DOWN;
import static game.domain.board.Direction.LEFT;
import static game.domain.board.Direction.RIGHT;
import static game.domain.board.Direction.UP;

import game.domain.board.BoardLocation;
import game.domain.board.BoardVector;
import game.domain.board.Direction;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    private static final Map<BoardVector, Direction> DIRECTIONS = Map.of(
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

        Direction direction = DIRECTIONS.get(boardVector);
        BoardLocation path = current.moveDirection(direction);

        return List.of(path);
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
