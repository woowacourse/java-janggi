package domain.piece;

import domain.Move;
import domain.Moves;
import domain.Position;
import domain.Team;
import java.util.List;

public class Byeong extends Piece {

    private static final List<Moves> moves = List.of(
            Moves.create(Move.BACK),
            Moves.create(Move.RIGHT),
            Moves.create(Move.LEFT)
    );

    public Byeong(Team team) {
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
