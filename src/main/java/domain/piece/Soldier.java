package domain.piece;

import domain.piece.path.FixedSingleMovePathFinder;
import domain.position.Direction;
import domain.position.Movement;
import domain.piece.path.DefaultPathValidator;
import domain.piece.path.FixedMultiStepPathFinder;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {
    private static final Map<TeamType, List<Direction>> DIRECTIONS;

    static {
        DIRECTIONS = Map.of(
                TeamType.CHO,
                List.of(Direction.UP,
                        Direction.RIGHT,
                        Direction.LEFT)
                ,
                TeamType.HAN,
                List.of(Direction.DOWN,
                        Direction.RIGHT,
                        Direction.LEFT));
    }

    public Soldier(TeamType teamType) {
        super(teamType, new FixedSingleMovePathFinder(findDirections(teamType)), new DefaultPathValidator());
    }

    private static List<Direction> findDirections(TeamType teamType) {
        return DIRECTIONS.get(teamType);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

}
