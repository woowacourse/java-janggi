package janggi.domain.movestrategy;

import janggi.domain.board.Board;
import janggi.domain.board.BoardState;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceBoundStrategyTest {

    @ParameterizedTest
    @CsvSource ({
            "1, 4, 0, 3", "1, 4, 0, 4", "1, 4, 0, 5",
            "1, 4, 1, 3", "1, 4, 1, 5",
            "1, 4, 2, 3", "1, 4, 2, 4", "1, 4, 2, 5"
    })
    void 한나라_궁성_안으로_이동하는_것은_통과한다(int fromRow, int fromColumn, int toRow, int toColumn) {
        // give
        MoveStrategy alwaysTrueStrategy = (from, to, boardState) -> true;
        PalaceBoundStrategy strategy = new PalaceBoundStrategy(alwaysTrueStrategy);

        BoardState boardState = new Board(new HashMap<>());
        
        Position from = Position.of(Row.of(fromRow), Column.of(fromColumn));
        Position to = Position.of(Row.of(toRow), Column.of(toColumn));
        // when & then
        assertThat(strategy.canMove(from, to, boardState)).isTrue();
    }

    @ParameterizedTest
    @CsvSource ({
            "0, 3, 0, 2", "0, 5, 0, 6",
            "1, 3, 1, 2", "1, 5, 1, 6",
            "2, 3, 2, 2", "2, 4, 3, 4", "2, 5, 2, 6"
    })
    void 한나라_궁성_경계_밖으로_나가는_것은_실패한다(int fromRow, int fromColumn, int toRow, int toColumn) {
        // give
        MoveStrategy alwaysTrueStrategy = (from, to, boardState) -> true;
        PalaceBoundStrategy strategy = new PalaceBoundStrategy(alwaysTrueStrategy);
        BoardState board = new Board(new HashMap<>());
        
        Position from = Position.of(Row.of(fromRow), Column.of(fromColumn));
        Position to = Position.of(Row.of(toRow), Column.of(toColumn));
        // when & then
        assertThat(strategy.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest
    @CsvSource ({
            "8, 4, 7, 3", "8, 4, 7, 4", "8, 4, 7, 5",
            "8, 4, 8, 3", "8, 4, 8, 5",
            "8, 4, 9, 3", "8, 4, 9, 4", "8, 4, 9, 5"
    })
    void 초나라_궁성_안으로_이동하는_것은_통과한다(int fromRow, int fromColumn, int toRow, int toColumn) {
        // give
        MoveStrategy alwaysTrueStrategy = (from, to, boardState) -> true;
        PalaceBoundStrategy strategy = new PalaceBoundStrategy(alwaysTrueStrategy);
        BoardState board = new Board(new HashMap<>());
        
        Position from = Position.of(Row.of(fromRow), Column.of(fromColumn));
        Position to = Position.of(Row.of(fromRow), Column.of(fromColumn));
        // when & then
        assertThat(strategy.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest
    @CsvSource ({
            "7, 3, 7, 2", "7, 5, 7, 6", "7, 4, 6, 4",
            "8, 3, 8, 2", "8, 5, 8, 6",
            "9, 3, 9, 2", "9, 5, 9, 6"
    })
    void 초나라_궁성_경계_밖으로_나가는_것은_실패한다(int fromRow, int fromColumn, int toRow, int toColumn) {
        // give
        MoveStrategy alwaysTrueStrategy = (from, to, boardState) -> true;
        PalaceBoundStrategy strategy = new PalaceBoundStrategy(alwaysTrueStrategy);
        BoardState board = new Board(new HashMap<>());

        Position from = Position.of(Row.of(fromRow), Column.of(fromColumn));
        Position to = Position.of(Row.of(toRow), Column.of(toColumn));
        // when & then
        assertThat(strategy.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest
    @CsvSource ({
            "1, 3, 0, 4",
            "1, 5, 0, 4",
            "1, 3, 2, 4",
            "1, 5, 2, 4"
    })
    void 한나라_궁성_안이라도_정중앙을_거치지_않는_대각선_이동은_불가능하다(int fromRow, int fromColumn, int toRow, int toColumn) {
        // give
        MoveStrategy alwaysTrueStrategy = (from, to, boardState) -> true;
        PalaceBoundStrategy strategy = new PalaceBoundStrategy(alwaysTrueStrategy);
        BoardState board = new Board(new HashMap<>());
        
        Position from = Position.of(Row.of(fromRow), Column.of(fromColumn));
        Position to = Position.of(Row.of(toRow), Column.of(toColumn));

        // when & then
        assertThat(strategy.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest
    @CsvSource ({
            "8, 3, 7, 4",
            "8, 5, 7, 4",
            "8, 3, 9, 4",
            "8, 5, 9, 4"
    })
    void 초나라_궁성_안이라도_정중앙을_거치지_않는_대각선_이동은_불가능하다(int fromRow, int fromColumn, int toRow, int toColumn) {
        // given
        MoveStrategy alwaysTrueStrategy = (from, to, boardState) -> true;
        PalaceBoundStrategy strategy = new PalaceBoundStrategy(alwaysTrueStrategy);
        BoardState board = new Board(new HashMap<>());

        Position from = Position.of(Row.of(fromRow), Column.of(fromColumn));
        Position to = Position.of(Row.of(toRow), Column.of(toColumn));
        // when & then
        assertThat(strategy.canMove(from, to, board)).isFalse();
    }

    @Test
    void 내부_전략에서_거부하면_궁성_안이라도_불가능하다() {
        // give
        MoveStrategy alwaysFalseStrategy = (from, to, boardState) -> false;
        PalaceBoundStrategy strategy = new PalaceBoundStrategy(alwaysFalseStrategy);
        BoardState board = new Board(new HashMap<>());

        Position from = Position.of(Row.of(1), Column.of(4));
        Position to = Position.of(Row.of(2), Column.of(4));
        // when & then
        assertThat(strategy.canMove(from, to, board)).isFalse();
    }
}
