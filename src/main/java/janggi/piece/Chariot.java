package janggi.piece;

import static janggi.position.Direction.EAST;
import static janggi.position.Direction.NORTH;
import static janggi.position.Direction.SOUTH;
import static janggi.position.Direction.WEST;

import janggi.Team;
import janggi.position.Route;
import java.util.List;

import janggi.position.Position;
import janggi.board.Board;

public class Chariot extends Piece {

    public Chariot(Position position, Team team) {
        super(position, team);
        routes.addAll(List.of(
            new Route(List.of(EAST)),
            new Route(List.of(WEST)),
            new Route(List.of(SOUTH)),
            new Route(List.of(NORTH))
        ));
    }

//    @Override
//    protected Route findMovableRoute(Board board, int dx, int dy) {
//        Position target = position.move(dx, dy);
//        for (var route : routes) {
//            Position dir = route.positions().getFirst();
//            Position nextPos = position.move(dir.x(), dir.y());
//            while (board.isInboard(nextPos)) {
//                if (nextPos.equals(target)) {
//                    return route;
//                }
//                nextPos = nextPos.move(dir.x(), dir.y());
//            }
//        }
//        return null;
//    }
//
//    @Override
//    protected void validateRoute(Board board, Route route, Position target) {
//        Position validatePosition = nextPositionOnRoute(position, route);
//        while (!validatePosition.equals(target)) {
//            validateOtherPieceOnRoute(board, validatePosition);
//            validatePosition = validatePosition.move(route.positions().getFirst());
//        }
//    }
//
//    private static void validateOtherPieceOnRoute(Board board, Position validatePosition) {
//        if (board.hasPieceOn(validatePosition)) {
//            throw new IllegalArgumentException("[ERROR] 이동 경로에 다른 기물이 존재합니다.");
//        }
//    }
//
//    private Position nextPositionOnRoute(Position position, Route route) {
//        return position.move(route.positions().getFirst());
//    }

    @Override
    public PieceType type() {
        return PieceType.CHARIOT;
    }
}
