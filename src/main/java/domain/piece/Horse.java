package domain.piece;

import domain.position.Direction;
import domain.Path;
import domain.position.Position;
import domain.TeamType;
import java.util.List;

public class Horse extends Piece {
    private static final List<Path> PATHS;

    static {
        PATHS = List.of(
                new Path(List.of(Direction.UP, Direction.RIGHT_UP)),
                new Path(List.of(Direction.UP, Direction.LEFT_UP)),
                new Path(List.of(Direction.DOWN, Direction.RIGHT_DOWN)),
                new Path(List.of(Direction.DOWN, Direction.LEFT_DOWN)),
                new Path(List.of(Direction.RIGHT, Direction.RIGHT_UP)),
                new Path(List.of(Direction.RIGHT, Direction.RIGHT_DOWN)),
                new Path(List.of(Direction.LEFT, Direction.LEFT_UP)),
                new Path(List.of(Direction.LEFT, Direction.LEFT_DOWN))
        );
    }

    public Horse(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Horse(Horse horse) {
        super(horse);
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }

    @Override
    public Piece newInstance() {
        return new Horse(this);
    }

    @Override
    protected List<Path> getPaths() {
        return PATHS;
    }
}
