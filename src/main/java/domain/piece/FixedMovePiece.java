package domain.piece;

import domain.Moves;
import domain.Position;
import domain.Team;
import java.util.List;

public abstract class FixedMovePiece extends Piece {

    public FixedMovePiece(Team team) {
        super(team);
    }

    abstract protected List<Moves> getMovesOptions();

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        List<Moves> movesOptions = getMovesOptions();
        Moves possibleMoves = movesOptions.stream()
                .filter(moves -> moves.isPossibleToArrive(startPosition, targetPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이 위치로 이동할 수 없습니다."));

        return possibleMoves.convertToPath(startPosition);
    }
}
