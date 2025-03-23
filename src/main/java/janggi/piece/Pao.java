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

public class Pao extends Piece {

    public Pao(Position position, Team team) {
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
//        if (isTargetPao(board, target)) {
//            return null;
//        }
//        boolean isOvered = false;
//        for (var route : routes) {
//            Position nextPos = nextPositionOnRoute(position, route);
//            while (board.isInboard(nextPos)) {
//                if (overPiece(board, nextPos)) {
//                    isOvered = true;
//                }
//                if (nextPos.equals(target) && isOvered) {
//                    return route;
//                }
//                nextPos = nextPositionOnRoute(nextPos, route);
//            }
//        }
//        return null;
//    }
//
//    private boolean overPiece(Board board, Position nextPos) {
//        return board.hasPieceOn(nextPos);
//    }
//
//    private boolean isTargetPao(Board board, Position target) {
//        return board.hasPieceOn(target) && board.get(target).type() == PieceType.PAO;
//    }
//
//    @Override
//    protected void validateRoute(Board board, Route route, Position target) {
//        boolean isOvered = false;
//        Position validatePosition = nextPositionOnRoute(position, route);
//        while (!validatePosition.equals(target)) {
//            if (board.hasPieceOn(validatePosition)) {
//                validateOverPao(board, validatePosition);
//                validateIsOvered(isOvered);
//                isOvered = true;
//            }
//            validatePosition = nextPositionOnRoute(validatePosition, route);
//        }
//    }
//
//    private static void validateOverPao(Board board, Position validatePosition) {
//        if (board.get(validatePosition).type() == PieceType.PAO) {
//            throw new IllegalArgumentException("[ERROR] 포는 포를 넘을 수 없습니다.");
//        }
//    }
//
//    private static void validateIsOvered(boolean isOvered) {
//        if (isOvered) {
//            throw new IllegalArgumentException("[ERROR] 포는 기물을 1개만 넘을 수 있습니다.");
//        }
//    }
//
//    private Position nextPositionOnRoute(Position position, Route route) {
//        return position.move(route.positions().getFirst());
//    }

    @Override
    public PieceType type() {
        return PieceType.PAO;
    }
}
