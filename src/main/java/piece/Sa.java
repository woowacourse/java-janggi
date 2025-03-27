package piece;

import board.Board;
import coordinate.Coordinate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import team.Team;

public class Sa extends Piece {

    public Sa(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        if (!departure.isInCastle()) {
            throw new IllegalStateException("궁성 기물은 궁성 좌표 안에만 존재할 수 있습니다.");
        }

        return Stream.concat(
                        departure.moveByCrossOne().stream(),
                        departure.moveByDiagonalOneInCastle().stream()
                )
                .filter(Objects::nonNull)
                .filter(Coordinate::isInCastle)
                .collect(Collectors.toSet());
    }

    @Override
    protected boolean canMoveConsideringObstacles(Board board, Coordinate departure, Coordinate arrival) {
        return true;
    }

    @Override
    protected Set<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        return Set.of();
    }

    @Override
    public String getName() {
        return "사";
    }
}
