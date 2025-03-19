package janggi.piece;

import janggi.*;

import java.util.List;
import java.util.stream.Stream;

public class Cannon extends Piece {

    public Cannon(final Position position, final Team team) {
        super(position, team);
    }

    public static List<Cannon> Default(Team team) {
        int row = getRowByTeam(3, team);

        return Stream.of(2, 8)
                .map(col -> Position.of(row, col))
                .map(position -> new Cannon(position, team))
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

        int maxDiff = Math.max(Math.abs(diffRow), Math.abs(diffColumn));
        int minDiff = Math.min(Math.abs(diffRow), Math.abs(diffColumn));

        if (maxDiff != 0 && minDiff != 0) {
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
