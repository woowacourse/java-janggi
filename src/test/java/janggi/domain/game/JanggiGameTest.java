package janggi.domain.game;

import static janggi.domain.piece.Side.BLUE;
import static janggi.domain.piece.Side.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.King;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Tank;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Nested
    @DisplayName("말 점수 테스트")
    class PieceScoreTest {

        @DisplayName("RED 팀 기물 점수를 계산한다.")
        @Test
        void returnScoreByRedSidePieces() {
            // given
            Board board = new Board(Map.of(
                    new Position(1, 1), new Tank(RED),
                    new Position(1, 2), new Cannon(RED),
                    new Position(1, 3), new Soldier(RED),
                    new Position(1, 4), new King(RED)
            ));
            JanggiGame janggiGame = new JanggiGame(
                    board,
                    Turn.firstTurn()
            );

            // when
            Score score = janggiGame.scoreBySide(RED);

            // then
            assertThat(score).isEqualTo(new Score(23.5));
        }

        @DisplayName("BLUE 팀 기물 점수를 계산한다.")
        @Test
        void returnScoreByBlueSidePieces() {
            // given
            Board board = new Board(Map.of(
                    new Position(1, 1), new Tank(BLUE),
                    new Position(1, 2), new Cannon(BLUE),
                    new Position(1, 3), new Soldier(BLUE),
                    new Position(1, 4), new King(BLUE)
            ));
            JanggiGame janggiGame = new JanggiGame(
                    board,
                    Turn.firstTurn()
            );

            // when
            Score score = janggiGame.scoreBySide(BLUE);

            // then
            assertThat(score).isEqualTo(new Score(22));
        }
    }
}
