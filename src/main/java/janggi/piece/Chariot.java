package janggi.piece;

import janggi.Team;
import janggi.coordinate.Position;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.MoveStrategy;
import janggi.piece.strategy.move.StraightMoveStrategy;

import java.util.List;

public class Chariot extends Piece {

    private Chariot(final Position position, final Team team, final MoveStrategy moveStrategy, final RequiredBlockCountStrategy blockStrategy) {
        super(position, team, moveStrategy, blockStrategy);
    }

    public static Chariot of(final Position position, final Team team) {
        return new Chariot(position, team, new StraightMoveStrategy(), RequiredBlockCountStrategy.common());
    }

    public static List<Chariot> defaultsOf(Team team) {
        int defaultRow = Team.decideRow(1, team);
        List<Integer> defaultColumns = List.of(1, 9);

        return defaultColumns.stream()
                .map(defaultColumn -> Chariot.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Chariot.of(position, team);
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }
}
