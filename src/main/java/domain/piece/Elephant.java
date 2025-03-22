package domain.piece;

import domain.position.Direction;
import domain.Path;
import domain.position.Position;
import domain.TeamType;
import java.util.List;

public class Elephant extends Piece {
    private static final List<Path> PATHS;

    static {
        PATHS = List.of(
                new Path(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT_UP)),
                new Path(List.of(Direction.UP, Direction.LEFT_UP, Direction.LEFT_UP)),
                new Path(List.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
                new Path(List.of(Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT_DOWN)),
                new Path(List.of(Direction.RIGHT, Direction.RIGHT_UP, Direction.RIGHT_UP)),
                new Path(List.of(Direction.RIGHT, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)),
                new Path(List.of(Direction.LEFT, Direction.LEFT_UP, Direction.LEFT_UP)),
                new Path(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.LEFT_DOWN))
        );
    }

    public Elephant(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Elephant(Elephant elephant) {
        super(elephant);
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public Piece newInstance() {
        return new Elephant(this);
    }

    @Override
    protected List<Path> getPaths() {
        return PATHS;
    }
}
