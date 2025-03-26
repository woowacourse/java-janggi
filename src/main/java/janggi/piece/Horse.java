package janggi.piece;

import janggi.Team;
import janggi.coordinate.Position;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.CurvedMoveStrategy;
import janggi.piece.strategy.move.MoveStrategy;

import java.util.List;

public class Horse extends Piece {

    public static final int STRAIGHT_MOVEMENT = 1;
    public static final int DIAGONAL_MOVEMENT = 1;

    public Horse(final Position position, final Team team, final MoveStrategy moveStrategy, final RequiredBlockCountStrategy blockStrategy) {
        super(position, team, moveStrategy, blockStrategy);
    }

    public static Horse of(Position position, Team team) {
        return new Horse(position, team, new CurvedMoveStrategy(STRAIGHT_MOVEMENT, DIAGONAL_MOVEMENT), RequiredBlockCountStrategy.common());
    }

    public static List<Horse> defaultsOf(Team team) {
        int defaultRow = Team.decideRow(1, team);
        List<Integer> defaultColumns = List.of(3, 8);

        return defaultColumns.stream()
                .map(defaultColumn -> Horse.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Horse.of(position, team);
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }
}
