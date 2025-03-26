package janggi.piece;

import janggi.Board;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.MoveStrategy;
import janggi.piece.strategy.move.SingleMoveStrategy;

import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Position position, final Team team, final MoveStrategy moveStrategy, final RequiredBlockCountStrategy blockStrategy) {
        super(position, team, moveStrategy, blockStrategy);
    }

    public static Soldier of(Position position, Team team) {
        return new Soldier(position, team, new SingleMoveStrategy(), RequiredBlockCountStrategy.common());
    }

    public static List<Soldier> defaultsOf(Team team) {
        int defaultRow = Team.decideRow(4, team);
        List<Integer> defaultColumns = List.of(1, 3, 5, 7, 9);

        return defaultColumns.stream()
                .map(defaultColumn -> Soldier.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Soldier.of(position, team);
    }

    @Override
    protected void validateSpecialRule(final Board board, final Position destination) {
        Vector vector = Vector.of(position, destination);

        if (team.isHan() && vector.deltaRow() < 0) {
            throw new IllegalArgumentException("병은 본진을 향할 수 없습니다.");
        }

        if (team.isCho() && vector.deltaRow() > 0) {
            throw new IllegalArgumentException("졸은 본진을 향할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}
