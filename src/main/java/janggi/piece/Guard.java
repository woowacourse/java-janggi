package janggi.piece;

import janggi.Team;
import janggi.coordinate.Position;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.MoveStrategy;
import janggi.piece.strategy.move.SingleMoveStrategy;

import java.util.List;

public class Guard extends Piece {

    public Guard(final Position position, final Team team, final MoveStrategy moveStrategy, final RequiredBlockCountStrategy blockStrategy) {
        super(position, team, moveStrategy, blockStrategy);
    }

    public static Guard of(Position position, Team team) {
        return new Guard(position, team, new SingleMoveStrategy(), RequiredBlockCountStrategy.common());
    }

    public static List<Guard> defaultsOf(Team team) {
        int defaultRow = Team.decideRow(1, team);
        List<Integer> defaultColumns = List.of(4, 6);

        return defaultColumns.stream()
                .map(defaultColumn -> Guard.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Guard.of(position, team);
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }
}
