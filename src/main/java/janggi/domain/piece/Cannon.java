package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Score;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.CannonBlockStrategy;
import janggi.domain.rule.move.StraightMoveStrategy;
import java.util.List;

public class Cannon extends Piece {

    private static final int SCORE = 7;
    private static final Movement MOVEMENT = new Movement(List.of(0, 1));

    public Cannon(final Position position, final Team team) {
        super(position, team, PieceType.Cannon, new MoveRule(new StraightMoveStrategy(), new CannonBlockStrategy()));
    }

    public static List<Cannon> Default(Team team) {
        int defaultRow = Team.decideRow(3, team);
        List<Integer> defaultColumns = List.of(2, 8);

        return defaultColumns.stream()
                .map(defaultColumn -> new Cannon(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination, MOVEMENT);
        validateCannonRestrict(board, destination);
        return new Cannon(destination, team);
    }

    private void validateCannonRestrict(final Board board, final Position destination) {
        checkExistCannonInDestination(board, destination);
        boolean containsCannon = Route.of(this.position, destination).stream()
                .filter(board::isExists)
                .anyMatch(position -> board.getPiece(position).isSameType(this));

        if (containsCannon) {
            throw new IllegalArgumentException("이동 경로에 포가 존재합니다.");
        }
    }

    private void checkExistCannonInDestination(final Board board, final Position destination) {
        if (board.isExists(destination) && board.getPiece(destination).isSameType(this)) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public Score die() {
        return new Score(SCORE);
    }
}
