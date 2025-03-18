package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

import java.util.List;
import java.util.stream.Stream;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team) {
        super(position, team);
    }

    public static List<Chariot> Default(Team team) {
        int row = getRowByTeam(1, team);

        return Stream.of(1, 9)
                .map(col -> new Position(row, col))
                .map(position -> new Chariot(position, team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination);
        return new Chariot(destination, team);
    }

    private void validateMove(final Board board, final Position destination) {
        if (isAllyInDestination(board, destination) || isInvalidMove(board, destination)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    private boolean isAllyInDestination(final Board board, final Position destination) {
        return board.isExists(destination) && board.isAlly(destination, this.team);
    }

    @Override
    public Score die() {
        return Score.Chariot();
    }

    private boolean isInvalidMove(final Board board, final Position destination) {
        return destination.row() != position.row() && destination.column() != position.column();
    }
}
