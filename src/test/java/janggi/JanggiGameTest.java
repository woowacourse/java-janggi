package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.piece.Cannon;
import janggi.piece.Color;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.piece.Soldier;
import janggi.piece.Tank;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JanggiGameTest {

    @Nested
    @DisplayName("보드 초기화 테스트")
    class InitJanggiGameTest {

        @Test
        @DisplayName("보드가 초기화 되면 32개의 기물을 가지고 있다.")
        void create32PiecesWhenStart() {
            // when
            JanggiGame janggiGame = new JanggiGame();

            // then
            int expected = 32;
            assertThat(janggiGame.getBoard()).hasSize(expected);
        }

        @ParameterizedTest
        @DisplayName("보드가 초기화 되면 2개의 진영이 16개의 기물을 가지고 있다.")
        @CsvSource(value = {"RED", "BLUE"})
        void createEachSide16PiecesWhenStart(Color color) {
            // when
            JanggiGame janggiGame = new JanggiGame();

            // then
            List<Piece> pieces = janggiGame.getBoard()
                    .values()
                    .stream()
                    .toList();
            List<Piece> piecesOfSide = pieces.stream()
                    .filter(piece -> piece.getSide() == color)
                    .toList();
            int expected = 16;
            assertThat(piecesOfSide).hasSize(expected);
        }
    }

    @Nested
    @DisplayName("기물 이동 테스트")
    class MoveTest {

        @Test
        @DisplayName("장기말을 이동시킬 때, 시작점과 도착점의 말이 같은 색이라면 예외가 발생한다")
        void should_throw_exception_when_start_and_end_position_piece_is_same_color() {
            // given
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            new Position(1, 1), new Tank(Color.RED),
                            new Position(2, 1), new Soldier(Color.RED)
                    ))
            );
            Position start = new Position(1, 1);
            Position end = new Position(2, 1);

            // then
            assertThatThrownBy(() -> janggiGame.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("시작점과 도착점이 주어질 때, 시작점에 있는 말을 도착점으로 이동시킨다")
        void should_move_piece_by_start_and_end_position() {
            // given
            Piece piece = new Tank(Color.RED);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            new Position(1, 1), piece
                    ))
            );
            Position start = new Position(1, 1);
            Position end = new Position(2, 1);

            // when
            janggiGame.move(start, end);

            // then
            assertAll(
                    () -> assertThat(janggiGame.getBoard().get(end)).isEqualTo(piece),
                    () -> assertThat(janggiGame.getBoard().get(start)).isNull()
            );
        }

        @Test
        @DisplayName("장기말을 이동시킬 때, 도착점에 상대편 장기말이 있는 경우 제거하고 시작점에 있는 말을 도착점으로 이동시킨다")
        void should_move_and_kill_end_position_piece_by_start_and_end_position() {
            // given
            Piece piece = new Tank(Color.RED);
            Piece enemyPiece = new Cannon(Color.BLUE);
            Position start = new Position(1, 1);
            Position end = new Position(2, 1);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, piece,
                            end, enemyPiece
                    ))
            );

            // when
            janggiGame.move(start, end);

            // then
            assertAll(
                    () -> assertThat(janggiGame.getBoard().get(end)).isEqualTo(piece),
                    () -> assertThat(janggiGame.getBoard().get(start)).isNull()
            );
        }

        @Test
        @DisplayName("장기말을 이동시킬 때, 장기말의 이동 규칙과 맞지 않는다면 예외가 발생한다")
        void should_throw_exception_when_unfollow_piece_movement_rule() {
            // given
            Piece piece = new Tank(Color.RED);
            Position start = new Position(1, 1);
            Position end = new Position(2, 2);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, piece
                    ))
            );

            // when & then
            assertThatThrownBy(() -> janggiGame.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("말의 경로에 다른 말이 존재한다면 예외를 던진다.")
        void should_throw_exception_when_exists_piece_on_path() {
            // given
            Position start = new Position(1, 1);
            Position positionOnPath = new Position(3, 1);
            Position end = new Position(5, 1);
            Piece tank = new Tank(Color.RED);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, tank,
                            positionOnPath, new Soldier(Color.BLUE)
                    ))
            );

            // when & then
            assertThatThrownBy(() -> janggiGame.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포가 이동할 때, 경로에 말이 2개 이상 있다면 예외가 발생한다")
        void should_throw_exception_when_exists_two_or_more_pieces_on_cannon_movement_path() {
            // given
            Piece cannon = new Cannon(Color.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);

            Position positionOnPathFirst = new Position(3, 1);
            Position positionOnPathSecond = new Position(4, 1);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, cannon,
                            positionOnPathFirst, new Tank(Color.BLUE),
                            positionOnPathSecond, new Tank(Color.BLUE)
                    ))
            );

            // when & then
            assertThatThrownBy(() -> janggiGame.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포가 이동할 때, 경로에 말이 없다면 예외가 발생한다")
        void should_throw_exception_when_not_exists_piece_on_cannon_movement_path() {
            // given
            Piece cannon = new Cannon(Color.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, cannon
                    ))
            );

            // when & then
            assertThatThrownBy(() -> janggiGame.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포가 이동할 때, 경로에 말이 1개 있다면 예외가 발생하지 않는다")
        void should_not_throw_exception_when_exists_one_piece_on_cannon_movement_path() {
            // given
            Piece cannon = new Cannon(Color.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            Position positionOnPath = new Position(3, 1);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, cannon,
                            positionOnPath, new Tank(Color.BLUE)
                    ))
            );

            // when & then
            assertThatCode(() -> janggiGame.move(start, end))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("포가 이동할 때, 경로에 포가 있다면 예외가 발생한다")
        void should_throw_exception_when_exists_cannon_piece_on_cannon_movement_path() {
            // given
            Piece cannon = new Cannon(Color.RED);
            Piece otherCannon = new Cannon(Color.BLUE);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            Position positionOnPath = new Position(3, 1);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, cannon,
                            positionOnPath, otherCannon
                    ))
            );

            // when & then
            assertThatThrownBy(() -> janggiGame.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포가 이동할 때, 도착점의 장기말이 포인 경우 예외가 발생한다")
        void should_throw_exception_when_end_position_piece_is_cannon() {
            // given
            Piece cannon = new Cannon(Color.RED);
            Piece otherCannon = new Cannon(Color.BLUE);
            Piece tank = new Tank(Color.BLUE);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            Position positionOnPath = new Position(3, 1);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, cannon,
                            positionOnPath, tank,
                            end, otherCannon
                    ))
            );

            // when & then
            assertThatThrownBy(() -> janggiGame.move(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
