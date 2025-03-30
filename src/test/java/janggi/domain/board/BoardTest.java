package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.game.Turn;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Tank;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("기물 이동 테스트")
    class MoveTest {

        @Test
        @DisplayName("현재 위치의 말과 목적지의 말이 같은 팀이라면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenCurrentPieceAndDestinationPieceIsSameSide() {
            // given
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), new Tank(Side.RED),
                            new Position(2, 1), new Soldier(Side.RED)
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(2, 1);
            Turn turn = new Turn(Side.RED);

            // then
            assertThatThrownBy(() -> board.move(start, end, turn))
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
            Turn turn = new Turn(Side.RED);

            // when
            board.move(start, end, turn);

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
            Turn turn = new Turn(Side.RED);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end, turn))
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
            Turn turn = new Turn(Side.RED);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end, turn))
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
            Turn turn = new Turn(Side.RED);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end, turn))
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
            Turn turn = new Turn(Side.RED);

            // when

            // then
            assertThatCode(() -> board.move(start, end, turn))
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
            Turn turn = new Turn(Side.RED);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end, turn))
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
            Turn turn = new Turn(Side.RED);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end, turn))
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
            Turn turn = new Turn(Side.RED);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("턴 테스트")
    class TurnTest {

        @DisplayName("본인의 턴이 아니라면 예외를 던진다.")
        @Test
        void throwExceptionWhenIsNotMyTurn() {
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
            Turn turn = new Turn(Side.BLUE);

            // when

            // then
            assertThatThrownBy(() -> board.move(start, end, turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("본인 턴이라면 말을 이동시킨다.")
        @Test
        void movePieceWhenMyTurn() {
            // given
            Piece piece = new Tank(Side.RED);
            Board board = new Board(
                    Map.of(
                            new Position(1, 1), piece
                    )
            );
            Position start = new Position(1, 1);
            Position end = new Position(2, 1);
            Turn turn = new Turn(Side.RED);

            // when
            board.move(start, end, turn);

            // then
            assertAll(
                    () -> assertThat(board.getBoard().get(end)).isEqualTo(piece),
                    () -> assertThat(board.getBoard().get(start)).isNull()
            );
        }
    }
}
