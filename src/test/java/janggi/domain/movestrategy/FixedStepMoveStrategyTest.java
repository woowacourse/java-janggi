package janggi.domain.movestrategy;

import janggi.domain.board.Board;
import janggi.domain.board.BoardState;
import janggi.domain.movestrategy.route.Direction;
import janggi.domain.movestrategy.route.Route;
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

class FixedStepMoveStrategyTest {

    @Test
    void 주입받은_경로중_일치하고_방해물이_없는_경로가_있으면_이동가능하다() {
        // give
        Route route = new Route(List.of(Direction.SOUTH_EAST, Direction.SOUTH_EAST));
        FixedStepMoveStrategy strategy = new FixedStepMoveStrategy(List.of(route));

        BoardState boardState = new Board(new HashMap<>());
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(2), Column.of(2));
        // // when & then
        assertThat(strategy.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 목적지가_라우트와_맞지_않으면_이동불가능하다() {
        // give
        Route route = new Route(List.of(Direction.SOUTH_EAST, Direction.SOUTH_EAST));
        FixedStepMoveStrategy strategy = new FixedStepMoveStrategy(List.of(route));

        BoardState boardState = new Board(new HashMap<>());
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(1), Column.of(1));
        // when & then
        assertThat(strategy.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 일치하는_경로라도_경유지에_장애물이_있으면_이동불가능하다() {
        // give
        Route route = new Route(List.of(Direction.SOUTH_EAST, Direction.SOUTH_EAST));
        FixedStepMoveStrategy strategy = new FixedStepMoveStrategy(List.of(route));

        Position from = Position.of(Row.of(0), Column.of(0));
        Position obstacle = Position.of(Row.of(1), Column.of(1));
        Position to = Position.of(Row.of(2), Column.of(2));

        Map<Position, Piece> initialPieces = new HashMap<>();
        initialPieces.put(obstacle, new Piece(Team.HAN, PieceType.HAN_JOL));
        BoardState boardState = new Board(initialPieces);
        // when & then
        assertThat(strategy.canMove(from, to, boardState)).isFalse();
    }
}
