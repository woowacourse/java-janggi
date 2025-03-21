package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Score;
import janggi.domain.Team;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(final Position position, final Team team) {
        super(position, team);
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
        validateMove(board, destination);
        validateCannonRestrict(board, destination);
        return new Cannon(destination, team);
    }

    @Override
    protected void validateCorrectRule(Position destination) {
        int diffRow = destination.subtractRow(this.position);
        int diffColumn = destination.subtractColumn(this.position);

        if (Math.min(Math.abs(diffRow), Math.abs(diffColumn)) != 0) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    @Override
    protected void validateIsBlock(final Board board, final Position destination) {
        if (countPieceInRoute(board, destination) != 1) {
            throw new IllegalArgumentException("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
        }
    }

    private void validateCannonRestrict(final Board board, final Position destination) {
        boolean containsCannon = Route.of(this.position, destination).stream()
                .filter(board::isExists)
                .anyMatch(position -> board.getPiece(position).isCannon());

        if (containsCannon) {
            throw new IllegalArgumentException("이동 경로에 포가 존재합니다.");
        }
    }

    @Override
    public Score die() {
        return Score.Cannon();
    }

    @Override
    protected boolean isCannon() {
        return true;
    }
}
