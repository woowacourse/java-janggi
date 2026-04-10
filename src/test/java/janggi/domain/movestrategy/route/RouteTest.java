package janggi.domain.movestrategy.route;

import janggi.domain.board.Board;
import janggi.domain.board.BoardState;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RouteTest {

    @Test
    void 지시된_경로대로_가면_도착지와_일치하는지_isMatch를_통해_확인할수_있다() {
        // give
        Route route = new Route(List.of(Direction.SOUTH, Direction.SOUTH_EAST));
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(2), Column.of(1));
        // when & then
        assertThat(route.isMatch(from, to)).isTrue();
    }

    @Test
    void 도착지가_어긋나면_isMatch는_false를_반환한다() {
        // give
        Route route = new Route(List.of(Direction.SOUTH, Direction.SOUTH_EAST));
        Position from = Position.of(Row.of(0), Column.of(0));
        Position failTo = Position.of(Row.of(1), Column.of(1)); // 잘못된 도착지
        // when & then
        assertThat(route.isMatch(from, failTo)).isFalse();
    }

    @Test
    void 경로_중간에_장애물이_있으면_isPathClear는_false를_반환한다() {
        // give
        Route route = new Route(List.of(Direction.SOUTH, Direction.SOUTH_EAST));
        Position from = Position.of(Row.of(0), Column.of(0));
        Position obstacle = Position.of(Row.of(1), Column.of(0));

        Map<Position, Piece> initialPieces = new HashMap<>();
        initialPieces.put(obstacle, new Piece(Team.HAN, PieceType.HAN_JOL));
        BoardState boardState = new Board(initialPieces);
        // when & then
        assertThat(route.isPathClear(from, boardState)).isFalse();
    }

    @Test
    void 경로가_장기판_범위를_벗어나면_isPathClear는_false를_반환한다() {
        // give
        Route route = new Route(List.of(Direction.NORTH, Direction.NORTH_WEST));
        Position from = Position.of(Row.of(0), Column.of(0));

        BoardState boardState = new Board(new HashMap<>());
        // when & then
        assertThat(route.isPathClear(from, boardState)).isFalse();
    }
}
