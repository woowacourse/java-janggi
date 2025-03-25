package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public class Ma extends Piece {

    private static final List<Moves> moves = List.of(
            Moves.create(Move.FRONT, Move.FRONT_LEFT),
            Moves.create(Move.FRONT, Move.FRONT_RIGHT),
            Moves.create(Move.BACK, Move.BACK_LEFT),
            Moves.create(Move.BACK, Move.BACK_RIGHT),
            Moves.create(Move.RIGHT, Move.FRONT_RIGHT),
            Moves.create(Move.RIGHT, Move.BACK_RIGHT),
            Moves.create(Move.LEFT, Move.FRONT_LEFT),
            Moves.create(Move.LEFT, Move.BACK_LEFT)
    );

    public Ma(Team team) {
        super(team);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        Moves possibleMoves = moves.stream()
                .filter(moves -> moves.isPossibleToArrive(startPosition, targetPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이 위치로 이동할 수 없습니다."));

        return possibleMoves.convertToPath(startPosition);
    }
}
