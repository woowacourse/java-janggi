package piece;

import static coordinate.Direction.DOWN;
import static coordinate.Direction.LEFT;
import static coordinate.Direction.RIGHT;

import board.Board;
import coordinate.Coordinate;
import coordinate.MoveVector;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import team.Team;

public class Byeong extends Piece {

    public Byeong(Team team) {
        super(team);
        if (team == Team.CHO) {
            throw new IllegalArgumentException("병은 초나라에서 사용할 수 없습니다.");
        }
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return Stream.<List<MoveVector>>of(
                        List.of(DOWN),
                        List.of(RIGHT),
                        List.of(LEFT)
                )
                .map(departure::moveBy)
                .filter(Objects::nonNull)
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
        return "병";
    }
}
