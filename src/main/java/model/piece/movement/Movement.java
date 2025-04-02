package model.piece.movement;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import model.Position;
import model.Team;
import model.board.BoardSearcher;
import model.piece.Piece;

public interface Movement {

    @Nullable
    Piece.Route findMovableRoute(BoardSearcher boardSearcher, List<Piece.Route> routes, Team team, Position position,
        Position difference);

    default void validateNormalRoute(Team team, Position target) {
    }

    default void validateRoute(BoardSearcher boardSearcher, Piece.Route route, Position position, Position difference) {
        Position onRoute = position;
        for (int i = 0; i < route.positions().size() - 1; i++) {
            onRoute = onRoute.move(route.positions().get(i));
            if (boardSearcher.hasPieceOn(onRoute)) {
                throw new IllegalArgumentException("[ERROR] 이동 경로에 다른 기물이 존재합니다.");
            }
        }
    }
}
