package domain.piece;

import domain.Path;
import domain.TeamType;
import domain.piece.move.FixedMoveRule;
import domain.piece.path.DefaultPathValidator;
import domain.position.Direction;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {

    private static final Map<TeamType, List<Path>> TEAM_PATH;

    static {
        TEAM_PATH = Map.of(
                TeamType.CHO,
                List.of(new Path(List.of(Direction.UP)),
                        new Path(List.of(Direction.RIGHT)),
                        new Path(List.of(Direction.LEFT))
                ),
                TeamType.HAN,
                List.of(new Path(List.of(Direction.DOWN)),
                        new Path(List.of(Direction.RIGHT)),
                        new Path(List.of(Direction.LEFT))
                )
        );
    }

    public Soldier(TeamType teamType) {
        super(teamType, new FixedMoveRule(getPathsByTeam(teamType)), new DefaultPathValidator());
    }

    private static List<Path> getPathsByTeam(TeamType teamType) {
        if (!TEAM_PATH.containsKey(teamType)) {
            throw new IllegalStateException("존재하지 않는 팀입니다.");
        }
        return TEAM_PATH.get(teamType);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}
