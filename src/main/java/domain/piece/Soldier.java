package domain.piece;

import domain.position.Direction;
import domain.position.Movement;
import domain.piece.path.DefaultPathValidator;
import domain.piece.path.FixedPatternPathFinder;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {
    private static final Map<TeamType, List<Movement>> MOVEMENTS;

    static {
        MOVEMENTS = Map.of(
                TeamType.CHO,
                List.of(new Movement(List.of(Direction.UP)),
                        new Movement(List.of(Direction.RIGHT)),
                        new Movement(List.of(Direction.LEFT)))
                ,
                TeamType.HAN,
                List.of(new Movement(List.of(Direction.DOWN)),
                        new Movement(List.of(Direction.RIGHT)),
                        new Movement(List.of(Direction.LEFT))));
    }

    public Soldier(TeamType teamType) {
        super(teamType, new FixedPatternPathFinder(findMovements(teamType)), new DefaultPathValidator());
    }

    private static List<Movement> findMovements(TeamType teamType) {
        return MOVEMENTS.get(teamType);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

}
