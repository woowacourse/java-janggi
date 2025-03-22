package domain.piece;

import domain.position.Direction;
import domain.Path;
import domain.position.Position;
import domain.TeamType;
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

    public Guard(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Guard(Guard guard) {
        super(guard);
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }

    @Override
    public Piece newInstance() {
        return new Guard(this);
    }

    @Override
    protected List<Path> getPaths() {
        return PATHS;
    }
}
