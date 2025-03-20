package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;
import janggi.piece.strategy.block.BlockStrategy;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.MoveStrategy;
import janggi.piece.strategy.move.SingleMoveStrategy;

public class General extends Piece {

    public General(final Position position, final Team team, final MoveStrategy moveStrategy, final BlockStrategy blockStrategy) {
        super(position, team, moveStrategy, blockStrategy);
    }

    public static General of(final Position position, final Team team) {
        return new General(position, team, new SingleMoveStrategy(), RequiredBlockCountStrategy.common());
    }

    public static General Default(Team team) {
        int defaultRow = Team.decideRow(2, team);
        int defaultColumn = 5;

        return General.of(Position.of(defaultRow, defaultColumn), team);
    }

    @Override
    public Score die() {
        // 점수 많이 주고 게임 끝내기 가능
        return Score.General();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return General.of(position, team);
    }

    @Override
    protected void validateSpecialRule(final Board board, final Position destination) {
        // 특수 규칙 없음
    }

    @Override
    protected PieceType getType() {
        return PieceType.GENERAL;
    }
}
