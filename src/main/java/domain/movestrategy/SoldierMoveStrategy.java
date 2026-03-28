package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import java.util.Map;

public class SoldierMoveStrategy extends BasicMoveStrategy {

    private static final List<Delta> CHO_PATHS = List.of(
            Delta.RIGHT, Delta.DOWN, Delta.LEFT
    );

    private static final List<Delta> HAN_PATHS = List.of(
            Delta.RIGHT, Delta.UP, Delta.LEFT
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        List<Delta> paths = getPathsByTeam(from, pieces);

        return paths.stream()
                .map(from::move)
                .filter(this::isInsideBoard)
                .toList();
    }

    private static List<Delta> getPathsByTeam(final Position from, final Map<Position, Piece> pieces) {
        if (pieces.get(from).getTeam() == Team.HAN) {
            return HAN_PATHS;
        }
        return CHO_PATHS;
    }
}
