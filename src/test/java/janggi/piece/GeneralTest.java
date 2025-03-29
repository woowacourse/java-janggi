package janggi.piece;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import janggi.moving.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GeneralTest {
    @Nested
    class validatePath {
        @DisplayName("초나라_궁이_궁성_밖으로_이동하면_예외를_발생한다")
        @Test
        void aa() {
            // given
            Map<Position, Piece> initialBoard = new HashMap<>();
            Position start = createPosition(4, 2);
            Position goal = createPosition(4, 3);
            Path path = new Path(List.of(start, goal));
            General piece = new General(Team.GREEN);

            initialBoard.put(start, piece);
            Board board = new Board(initialBoard);

            // then
            assertThatThrownBy(() -> piece.validatePath(board, path))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 궁은 궁성을 벗어날 수 없습니다.");
        }

        @DisplayName("한나라_궁이_궁성_밖으로_이동하면_예외를_발생한다")
        @Test
        void aaa() {
            // given
            Map<Position, Piece> initialBoard = new HashMap<>();
            Position start = createPosition(3, 7);
            Position goal = createPosition(2, 7);
            Path path = new Path(List.of(start, goal));
            General piece = new General(Team.RED);

            initialBoard.put(start, piece);
            Board board = new Board(initialBoard);

            // then
            assertThatThrownBy(() -> piece.validatePath(board, path))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 궁은 궁성을 벗어날 수 없습니다.");
        }

        @DisplayName("초나라_궁이_선이_없는데_대각으로_이동하면_예외를_발생한다")
        @CsvSource(value = {"4:0:3:1", "4:0:5:1", "3:1:4:0", "3:1:4:2", "4:2:3:1", "4:2:5:1", "5:1:4:2", "5:1:4:0"},
                delimiterString = ":")
        @ParameterizedTest
        void should_ThrowException_WhenInvalidDiagonalPath(int startColumn, int startRow, int goalColumn, int goalRow) {
            // given
            Map<Position, Piece> initialBoard = new HashMap<>();
            Position start = createPosition(startColumn, startRow);
            Position goal = createPosition(goalColumn, goalRow);
            Path path = new Path(List.of(start, goal));
            General piece = new General(Team.GREEN);

            initialBoard.put(start, piece);
            Board board = new Board(initialBoard);

            // then
            assertThatThrownBy(() -> piece.validatePath(board, path))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 선이 존재하는 경우에만 이동할 수 있습니다.");
        }

        @DisplayName("한나라_궁이_선이_없는데_대각으로_이동하면_예외를_발생한다")
        @CsvSource(value = {"4:9:3:8", "4:9:5:8", "3:8:4:9", "3:8:4:7", "4:7:3:8", "4:7:5:8", "5:8:4:7", "5:8:4:9"},
                delimiterString = ":")
        @ParameterizedTest
        void should_ThrowException_WhenInvalidDiagonalPath2(int startColumn, int startRow, int goalColumn, int goalRow) {
            // given
            Map<Position, Piece> initialBoard = new HashMap<>();
            Position start = createPosition(startColumn, startRow);
            Position goal = createPosition(goalColumn, goalRow);
            Path path = new Path(List.of(start, goal));
            General piece = new General(Team.RED);

            initialBoard.put(start, piece);
            Board board = new Board(initialBoard);

            // then
            assertThatThrownBy(() -> piece.validatePath(board, path))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 선이 존재하는 경우에만 이동할 수 있습니다.");
        }
    }

    @Nested
    class validateMovable {
        @DisplayName("초나라_궁은_궁성_내_선을_따라_대각으로_한칸_움직일_수_있다")
        @CsvSource(value = {"3:0:4:1", "3:2:4:1", "5:0:4:1", "5:2:4:1", "4:1:3:0", "4:1:3:2", "4:1:5:0", "4:1:5:2"},
                delimiterString = ":")
        @ParameterizedTest
        void validateMovable(int startColumn, int startRow, int goalColumn, int goalRow) {
            // given
            Map<Position, Piece> initialBoard = new HashMap<>();
            Position start = createPosition(startColumn, startRow);
            Position goal = createPosition(goalColumn, goalRow);
            General piece = new General(Team.GREEN);

            initialBoard.put(start, piece);
            Board board = new Board(initialBoard);

            // then
            assertThatCode(() -> piece.validateMovable(board, start, goal))
                    .doesNotThrowAnyException();
        }
    }
}
