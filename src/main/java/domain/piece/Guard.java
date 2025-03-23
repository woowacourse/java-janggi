package domain.piece;

import domain.Path;
import domain.TeamType;
import domain.piece.move.FixedMoveRule;
import domain.piece.path.DefaultPathValidator;
import domain.position.Direction;
import java.util.List;

public class Guard extends Piece {

    private static final List<Path> PATHS;

    static {
        PATHS = List.of(
                new Path(List.of(Direction.UP)),
                new Path(List.of(Direction.DOWN)),
                new Path(List.of(Direction.RIGHT)),
                new Path(List.of(Direction.LEFT))
        );
    }

    public Guard(TeamType teamType) {
        super(teamType, new FixedMoveRule(PATHS), new DefaultPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }
}
