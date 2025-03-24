package janggi.piece;

import janggi.Board;
import janggi.Score;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.piece.strategy.block.BlockStrategy;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.CurvedMoveStrategy;
import janggi.piece.strategy.move.MoveStrategy;

import java.util.List;

public class Elephant extends Piece {

    public static final int STRAIGHT_MOVEMENT = 1;
    public static final int DIAGONAL_MOVEMENT = 2;

    public Elephant(final Position position, final Team team, final MoveStrategy moveStrategy, final BlockStrategy blockStrategy) {
        super(position, team, moveStrategy, blockStrategy);
    }

    public static Elephant of(Position position, Team team) {
        return new Elephant(position, team, new CurvedMoveStrategy(STRAIGHT_MOVEMENT, DIAGONAL_MOVEMENT), RequiredBlockCountStrategy.common());
    }

    public static List<Elephant> defaultsOf(Team team) {
        int defaultRow = Team.decideRow(1, team);
        List<Integer> defaultColumns = List.of(2, 7);

        return defaultColumns.stream()
                .map(defaultColumn -> Elephant.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    public Score die() {
        return Score.elephant();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Elephant.of(position, team);
    }

    @Override
    protected void validateSpecialRule(final Board board, final Position destination) {
        // 특수 규칙 없음
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }
}
