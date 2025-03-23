package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public class Ma extends Piece {

    private static final List<List<Move>> moves = List.of(
            List.of(Move.FRONT, Move.FRONT_LEFT),
            List.of(Move.FRONT, Move.FRONT_RIGHT),
            List.of(Move.BACK, Move.BACK_LEFT),
            List.of(Move.BACK, Move.BACK_RIGHT),
            List.of(Move.RIGHT, Move.FRONT_RIGHT),
            List.of(Move.RIGHT, Move.BACK_RIGHT),
            List.of(Move.LEFT, Move.FRONT_LEFT),
            List.of(Move.LEFT, Move.BACK_LEFT)
    );

    public Ma(Team team) {
        super(team);
    }

    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        List<Move> possibleMoves = moves.stream()
                .filter(path -> isPossibleToArrive(startPosition, targetPosition, path))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이 위치로 이동할 수 없습니다."));

        return convertToPath(possibleMoves, startPosition);
    }

    @Override
    public boolean isCanon() {
        return false;
    }

    private boolean isPossibleToArrive(Position startPosition, Position targetPosition, List<Move> moveList) {
        for (Move move : moveList) {
            startPosition = startPosition.movePosition(move);
        }
        return startPosition.equals(targetPosition);
    }
}
