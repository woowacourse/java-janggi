package domain.piece;

import domain.TeamType;
import domain.piece.move.LimitedMoveRule;
import domain.piece.move.area.FreeMoveConstraint;
import domain.piece.path.DefaultPathValidator;
import domain.position.Direction;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {

    private static final Map<TeamType, List<Direction>> TEAM_DIRECTION;
    private static final int SOLDIER_MOVE_COUNT = 1;

    static {
        TEAM_DIRECTION = Map.of(
                TeamType.CHO,
                List.of(Direction.UP, Direction.RIGHT, Direction.LEFT),
                TeamType.HAN,
                List.of(Direction.DOWN, Direction.RIGHT, Direction.LEFT)
        );
    }

    public Soldier(TeamType teamType) {
        super(teamType, new LimitedMoveRule(getPathsByTeam(teamType), SOLDIER_MOVE_COUNT, new FreeMoveConstraint()),
                new DefaultPathValidator());
    }

    private static List<Direction> getPathsByTeam(TeamType teamType) {
        if (!TEAM_DIRECTION.containsKey(teamType)) {
            throw new IllegalStateException("존재하지 않는 팀입니다.");
        }
        return TEAM_DIRECTION.get(teamType);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}
