package janggi.game;

import janggi.piece.Team;
import janggi.piece.pieces.Piece;
import janggi.position.Position;
import janggi.position.Route;
import java.util.HashMap;
import java.util.List;

public class Janggi {
    private final Pieces pieces;
    private Team turn;

    public Janggi(Pieces pieces, Team startTurn) {
        this.pieces = pieces;
        this.turn = startTurn;
    }

    public void judgeUnitTurn(Position position) {
        Team unitTeam = pieces.findTeamByPosition(position);
        if (unitTeam != this.turn) {
            throw new IllegalArgumentException("현재 차례가 아닙니다.");
        }
    }

    public void changeTurn() {
        turn = turn.getOppositie();
    }

    public List<Route> searchAvailableRoutes(Position pickedPosition) {
        Piece pickedPiece = pieces.findPieceByPosition(pickedPosition);
        List<Route> totalRoutes = pickedPiece.calculateRoutes(pickedPosition);
        if (pickedPiece.isCannon()) {
            totalRoutes = applyCannonProperty(totalRoutes, pickedPosition);
        }
        List<Route> routes = findAvailableRoute(totalRoutes, pickedPosition);
        if (routes.isEmpty()) {
            throw new IllegalArgumentException("해당 기물의 이동 가능한 경로가 없습니다.");
        }
        return routes;
    }

    private List<Route> applyCannonProperty(List<Route> totalRoutes, Position pick) {
        totalRoutes = totalRoutes.stream().filter(route -> route.canBombJump(pieces)).toList();
        return totalRoutes.stream()
                .filter(route -> isAvailableEndPoint(route, pick))
                .toList();
    }

    public boolean isNoneEnemyUnit() {
        return pieces.isNoneSameTeamUnit(turn);
    }

    private List<Route> findAvailableRoute(List<Route> routes, Position startPoint) {
        return routes.stream()
                .filter(this::isAvailablePath)
                .filter(route -> isAvailableEndPoint(route, startPoint))
                .toList();
    }

    public boolean isAvailablePath(Route route) {
        if (route.length() == 0) {
            return false;
        }
        return route.getPointsExceptEndPoint().stream()
                .allMatch(pieces::isEmptyPoint);
    }

    private boolean isAvailableEndPoint(Route route, Position startPoint) {
        Position endPosition = route.searchEndPoint(startPoint);
        if (pieces.isExistPiece(endPosition)) {
            return turn != pieces.findTeamByPosition(endPosition);
        }
        return true;
    }

    public void moveAndCaptureIfEnemyExists(Route route, Position startPoint) {
        Position endPoint = route.searchEndPoint(startPoint);
        pieces.moveAndCaptureIfEnemyExists(startPoint, endPoint);
    }

    public Team getTurn() {
        return turn;
    }

    public HashMap<Position, Piece> getPieces() {
        return pieces.getPieces();
    }
}
