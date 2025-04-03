package janggi;

import static org.assertj.core.api.Assertions.assertThat;
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
        @DisplayName("시작점과 도착점의 장기말이 같은 색이라 이동 불가능하다면 false 반환한다")
        void should_return_false_when_start_and_end_position_piece_is_same_color() {
            // given
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            new Position(1, 1), new Tank(Color.RED),
                            new Position(2, 1), new Soldier(Color.RED)
                    ))
            );
            Position start = new Position(1, 1);
            Position end = new Position(2, 1);

            // when
            boolean isMoveSuccess = janggiGame.move(start, end);

            // then
            assertThat(isMoveSuccess).isFalse();
        }

        @Test
        @DisplayName("시작점에 있는 말을 장기말이 없는 도착점으로 이동시킨다")
        void should_move_piece_to_empty_end_position() {
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
                    () -> assertThat(janggiGame.getBoard()
                            .get(end)).isEqualTo(piece),
                    () -> assertThat(janggiGame.getBoard()
                            .get(start)).isNull()
            );
        }

        @Test
        @DisplayName("도착점에 상대편 장기말이 있는 경우 제거하고 시작점에 있는 말을 도착점으로 이동시킨다")
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
                    () -> assertThat(janggiGame.getBoard()
                            .get(end)).isEqualTo(piece),
                    () -> assertThat(janggiGame.getBoard()
                            .get(start)).isNull()
            );
        }

        @Test
        @DisplayName("장기말의 이동 규칙을 따르지 않으면 이동 불가능하여 false 반환한다")
        void should_return_false_when_unfollow_piece_movement_rule() {
            // given
            Piece piece = new Tank(Color.RED);
            Position start = new Position(1, 1);
            Position end = new Position(2, 2);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, piece
                    ))
            );

            // when
            boolean isMoveSuccess = janggiGame.move(start, end);

            // then
            assertThat(isMoveSuccess).isFalse();
        }

        @Test
        @DisplayName("장기말의 이동 경로에 다른 장기말이 존재하면 이동 불가능 하여 false 반환한다")
        void should_return_false_when_exists_piece_on_path() {
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

            // when
            boolean isMoveSuccess = janggiGame.move(start, end);

            // then
            assertThat(isMoveSuccess).isFalse();
        }

        @Test
        @DisplayName("포가 이동할 때, 경로에 장기말이 2개 이상이면 이동 불가능 하여 false 반환한다")
        void should_return_false_when_exists_two_or_more_pieces_on_cannon_movement_path() {
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

            // when
            boolean isMoveSuccess = janggiGame.move(start, end);

            // then
            assertThat(isMoveSuccess).isFalse();
        }

        @Test
        @DisplayName("포가 이동할 때, 경로에 장기말이 없으면 이동 불가능하여 false 반환한다")
        void should_return_false_when_not_exists_piece_on_cannon_movement_path() {
            // given
            Piece cannon = new Cannon(Color.RED);
            Position start = new Position(1, 1);
            Position end = new Position(6, 1);
            JanggiGame janggiGame = new JanggiGame(
                    new Pieces(Map.of(
                            start, cannon
                    ))
            );

            // when
            boolean isMoveSuccess = janggiGame.move(start, end);

            // then
            assertThat(isMoveSuccess).isFalse();
        }

        @Test
        @DisplayName("포가 이동할 때, 경로에 장기말이 1개 있어 이동 가능하다면 true 반환한다")
        void should_return_true_when_exists_one_piece_on_cannon_movement_path() {
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

            // when
            boolean isMoveSuccess = janggiGame.move(start, end);

            // then
            assertThat(isMoveSuccess).isTrue();
        }

        @Test
        @DisplayName("포가 이동할 때, 이동 경로에 포가 있으면 이동 불가능하여 false 반환한다")
        void should_return_false_when_exists_cannon_piece_on_cannon_movement_path() {
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

            // when
            boolean isMoveSuccess = janggiGame.move(start, end);

            // then
            assertThat(isMoveSuccess).isFalse();
        }

        @Test
        @DisplayName("포가 이동할 때, 도착점이 포인 경우 이동 불가능 하여 false 반환한다")
        void should_return_false_when_start_and_end_position_piece_is_cannon() {
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

            // when
            boolean isMoveSuccess = janggiGame.move(start, end);

            // then
            assertThat(isMoveSuccess).isFalse();
        }
    }

    @Test
    @DisplayName("색상이 주어질 때 해당 색상의 장기말 점수를 반환한다")
    void should_return_piece_score_by_color() {
        // given
        JanggiGame janggiGame = new JanggiGame(Pieces.init());

        // when
        double redScore = janggiGame.getScore(Color.RED);
        double blueScore = janggiGame.getScore(Color.BLUE);

        // then
        final double AFTER_PLAYER_DEOM_SCORE = 1.5;
        assertAll(
                () -> assertThat(redScore).isEqualTo(72 + AFTER_PLAYER_DEOM_SCORE),
                () -> assertThat(blueScore).isEqualTo(72)
        );
    }

    @Test
    @DisplayName("Position이 주어질 때 해당 위치에 있는 장기말을 반환한다")
    void should_return_piece_by_position() {
        // given
        Position position = new Position(1, 1);
        Piece piece = new Tank(Color.RED);
        JanggiGame janggiGame = new JanggiGame(
                new Pieces(Map.of(
                        position, piece
                ))
        );

        // when
        Piece result = janggiGame.getPiece(position);

        // then
        assertThat(result).isEqualTo(piece);
    }
}
