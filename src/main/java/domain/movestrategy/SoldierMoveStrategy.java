package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Team;
import java.util.List;
import java.util.Map;

public class SoldierMoveStrategy extends DefaultMoveStrategy {

    private static final List<Position> OFFSET_POSITIONS = List.of(
            Position.of(0, 1),
            Position.of(0, -1),
            Position.of(1, 0)
    );

    private static final List<Direction> PATHS_OF_HAN = List.of(Direction.RIGHT, Direction.UP, Direction.LEFT);
    private static final List<Direction> PATHS_OF_CHO = List.of(Direction.RIGHT, Direction.DOWN, Direction.LEFT);

    // TODO: 이동할 칸이 아군이면 이동 불가
    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        List<Position> paths = PATHS_OF_CHO.stream()
                .map(Direction::getDelta)
                .toList();

        if (pieces.get(from).getTeam() == Team.HAN) {
            paths = PATHS_OF_HAN.stream()
                    .map(Direction::getDelta)
                    .toList();
        }

        return paths.stream()
                .filter(this::inBoard)
                .filter(position -> pieces.get(from).isOpposite(pieces.get(position)))
                .toList();
    }
}
