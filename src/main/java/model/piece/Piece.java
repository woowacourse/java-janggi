package model.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import model.Position;
import model.Team;
import model.board.BoardSearcher;
import model.piece.movement.DefaultMovement;

public abstract class Piece {

    private Integer id;
    protected Position position;
    protected final Team team;
    protected final List<Route> routes = new ArrayList<>();
    protected final DefaultMovement movement = new DefaultMovement();

    protected Piece(int x, int y, Team team) {
        this.team = team;
        position = new Position(x, y);
    }

    public abstract PieceType type();

    protected Route findMovableRoute(BoardSearcher boardSearcher, Position difference) {
        return movement.findMovableRoute(boardSearcher, routes, team, position, difference);
    }

    protected void validateRoute(BoardSearcher boardSearcher, Route route, Position difference) {
        movement.validateRoute(boardSearcher, route, position, difference);
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
