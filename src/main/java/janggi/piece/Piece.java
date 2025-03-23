package janggi.piece;

import janggi.Team;
import janggi.board.Board;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

import janggi.position.Position;

public abstract class Piece {

    private final Team team;
    protected Position position;
    protected final List<Route> routes = new ArrayList<>();

    protected Piece(Position position, Team team) {
        this.team = team;
        this.position = position;
    }

    public void move(Board board, Position destination) {
        Route movableRoute = findMovableRoute();
        movableRoute.validateInterrupt(board, position);
        arrival(board, destination);
    }

    protected Route findMovableRoute() {
        for (var route : routes) {
            if(route.canMove(position)){
                return route;
            }
        }
        throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
    }

    private void arrival(Board board, Position destination) {
        if (!board.hasPieceOn(destination)) {
            position = destination;
            return;
        }
        Piece destinationPiece = board.get(destination);
        if (destinationPiece.team == this.team) {
            throw new IllegalArgumentException("[ERROR] 도착 지점에 같은 팀의 기물이 존재합니다.");
        }
        board.take(destinationPiece);
        position = destination;
    }

    public Position position() {
        return position;
    }

    public boolean onPosition(Position position) {
        return this.position.equals(position);
    }

    public Team getTeam() {
        return team;
    }

    public abstract PieceType type();
}
