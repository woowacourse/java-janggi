package model.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import model.Position;
import model.Team;
import model.board.Board;

public abstract class Piece {

    private final Team team;
    protected Position position;
    protected final List<Route> routes = new ArrayList<>();

    protected Piece(int x, int y, Team team) {
        this.team = team;
        position = new Position(x, y);
    }

    public void move(Board board, Team currentTurn, int dx, int dy) {
        Position target = position.move(dx, dy);
        validateInBoard(board, target);
        validateTeam(currentTurn);
        Route movableRoute = findMovableRoute(board, dx, dy);
        validateRoute(board, movableRoute, target);
        arrival(board, target);
        position = target;
    }

    private void validateTeam(Team currentTurn) {
        if (!currentTurn.equals(team)) {
            throw new IllegalArgumentException("[ERROR] 다른 팀의 기물은 움직일 수 없습니다.");
        }
    }

    private static void validateInBoard(Board board, Position target) {
        if (!board.isInBoard(target)) {
            throw new IllegalArgumentException("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
        }
    }

    protected abstract Route findMovableRoute(Board board, int dx, int dy);

    protected abstract void validateRoute(Board board, Route route, Position target);

    private void arrival(Board board, Position target) {
        if (!board.hasPieceOn(target)) {
            return;
        }
        Piece targetPiece = board.get(target);
        if (targetPiece.team == team) {
            throw new IllegalArgumentException("[ERROR] 도착 지점에 같은 팀의 기물이 존재합니다.");
        }
        board.take(targetPiece);
    }

    public Position getPosition() {
        return position;
    }

    public boolean onPosition(Position nextPos) {
        return position.equals(nextPos);
    }

    public record Route(
        List<Position> positions
    ) {

        public Position sum() {
            return new Position(sumOf(Position::x), sumOf(Position::y));
        }

        private int sumOf(ToIntFunction<Position> function) {
            return positions.stream()
                .mapToInt(function)
                .sum();
        }
    }

    public Team getTeam() {
        return team;
    }

    public boolean equalsTeam(Team team) {
        return this.team == team;
    }

    public abstract PieceType type();
}
