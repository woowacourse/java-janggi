package domain.piece;

import domain.position.Direction;
import domain.Path;
import domain.position.Position;
import domain.TeamType;
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

    public Soldier(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Soldier(Soldier soldier) {
        super(soldier);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public Piece newInstance() {
        return new Soldier(this);
    }

    @Override
    protected List<Path> getPaths() {
        if(!TEAM_PATH.containsKey(teamType)){
            throw new IllegalStateException("존재하지 않는 팀입니다.");
        }
        return TEAM_PATH.get(teamType);
    }
}
