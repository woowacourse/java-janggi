package domain.piece;

import domain.piece.move.Path;
import domain.TeamType;
import domain.piece.move.FixedMoveRule;
import domain.piece.move.area.FreeMoveConstraint;
import domain.piece.path.DefaultPathValidator;
import domain.position.Direction;
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

    public Elephant(TeamType teamType) {
        super(teamType, new FixedMoveRule(PATHS, new FreeMoveConstraint()), new DefaultPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }
}
