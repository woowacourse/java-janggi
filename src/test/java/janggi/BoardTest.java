package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.piece.Cannon;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Tank;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BoardTest {

    @Nested
    @DisplayName("보드 초기화 테스트")
    class InitBoardTest {

        @Test
        @DisplayName("보드가 초기화 되면 32개의 기물을 가지고 있다.")
        void create32PiecesWhenStart() {
            // when
            Board board = Board.init();

            // then
            int expected = 32;
            assertThat(board.getBoard()).hasSize(expected);
        }

        @ParameterizedTest
        @DisplayName("보드가 초기화 되면 2개의 진영이 16개의 기물을 가지고 있다.")
        @CsvSource(value = {"RED", "BLUE"})
        void createEachSide16PiecesWhenStart(Side side) {
            // when
            Board board = Board.init();

            // then
            List<Piece> pieces = board.getBoard()
                    .values()
                    .stream()
                    .toList();
            List<Piece> piecesOfSide = pieces.stream()
                    .filter(piece -> piece.getSide() == side)
                    .toList();
            int expected = 16;
            assertThat(piecesOfSide).hasSize(expected);
        }
    }

    @Nested
    @DisplayName("기물 이동 테스트")
    class MoveTest {

        @Test
        @DisplayName("현재 위치의 말과 목적지의 말이 같은 팀이라면 예외를 발생시킨다.")
        void sholudThrowExceptionWhenCurrentPieceAndDestinationPieceIsSameSide() {
            // given
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), new Tank(Side.RED),
                            new Position(2, 1), new Soldier(Side.RED)
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(2, 1);

            // then
            assertThatThrownBy(() -> board.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("시작점과 도착점이 주어졌을 때, 말을 이동시킨다.")
        void movePieceWithStartEndPosition() {
            // given
            Piece piece = new Tank(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(2, 1);

            // when
            board.move(start, end);

            // then
            assertAll(
                    () -> assertThat(board.getBoard().get(end)).isEqualTo(piece),
                    () -> assertThat(board.getBoard().get(start)).isNull()
            );
        }

        @Test
        @DisplayName("말의 이동 규칙과 맞지 않는다면 예외를 던진다.")
        void shouldThrowExceptionWhenUnfollowingRule() {
            // given
            Piece piece = new Tank(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(2, 2);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("말의 경로에 다른 말이 존재한다면 예외를 던진다.")
        void shouldThrowExceptionWhenExistsPieceOnPath() {
            // given
            Piece piece = new Tank(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece,
                            new Position(3, 1), new Soldier(Side.RED)
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(5, 1);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포의 경로 상 말이 2개 이상인 경우 예외를 던진다.")
        void shouldThrowExceptionWhenExistsOverTwoPieceOnCannonPath() {
            // given
            Piece piece = new Cannon(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece,
                            new Position(3, 1), new Soldier(Side.RED),
                            new Position(4, 1), new Soldier(Side.RED)
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포의 경로 상 말이 1개인 경우 예외를 던지지 않는다.")
        void shouldThrowExceptionWhenExistsOnePieceOnCannonPath() {
            // given
            Piece piece = new Cannon(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece,
                            new Position(3, 1), new Soldier(Side.RED)
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);

            // when

            // then
            assertThatCode(() -> board.move(start, end))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("포의 경로 상 포가 존재하는 경우 예외를 던진다.")
        void shouldThrowExceptionWhenExistsCannonOnCannonPath() {
            // given
            Piece piece = new Cannon(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece,
                            new Position(3, 1), new Cannon(Side.RED)
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포의 경로 상에 말이 존재하지 않는 경우 예외를 던진다.")
        void shouldThrowExceptionWhenEmptyOnCannonPath() {
            // given
            Piece piece = new Cannon(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포의 도착지의 말이 포인 경우 예외를 던진다.")
        void shouldThrowExceptionWhenExistsCannonOnCannonEndPosition() {
            // given
            Piece piece = new Cannon(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece,
                            new Position(3, 1), new Soldier(Side.RED),
                            new Position(6, 1), new Cannon(Side.BLUE)
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
