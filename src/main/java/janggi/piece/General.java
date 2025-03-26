package janggi.piece;

import janggi.Team;
import janggi.coordinate.Position;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.MoveStrategy;
import janggi.piece.strategy.move.SingleMoveStrategy;

public class General extends Piece {

    public General(final Position position, final Team team, final MoveStrategy moveStrategy, final RequiredBlockCountStrategy blockStrategy) {
        super(position, team, moveStrategy, blockStrategy);
    }

    public static General of(final Position position, final Team team) {
        return new General(position, team, new SingleMoveStrategy(), RequiredBlockCountStrategy.common());
    }

    public static General defaultOf(Team team) {
        int defaultRow = Team.decideRow(2, team);
        int defaultColumn = 5;

        return General.of(Position.of(defaultRow, defaultColumn), team);
    }

    @Override
    protected Piece createPiece(final Position position) {
        return General.of(position, team);
    }

    @Override
    public PieceType getType() {
        return PieceType.GENERAL;
    }
}
