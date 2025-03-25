package model.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import model.Position;
import model.Team;

public abstract class Piece {
    private final Team team;

    protected Position position;
    protected final List<Route> routes = new ArrayList<>();

    protected Piece(int x, int y, Team team) {
        this.team = team;
        position = new Position(x, y);
    }

    protected abstract Route findMovableRoute(BoardSearcher boardSearcher, Position difference);

    protected abstract void validateRoute(BoardSearcher boardSearcher, Route route, Position difference);

    public abstract PieceType type();

    public void move(BoardSearcher boardSearcher, Team currentTurn, Position difference) {
        validateTeam(currentTurn);
        Route movableRoute = findMovableRoute(boardSearcher, difference);
        validateRoute(boardSearcher, movableRoute, difference);
        validateDestination(boardSearcher, difference);
        position = position.move(difference);
    }

    private void validateTeam(Team currentTurn) {
        if (!currentTurn.equals(team)) {
            throw new IllegalArgumentException("[ERROR] 다른 팀의 기물은 움직일 수 없습니다.");
        }
    }

    private void validateDestination(BoardSearcher boardSearcher, Position difference) {
        Piece target = boardSearcher.find(position.move(difference));
        if (target != null && team == target.team) {
            throw new IllegalArgumentException("[ERROR] 도착 지점에 같은 팀의 기물이 존재합니다.");
        }
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
}
